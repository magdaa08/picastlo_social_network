package com.picastlo.userservice

import com.picastlo.userservice.presentation.model.Profile
import com.picastlo.userservice.presentation.model.User
import com.picastlo.userservice.presentation.repository.ProfileRepository
import com.picastlo.userservice.presentation.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val profileRepository: ProfileRepository
) {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Bean
    fun init() = CommandLineRunner {
        val passwordEncoder = BCryptPasswordEncoder()

        // Seed users and profiles with unique passwords
        val usersData = listOf(
            User(username = "john_doe", passwordHash = passwordEncoder.encode("Password@123")),
            User(username = "jane_smith", passwordHash = passwordEncoder.encode("MySecret#456")),
            User(username = "robert_jones", passwordHash = passwordEncoder.encode("HelloWorld$789")),
            User(username = "emily_brown", passwordHash = passwordEncoder.encode("UniquePass%112")),
            User(username = "michael_clark", passwordHash = passwordEncoder.encode("SecurePass^113")),
            User(username = "sophia_white", passwordHash = passwordEncoder.encode("StrongPass&114")),
            User(username = "william_green", passwordHash = passwordEncoder.encode("AnotherPass*115")),
            User(username = "olivia_martin", passwordHash = passwordEncoder.encode("RandomPass!116")),
            User(username = "liam_johnson", passwordHash = passwordEncoder.encode("SuperSecure(117)")),
            User(username = "noah_thomas", passwordHash = passwordEncoder.encode("CleverPassword)118")),
            User(username = "isabella_lee", passwordHash = passwordEncoder.encode("AmazingPass_119")),
            User(username = "charlotte_hall", passwordHash = passwordEncoder.encode("Different1234")),
            User(username = "jack_miller", passwordHash = passwordEncoder.encode("VeryUnique5678")),
            User(username = "avery_rodriguez", passwordHash = passwordEncoder.encode("SpecialOne91011")),
            User(username = "ethan_sanchez", passwordHash = passwordEncoder.encode("HiddenGem1213")),
            User(username = "madison_king", passwordHash = passwordEncoder.encode("TopSecret1415")),
            User(username = "charles_wood", passwordHash = passwordEncoder.encode("PasswordIs1617")),
            User(username = "harper_adams", passwordHash = passwordEncoder.encode("AnotherUnique1819")),
            User(username = "james_hall", passwordHash = passwordEncoder.encode("MysteryPass2021")),
            User(username = "ella_turner", passwordHash = passwordEncoder.encode("FunAndSafe2223"))
        )

        // Save users
        val savedUsers = userRepository.saveAll(usersData)

        // Seed profiles with unique bios and avatars
        val profilesData = listOf(
            Profile(userId = 1L, bio = "Traveler, coffee lover, and a firm believer in the magic of sunsets. Always up for an adventure!", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=1"),
            Profile(userId = 2L, bio = "Tech enthusiast by day, gamer by night. Exploring the digital world one line of code at a time.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=2"),
            Profile(userId = 3L, bio = "Bookworm, foodie, and cat whisperer. Living life one page at a time and discovering new flavors.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=3"),
            Profile(userId = 4L, bio = "Creative mind with a passion for photography and exploring the world. Always capturing the moments.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=4"),
            Profile(userId = 5L, bio = "Fitness fanatic and health advocate. Pushing limits and finding new ways to stay strong and energized.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=5"),
            Profile(userId = 6L, bio = "Artist at heart, with a love for design and creating visual stories. Color and creativity are my life.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=6"),
            Profile(userId = 7L, bio = "Music lover who believes in the power of melodies to change moods. Music is the soundtrack of my life.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=7"),
            Profile(userId = 8L, bio = "Optimist with a passion for helping others. You’ll find me volunteering, smiling, and making a difference.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=8"),
            Profile(userId = 9L, bio = "Entrepreneur with a passion for turning ideas into reality. Let’s make things happen and build the future.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=9"),
            Profile(userId = 10L, bio = "Nature lover and environmental advocate. Doing my part to protect our planet and enjoy its beauty.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=10"),
            Profile(userId = 11L, bio = "Food explorer, always looking for the next delicious dish. Cooking is my therapy, and I love sharing recipes.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=11"),
            Profile(userId = 12L, bio = "Motivated to inspire and make a positive impact. Believing in kindness, courage, and living authentically.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=12"),
            Profile(userId = 13L, bio = "Gaming enthusiast with a knack for strategy. Always ready to team up and level up with friends.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=13"),
            Profile(userId = 14L, bio = "Passionate about sustainable living and minimalism. Keeping it simple, keeping it green.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=14"),
            Profile(userId = 15L, bio = "Lover of all things vintage and retro. Time traveler at heart, with a collection of stories from the past.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=15"),
            Profile(userId = 16L, bio = "Dog lover and proud pet parent. Life is better with a wagging tail and a loyal friend by your side.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=16"),
            Profile(userId = 17L, bio = "Curious about the universe, stargazing, and all things science. Always learning something new.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=17"),
            Profile(userId = 18L, bio = "Writer of stories, thoughts, and ideas. My pen is my weapon, and words are my power.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=18"),
            Profile(userId = 19L, bio = "Self-taught chef always experimenting with new recipes. From the kitchen to your heart, one dish at a time.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=19"),
            Profile(userId = 20L, bio = "Social butterfly, always finding the best parties, the most interesting people, and the brightest smiles.", avatar = "https://api.dicebear.com/9.x/big-ears/svg?seed=20")
        )

        // Save profiles
        profileRepository.saveAll(profilesData)
    }
}
