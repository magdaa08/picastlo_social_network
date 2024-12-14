package com.picastlo.userservice.presentation.controller

import com.picastlo.userservice.presentation.repository.UserRepository
import com.picastlo.userservice.presentation.service.UserAPI
import com.picastlo.userservice.presentation.service.UserDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class UserController(private val userRepository: UserRepository) : UserAPI {

    override fun getUserByUsername(@PathVariable username: String, request: HttpServletRequest): UserDTO? {
        val user = userRepository.findByUsername(username)
        return user.id?.let { UserDTO(it, user.username, user.passwordHash) }
    }

    override fun getUserByID(id: Long): UserDTO? {
        val user = userRepository.findById(id).get()
        return user.id?.let { UserDTO(it, user.username, user.passwordHash) }
    }

    override fun getCurrentUser(): UserDTO? {
        val username = SecurityContextHolder.getContext().authentication.details.toString()
        val user = userRepository.findByUsername(username)
        return user.id?.let { UserDTO(it, user.username, user.passwordHash) }
    }

    override fun getAllUsers(): List<UserDTO> {
        val users = mutableListOf<UserDTO>()
        val repoUsers = userRepository.findAll().toList()

        for (user in repoUsers) {
            user.id?.let { UserDTO(it, user.username, user.passwordHash) }?.let { users.add(it) }
        }
        return users
    }

    @ExceptionHandler(InternalAuthenticationServiceException::class)
    fun handleAuthenticationException(ex: InternalAuthenticationServiceException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid username or password"))
    }
}
