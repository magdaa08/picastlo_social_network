package com.picastlo.connectionsservice.presentation.controller

import com.picastlo.connectionsservice.data.UserDTO
import com.picastlo.connectionsservice.presentation.model.Group
import com.picastlo.connectionsservice.presentation.service.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/connections")
class ConnectionsController(private val friendshipService: FriendshipService, private val groupService: GroupService, private val groupMembershipService: GroupMembershipService) {
    @GetMapping("/friendships/{username}")
    fun getFriendsByUsername(@PathVariable username: String, request: HttpServletRequest): List<FriendshipDTO> {
        val userDTO = friendshipService.getUserDetails(username)
        val friendships = mutableListOf<FriendshipDTO>()
        val friends = friendshipService.getFriends(userDTO.id)
        for (friend in friends) {
            System.out.println(friend)
            friendships.add(FriendshipDTO(friend.userId1, friend.userId2))
        }
        System.out.println(request.requestURL.toString())
        return friendships
    }
    @GetMapping("/groups/{id}")
    fun getGroupById(@PathVariable id: Long): Optional<Group> {
        return groupService.getGroupById(id)
    }

    @GetMapping("/groups/{name}")
    fun getGroupByName(@PathVariable name: String): Group {
        return groupService.getGroupByName(name)
    }

    @GetMapping("/groups/{groupName}/members")
    fun getGroupMembers(@PathVariable groupName: String): List<UserDTO> {
        val group = groupService.getGroupByName(groupName)
        val groupMembers = groupService.getGroupMembers(group.id)
        return groupMembers
    }

    @GetMapping("/memberships/{username}")
    fun getGroupMembershipByUsername(@PathVariable username: String): List<GroupDTO> {
        val userDTO = groupMembershipService.getUserDetails(username)
        val groups: MutableList<GroupDTO> = mutableListOf()
        val groupMemberships = groupMembershipService.getGroupMemberships(userDTO.id)

        for (group in groupMemberships) {
            groups.add(GroupDTO(group.group.name, group.group.description))
        }

        return groups
    }
}