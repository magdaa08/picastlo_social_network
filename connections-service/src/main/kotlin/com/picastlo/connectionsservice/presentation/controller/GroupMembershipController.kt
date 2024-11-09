package com.picastlo.connectionsservice.presentation.controller

import com.picastlo.connectionsservice.presentation.model.GroupMembership
import com.picastlo.connectionsservice.presentation.service.GroupMembershipService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/memberships")
class GroupMembershipController(private val groupMembershipService: GroupMembershipService) {

    @GetMapping("/{id}")
    fun getGroupMembershipById(@PathVariable id: Long, principal: Principal): GroupMembership {
        return groupMembershipService.getGroupMembershipById(id)
    }

    @GetMapping("/{username}")
    fun getGroupMembershipByUsername(@PathVariable username: String, principal: Principal): List<GroupMembership> {
        val userDTO = groupMembershipService.getUserDetails(username)
        val groupMemberships = groupMembershipService.getGroupMemberships(userDTO.id)

        return groupMemberships
    }


}

