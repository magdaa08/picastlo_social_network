package com.picastlo.postservice.presentation.data

import org.apache.catalina.User
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "connections-service", url = "\${connections.service.url}")
interface ConnectionsClient {

    @GetMapping("/friendships/{username}")
    fun getFriendsByUsername(@PathVariable username: String): List<FriendshipDTO>

    @GetMapping("/memberships/{username}")
    fun getGroupsByUsername(@PathVariable username: String): List<GroupDTO>

    @GetMapping("/groups/{groupName}/members")
    fun getGroupMembers(@PathVariable groupName: String): List<UserDTO>

}


data class FriendshipDTO(val id1: Long, val id2: Long)
data class GroupDTO(val name: String, val description: String)