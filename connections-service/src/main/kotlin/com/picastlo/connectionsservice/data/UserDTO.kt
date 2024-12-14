package com.picastlo.connectionsservice.data

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", url = "\${user.service.url}")
interface UserClient {

    // Fetch a user by their ID
    @GetMapping("/users/{username}")
    fun getUserByUsername(@PathVariable username: String): UserDTO

    @GetMapping("/users/{id}")
    fun getUserByID(@PathVariable id: Long): UserDTO

    @GetMapping("/users/current")
    fun getCurrentUser(): UserDTO

}


data class UserDTO(val id: Long, val username: String, val password: String)