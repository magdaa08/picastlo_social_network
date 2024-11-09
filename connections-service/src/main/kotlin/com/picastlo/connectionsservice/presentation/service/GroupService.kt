package com.picastlo.connectionsservice.presentation.service

import com.picastlo.connectionsservice.config.security.CanReadAllResources
import com.picastlo.connectionsservice.config.security.CanReadOneResource
import com.picastlo.connectionsservice.presentation.model.Group
import com.picastlo.connectionsservice.presentation.model.GroupMembership
import com.picastlo.connectionsservice.presentation.repository.GroupMembershipRepository
import com.picastlo.connectionsservice.presentation.repository.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupMembershipRepository: GroupMembershipRepository
) {

    @CanReadOneResource
    fun getGroupById(id: Long): Group {
        return groupRepository.findById(id).orElseThrow { Exception("Group Membership not found") }
    }

    @CanReadAllResources
    fun getGroupByName(name: String): Group {
        return groupRepository.findByName(name)
    }

    fun getGroupMembers(id: Long?): List<GroupMembership>{
        return groupMembershipRepository.findByUserId(id)

    }

}