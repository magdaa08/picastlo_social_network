package com.picastlo.postservice.data

import com.picastlo.postservice.presentation.model.Post
import com.picastlo.postservice.presentation.repository.PostRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DataSeeder {

    @Bean
    fun init(postRepository: PostRepository) = CommandLineRunner {
        val posts = listOf(
            Post(
                image = null,
                text = "Exploring the beauty of nature with this breathtaking sunset photo!",
                pipelineId = 5L,
                visibility = "PUBLIC",
                userId = 1L
            ),
            Post(
                image = null,
                text = "Just finished reading an incredible book on AI and its future implications. Highly recommend it!",
                pipelineId = 3L,
                visibility = "PUBLIC",
                userId = 2L
            ),
            Post(
                image = null,
                text = "Starting a new project on renewable energy. Let’s make the world a greener place!",
                pipelineId = 6L,
                visibility = "PUBLIC",
                userId = 3L
            ),
            Post(
                image = null,
                text = "A throwback to my last adventure in the Alps. Can't wait to go back! 🏔️",
                pipelineId = 1L,
                visibility = "GROUP_ONLY",
                userId = 4L
            ),
            Post(
                image = null,
                text = "Coding late at night has its perks! Found a bug, and squashed it like a pro! 🐛",
                pipelineId = 7L,
                visibility = "PRIVATE",
                userId = 1L
            ),
            Post(
                image = null,
                text = "Celebrating 5 years at my dream job today! Grateful for all the learning and growth. 🙌",
                pipelineId = 10L,
                visibility = "PUBLIC",
                userId = 6L
            ),
            Post(
                image = null,
                text = "Recipe of the day: Homemade pizza with a twist of spicy BBQ sauce. 🍕🔥 Who wants the recipe?",
                pipelineId = 2L,
                visibility = "PUBLIC",
                userId = 7L
            ),
            Post(
                image = null,
                text = "Caught this rare bird in my backyard today. Nature is full of surprises! 🐦",
                pipelineId = 6L,
                visibility = "FRIENDS_ONLY",
                userId = 20L
            ),
            Post(
                image = null,
                text = "Launching a new open-source project for beginner developers. Stay tuned for updates!",
                pipelineId = 18L,
                visibility = "PUBLIC",
                userId = 8L
            ),
            Post(
                image = null,
                text = "Here’s a sneak peek of my upcoming artwork. Let me know your thoughts! 🎨✨",
                pipelineId = 1L,
                visibility = "PUBLIC",
                userId = 1L
            )
        )

        postRepository.saveAll(posts)
    }
}
