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
            Profile(bio = "A passionate developer who loves coding.", userId = savedUsers[0].id, avatar = "https://example.com/avatars/john.png"),
            Profile(bio = "Coffee lover and digital marketing expert.", userId = savedUsers[1].id, avatar = "https://example.com/avatars/jane.png"),
            Profile(bio = "Outdoor enthusiast and travel junkie.", userId = savedUsers[2].id, avatar = "https://example.com/avatars/robert.png"),
            Profile(bio = "Fitness coach and health enthusiast.", userId = savedUsers[3].id, avatar = "https://example.com/avatars/emily.png"),
            Profile(bio = "Tech geek and AI researcher.", userId = savedUsers[4].id, avatar = "https://example.com/avatars/michael.png"),
            Profile(bio = "Passionate about painting and art.", userId = savedUsers[5].id, avatar = "https://example.com/avatars/sophia.png"),
            Profile(bio = "Avid reader and history buff.", userId = savedUsers[6].id, avatar = "https://example.com/avatars/william.png"),
            Profile(bio = "Enjoys baking and trying out new recipes.", userId = savedUsers[7].id, avatar = "https://example.com/avatars/olivia.png"),
            Profile(bio = "Software engineer with a love for gaming.", userId = savedUsers[8].id, avatar = "https://example.com/avatars/liam.png"),
            Profile(bio = "Aspiring musician and guitarist.", userId = savedUsers[9].id, avatar = "https://example.com/avatars/noah.png"),
            Profile(bio = "Travel blogger sharing stories from around the world.", userId = savedUsers[10].id, avatar = "https://example.com/avatars/isabella.png"),
            Profile(bio = "Nature lover who enjoys hiking and camping.", userId = savedUsers[11].id, avatar = "https://example.com/avatars/charlotte.png"),
            Profile(bio = "Passionate about environmental conservation.", userId = savedUsers[12].id, avatar = "https://example.com/avatars/jack.png"),
            Profile(bio = "Foodie exploring culinary delights.", userId = savedUsers[13].id, avatar = "https://example.com/avatars/avery.png"),
            Profile(bio = "Sports enthusiast and gamer.", userId = savedUsers[14].id, avatar = "https://example.com/avatars/ethan.png"),
            Profile(bio = "Loves yoga and wellness practices.", userId = savedUsers[15].id, avatar = "https://example.com/avatars/madison.png"),
            Profile(bio = "Lifelong learner exploring new technologies.", userId = savedUsers[16].id, avatar = "https://example.com/avatars/charles.png"),
            Profile(bio = "Volunteer at local shelters, loves animals.", userId = savedUsers[17].id, avatar = "https://example.com/avatars/harper.png"),
            Profile(bio = "History teacher with a passion for storytelling.", userId = savedUsers[18].id, avatar = "https://example.com/avatars/james.png"),
            Profile(bio = "Enthusiast of all things fashion and design.", userId = savedUsers[19].id, avatar = "https://example.com/avatars/ella.png")
        )

        // Save profiles
        profileRepository.saveAll(profilesData)
    }
}
