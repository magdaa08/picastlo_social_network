package com.picastlo.postservice.presentation.controller

import com.picastlo.postservice.presentation.model.Post
import com.picastlo.postservice.presentation.repository.PostRepository
import com.picastlo.postservice.presentation.service.ConnectionsClient
import com.picastlo.postservice.presentation.service.PostAPI
import com.picastlo.postservice.presentation.service.UserClient
import com.picastlo.postservice.presentation.service.UserDTO

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class PostController(
    val postRepository: PostRepository,
    val userClient: UserClient,
    val connectionsClient: ConnectionsClient
) : PostAPI {

    override fun deletePost(id: Long) {
        return postRepository.deleteById(id)
    }

    override fun getPublicFeed(req: HttpServletRequest): List<Post> {
        return postRepository.findAllByVisibility("PUBLIC")
    }

    override fun createPost(@RequestBody post: Post, principal: Principal): Post {
        return postRepository.save(post)
    }

    override fun getPostsByUsername(@PathVariable username: String, principal: Principal): List<Post> {
        val userDTO = userClient.getUserByUsername(username)
        println(userDTO)
        val posts = mutableListOf<Post>()
        posts.addAll(postRepository.findAllByVisibility("PUBLIC"))

        val friends = connectionsClient.getFriendsByUsername(userDTO.username)
        for (friend in friends) {
            if (userDTO.id == friend.id1)
            {
                val friendPosts = postRepository.findByUserId(friend.id2)
                for (friendPost in friendPosts) {
                    if (friendPost.visibility == "FRIENDS_ONLY") {
                        posts.add(friendPost)
                    }
                }
            } else {
                val friendPosts = postRepository.findByUserId(friend.id1)
                for (friendPost in friendPosts) {
                    if (friendPost.visibility == "FRIENDS_ONLY") {
                        posts.add(friendPost)
                    }
                }
            }
        }

        val groups = connectionsClient.getGroupsByUsername(username)
        val members = mutableListOf<UserDTO>()

        for (group in groups) {
            members.addAll(connectionsClient.getGroupMembers(group.name))
        }

        for (user in members) {

            val groupPosts = postRepository.findByUserId(user.id)

            for (groupPost in groupPosts) {
                if (groupPost.visibility == "GROUPS_ONLY") {
                    posts.add(groupPost)
                }
            }
        }
        println(posts.size)
        return posts
    }

    override fun updatePost(@PathVariable id: Long, @RequestBody updatedPost: Post): Post {
        val existingPost = postRepository.findById(id).get()
        return postRepository.save(existingPost.copy(
            image = updatedPost.image,
            text = updatedPost.text,
            pipelineId = updatedPost.pipelineId,
            visibility = updatedPost.visibility,
            userId = updatedPost.userId
        ))
    }

    override fun getPostsByUserId(@PathVariable id: Long, principal: Principal): List<Post> {
        return postRepository.findByUserId(id)
    }
}
