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
        return userClient.getUserByUsername(username)
    }

    fun getFriends(userId: Long): List<Friendship> {
        return friendshipRepository.findByUserId1OrUserId2(userId, userId)
    }
}

data class FriendshipDTO(val id1: Long, val id2: Long)