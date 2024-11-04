package com.picastlo.userservice.presentation.controller


import com.picastlo.userservice.config.security.CanReadOneResource
import com.picastlo.userservice.presentation.model.User
import com.picastlo.userservice.presentation.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    // Endpoint to login user
    @PostMapping("/login")
    fun login() {
        //TODO
    }

    // Endpoint to get user details
    @GetMapping("/{username}")
    @CanReadOneResource()
    fun getUser(@PathVariable username: String): ResponseEntity<User> {
        val user = userService.findByUsername(username) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(user) // Return 200 OK with user data
    }
}
