package com.picastlo.connectionsservice.presentation.service

import com.picastlo.connectionsservice.config.security.CanReadAllResources
import com.picastlo.connectionsservice.config.security.CanReadOneResource
import com.picastlo.connectionsservice.data.UserClient
import com.picastlo.connectionsservice.data.UserDTO
import com.picastlo.connectionsservice.presentation.model.Friendship
import com.picastlo.connectionsservice.presentation.repository.FriendshipRepository
import org.springframework.stereotype.Service

@Service
class FriendshipService(
    private val friendshipRepository: FriendshipRepository,
    private val userClient: UserClient
) {

    fun getUserDetails(username: String): UserDTO {
        // Calling the user service to get the user ID
        return userClient.getUserByUsername(username)
    }

    @CanReadOneResource
    fun getFriendshipById(id: Long): Friendship {
        return friendshipRepository.findById(id).orElseThrow { Exception("Friendship not found") }
    }

    @CanReadAllResources
    fun getFriends(userId: Long): List<Friendship> {
        return friendshipRepository.findByUserId1OrUserId2(userId, userId)
    }


}