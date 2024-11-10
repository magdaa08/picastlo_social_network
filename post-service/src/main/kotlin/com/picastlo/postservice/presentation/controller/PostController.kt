package com.picastlo.postservice.presentation.controller

import com.picastlo.postservice.config.security.CanCreateResources
import com.picastlo.postservice.config.security.CanReadAllResources
import com.picastlo.postservice.presentation.model.Post
import com.picastlo.postservice.presentation.service.PostService
import com.picastlo.postservice.presentation.repository.PostRepository

//import com.picastlo.userservice.repository.UserRepository

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("posts")
class PostController(
    val postRepository: PostRepository,
    val postService: PostService
) {

    @PostMapping("/new")
    @CanCreateResources()
    fun createPost(
        @RequestParam("image") image: MultipartFile,
        @RequestParam("text") text: String,
        @RequestParam("pipelineId") pipelineId: String,
        @RequestParam("visibility") visibility: String,
        req: HttpServletRequest
    ): String {

        // Create the post object using the retrieved user
        val post = Post(
            image = image.bytes,
            text = text,
            pipelineId = pipelineId,
            visibility = visibility,
            userId = 1
        )

        postRepository.save(post)

        return "Post created"
    }

    @GetMapping("/public_feed")
    fun getPublicFeed(req: HttpServletRequest): List<Post> {
        return postRepository.findAllByVisibility("PUBLIC") // Ensure this method exists in PostRepository
    }

    @PostMapping
    fun createPost(@RequestBody post: Post, principal: Principal): Post {
        return postService.createPost(post)
    }

    @GetMapping("/owner/{username}")
    fun getPostsByUsername(@PathVariable username: String, principal: Principal): List<Post> {
        val userDTO = postService.getUserDetails(username)
        val posts = mutableListOf<Post>()
        posts.addAll(postService.getPostsByOwner(userDTO.id))

        val friends = postService.getFriends(username)

        for (friend in friends) {
            if (userDTO.id == friend.id1)
            {
                val friendPosts = postService.getPostsByOwner(friend.id2)
                for (friendPost in friendPosts) {
                    if (friendPost.visibility == "friends") {
                        posts.add(friendPost)
                    }
                }
            } else {
                val friendPosts = postService.getPostsByOwner(friend.id1)
                for (friendPost in friendPosts) {
                    if (friendPost.visibility == "friends") {
                        posts.add(friendPost)
                    }
                }
            }
        }

        val groups = postService.getGroups(username)

        for (user in groups) {

            val groupPosts = postService.getPostsByOwner(user.id)
            posts.addAll(groupPosts)

        }
        return posts
    }


    @DeleteMapping("/{id}")
    fun deletePipeline(@PathVariable id: Long, principal: Principal) {
        return postService.deletePost(id)
    }
}
