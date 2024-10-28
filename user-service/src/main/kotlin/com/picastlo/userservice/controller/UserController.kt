package com.picastlo.userservice.controller


import com.picastlo.userservice.model.User
import com.picastlo.userservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    // Endpoint to get user details
    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String): ResponseEntity<User> {
        val user = userService.findByUsername(username) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(user) // Return 200 OK with user data
    }
}
