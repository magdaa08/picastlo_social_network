package com.picastlo.userservice.presentation.service


import com.picastlo.userservice.config.security.CanReadResources
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/users")
    interface UserAPI {

    @CanReadResources
    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String, request: HttpServletRequest): UserDTO?

    @CanReadResources
    @GetMapping("/users/{id}")
    fun getUserByID(@PathVariable id: Long): UserDTO?

    @CanReadResources
    @GetMapping("/current")
    fun getCurrentUser(): UserDTO?

    @CanReadResources
    @GetMapping
    fun getAllUsers(): List<UserDTO>

}

data class UserDTO(val id: Long, val username: String, val password: String)