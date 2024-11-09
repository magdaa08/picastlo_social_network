package com.picastlo.connectionsservice.presentation.controller

import com.picastlo.connectionsservice.presentation.model.Friendship
import com.picastlo.connectionsservice.presentation.service.FriendshipService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/friendships")
class FriendshipController(private val friendshipService: FriendshipService) {

    @GetMapping("/{id}")
    fun getFriendshipById(@PathVariable id: Long, principal: Principal): Friendship {
        return friendshipService.getFriendshipById(id)
    }

    @GetMapping("/{username}")
    fun getFriendsByUsername(@PathVariable username: String, principal: Principal): List<Friendship> {
        val userDTO = friendshipService.getUserDetails(username)
        val friendships = friendshipService.getFriends(userDTO.id)

        return friendships
    }
}