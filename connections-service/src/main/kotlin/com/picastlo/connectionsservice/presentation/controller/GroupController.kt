package com.picastlo.connectionsservice.presentation.controller

import com.picastlo.connectionsservice.presentation.model.Group
import com.picastlo.connectionsservice.presentation.model.GroupMembership
import com.picastlo.connectionsservice.presentation.service.GroupService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/connections")
class GroupController(private val groupService: GroupService) {

    @GetMapping("/groups/{id}")
    fun getGroupById(@PathVariable id: Long, principal: Principal): Group {
        return groupService.getGroupById(id)
    }

    @GetMapping("/groups/{name}")
    fun getGroupByName(@PathVariable name: String, principal: Principal): Group {
        return groupService.getGroupByName(name)
    }

    @GetMapping("/groups/{groupName}/members")
    fun getGroupMembers(@PathVariable groupName: String): List<GroupMembership> {
        val group = groupService.getGroupByName(groupName)
        return groupService.getGroupMembers(group.id)
    }

}