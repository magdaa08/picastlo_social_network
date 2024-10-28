package com.picastlo.userservice.service

import com.picastlo.userservice.model.User
import com.picastlo.userservice.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service // Marks this class as a Spring service
class UserService(private val userRepository: UserRepository) {

    // Find user by username
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }
}
