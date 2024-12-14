package com.picastlo.connectionsservice.presentation.service

import com.picastlo.connectionsservice.config.security.CanReadAllResources
import com.picastlo.connectionsservice.config.security.CanReadOneResource
import com.picastlo.connectionsservice.data.UserClient
import com.picastlo.connectionsservice.data.UserDTO
import com.picastlo.connectionsservice.presentation.model.Group
import com.picastlo.connectionsservice.presentation.model.GroupMembership
import com.picastlo.connectionsservice.presentation.repository.GroupMembershipRepository
import com.picastlo.connectionsservice.presentation.repository.GroupRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupMembershipRepository: GroupMembershipRepository,
    private val userClient: UserClient
) {

    fun getGroupById(id: Long): Optional<Group> {
        return groupRepository.findById(id)
    }

    fun getGroupByName(name: String): Group {
        return groupRepository.findByName(name)
    }

    fun getGroupMembers(id: Long?): List<UserDTO>{
        val memberships = groupMembershipRepository.findByUserId(id)
        val users = ArrayList<UserDTO>()

        for (user in memberships){
            user.userId?.let { userClient.getUserByID(it) }?.let { users.add(it) }
        }

        return users
    }

}

data class GroupDTO(val name: String, val description: String)