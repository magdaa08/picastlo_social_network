package com.picastlo.userservice.presentation.service

import com.picastlo.userservice.presentation.model.User
import com.picastlo.userservice.presentation.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun findByUsername(username: String): Optional<User>? {
        return userRepository.findByUsername(username)?.let { Optional.of(it) }
    }

    fun getAllUsers(): List<User>? {
        return userRepository.findAll()
    }
}
