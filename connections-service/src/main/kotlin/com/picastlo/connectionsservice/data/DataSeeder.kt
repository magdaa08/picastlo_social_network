package com.picastlo.connectionsservice.data

import com.picastlo.connectionsservice.presentation.model.Friendship
import com.picastlo.connectionsservice.presentation.model.Group
import com.picastlo.connectionsservice.presentation.model.GroupMembership
import com.picastlo.connectionsservice.presentation.repository.FriendshipRepository
import com.picastlo.connectionsservice.presentation.repository.GroupMembershipRepository
import com.picastlo.connectionsservice.presentation.repository.GroupRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataSeeder(
    private val friendshipRepository: FriendshipRepository,
    private val groupRepository: GroupRepository,
    private val groupMembershipRepository: GroupMembershipRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        seedFriendships()
        seedGroups()
        seedGroupMemberships()
    }

    private fun seedFriendships() {
        if (friendshipRepository.count() == 0L) {
            val friendships = (1..20).map {
                Friendship(
                    userId1 = Random.nextLong(1, 21),
                    userId2 = Random.nextLong(1, 21)
                ).takeIf { it.userId1 != it.userId2 } // Ensure no self-friendship
            }.filterNotNull()

            friendshipRepository.saveAll(friendships)
        }
    }

    private fun seedGroups() {
        if (groupRepository.count() == 0L) {
            val groupNames = listOf(
                "Nature Enthusiasts", "Art Lovers", "Tech Innovators",
                "Bookworms United", "Movie Buffs", "Fitness Fanatics",
                "Gaming Legends", "Photography Pros", "Travel Wanderers",
                "Music Makers", "Food Critics", "History Buffs",
                "Science Geeks", "Poetry Circle", "DIY Crafters",
                "Pet Lovers", "Adventure Seekers", "Language Learners",
                "Startup Founders", "Sports Fans"
            )

            val groups = groupNames.mapIndexed { _, name ->
                Group(
                    name = name,
                    description = "We are $name! Join us to get involved into our crazy world! Share your images with us!"
                )
            }

            groupRepository.saveAll(groups)
        }
    }

    private fun seedGroupMemberships() {
        if (groupMembershipRepository.count() == 0L) {
            val groups = groupRepository.findAll()
            val memberships = (1..20).map {
                GroupMembership(
                    group = groups.random(),
                    userId = Random.nextLong(1, 21)
                )
            }

            groupMembershipRepository.saveAll(memberships)
        }
    }
}