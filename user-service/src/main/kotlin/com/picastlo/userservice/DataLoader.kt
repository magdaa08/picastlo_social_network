// DataLoader.kt
package com.picastlo.userservice

import com.picastlo.userservice.model.User
import com.picastlo.userservice.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataLoader(private val userRepository: UserRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // List of real names to use for seeding
        val names = listOf(
            "Alice", "Bob", "Charlie", "David", "Eva",
            "Frank", "Grace", "Hannah", "Ian", "Jack",
            "Kathy", "Liam", "Mia", "Noah", "Olivia",
            "Paul", "Quinn", "Ryan", "Sophia", "Tom"
        )

        // Generate unique passwords for each user
        val passwords = names.map { name -> generateRandomPassword(name) }

        // Create and save users with unique passwords
        val users = names.indices.map { index ->
            User(username = names[index].lowercase(), password = passwords[index])
        }
        userRepository.saveAll(users)

        // Create friendships
        for (user in users) {
            // Randomly select 3 to 5 friends from the list of users, excluding self
            val friendsCount = Random.nextInt(3, 6) // Between 3 and 5 friends
            val friends = users.filter { it != user }.shuffled().take(friendsCount)
            user.friends.addAll(friends)
        }

        // Save users again to update friendships
        userRepository.saveAll(users)
    }

    private fun generateRandomPassword(name: String): String {
        // Generate a simple password by appending a random number to the user's name
        val randomNumber = Random.nextInt(1000, 9999)
        return "${name.lowercase()}$randomNumber" // e.g., alice1234
    }
}
