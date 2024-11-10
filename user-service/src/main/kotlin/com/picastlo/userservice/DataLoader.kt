package com.picastlo.userservice

import com.picastlo.userservice.presentation.model.Profile
import com.picastlo.userservice.presentation.model.User
import com.picastlo.userservice.presentation.repository.ProfileRepository
import com.picastlo.userservice.presentation.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
) {

    @Bean
    fun init() = CommandLineRunner {
        val passwordEncoder = BCryptPasswordEncoder()

        // Seed users and profiles with unique passwords
        val usersData = listOf(
            User(username = "john_doe", passwordHash = passwordEncoder.encode("Password@123")),
            User(username = "jane_smith", passwordHash = passwordEncoder.encode("MySecret#456")),
            User(username = "robert_jones", passwordHash = passwordEncoder.encode("HelloWorld$789"))
        )

        // Save users
        val savedUsers = userRepository.saveAll(usersData)

        // Seed profiles with unique bios and avatars
        val profilesData = listOf(
            Profile(bio = "A passionate developer who loves coding.", userId = savedUsers[0].id, avatar = "https://example.com/avatars/john.png"),
            Profile(bio = "Coffee lover and digital marketing expert.", userId = savedUsers[1].id, avatar = "https://example.com/avatars/jane.png"),
            Profile(bio = "Outdoor enthusiast and travel junkie.", userId = savedUsers[2].id, avatar = "https://example.com/avatars/robert.png")
        )

        // Save profiles
        profileRepository.saveAll(profilesData)
    }
}
