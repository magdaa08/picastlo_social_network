package com.picastlo.connectionsservice.presentation.controller

import com.picastlo.connectionsservice.presentation.model.Friendship
import com.picastlo.connectionsservice.presentation.service.FriendshipDTO
import com.picastlo.connectionsservice.presentation.service.FriendshipService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


/*@RestController
@RequestMapping("/connections")
class FriendshipController(private val friendshipService: FriendshipService) {

    @GetMapping("/friendships/{username}")
    fun getFriendsByUsername(@PathVariable username: String, principal: Principal, request: HttpServletRequest): List<FriendshipDTO> {
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
}*/