package com.picastlo.postservice.presentation.service

import com.picastlo.postservice.config.security.CanCreateResources
import com.picastlo.postservice.config.security.CanReadAllResources
import com.picastlo.postservice.config.security.CanReadOneResource
import com.picastlo.postservice.presentation.data.*
import com.picastlo.postservice.presentation.model.Post
import com.picastlo.postservice.presentation.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userClient: UserClient,
    private val connectionsClient: ConnectionsClient
) {

    fun getUserDetails(username: String): UserDTO {
        return userClient.getUserByUsername(username)
    }

    fun getFriends(username: String): List<FriendshipDTO> {
        return connectionsClient.getFriendsByUsername(username)
    }

    fun getGroups(username: String): List<UserDTO> {
        val groups = connectionsClient.getGroupsByUsername(username)
        val users = mutableListOf<UserDTO>()
        for (group in groups) {
            users.addAll(connectionsClient.getGroupMembers(group.name))
        }
        return users
    }

    @CanReadOneResource
    fun getPostById(id: Long): Post {
        return postRepository.findById(id).orElseThrow { Exception("Pipeline not found") }
    }

    @CanCreateResources
    fun createPost(post: Post): Post {
        return postRepository.save(post)
    }

    @CanReadAllResources
    fun getPostsByOwner(ownerId: Long): List<Post> {
        return postRepository.findByUserId(ownerId)
    }

    @CanCreateResources
    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }
}