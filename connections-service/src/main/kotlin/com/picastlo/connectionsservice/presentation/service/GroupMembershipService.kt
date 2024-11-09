package com.picastlo.connectionsservice.presentation.service

import com.picastlo.connectionsservice.config.security.CanReadAllResources
import com.picastlo.connectionsservice.config.security.CanReadOneResource
import com.picastlo.connectionsservice.data.UserClient
import com.picastlo.connectionsservice.data.UserDTO
import com.picastlo.connectionsservice.presentation.model.GroupMembership
import com.picastlo.connectionsservice.presentation.repository.GroupMembershipRepository
import org.springframework.stereotype.Service

@Service
class GroupMembershipService(
    private val groupMembershipRepository: GroupMembershipRepository,
    private val userClient: UserClient
)  {

    fun getUserDetails(username: String): UserDTO {
        // Calling the user service to get the user ID
        return userClient.getUserByUsername(username)
    }

    @CanReadOneResource
    fun getGroupMembershipById(id: Long): GroupMembership {
        return groupMembershipRepository.findById(id).orElseThrow { Exception("Group Membership not found") }
    }

    @CanReadAllResources
    fun getGroupMemberships(userId: Long): List<GroupMembership> {
        return groupMembershipRepository.findByUserId(userId)
    }


}