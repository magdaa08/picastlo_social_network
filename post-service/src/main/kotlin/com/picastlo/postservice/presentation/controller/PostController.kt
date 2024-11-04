package com.picastlo.postservice.presentation.controller

import com.picastlo.postservice.config.security.CanCreateResources
import com.picastlo.postservice.config.security.CanReadAllResources
import com.picastlo.postservice.presentation.model.Post
import com.picastlo.postservice.presentation.service.PostService
import com.picastlo.postservice.presentation.repository.PostRepository

//import com.picastlo.userservice.repository.UserRepository

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
//import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("posts")
class PostController(
    val postRepository: PostRepository,
    //val userRepository: UserRepository,
    // val jwtTokenProvider: JwtTokenProvider // Uncomment when JWT logic is implemented
) {

    @PostMapping("/new")
    @CanCreateResources()
    fun createPost(
        @RequestParam("image") image: MultipartFile,
        @RequestParam("text") text: String,
        @RequestParam("pipelineId") pipelineId: String,
        @RequestParam("visibility") visibility: String,
        req: HttpServletRequest
    ): String {
        // Uncomment and implement the logic to retrieve user from JWT token
        // val token = jwtTokenProvider.resolveToken(req)
        // val username = jwtTokenProvider.getUsername(token!!)
        // val user = userRepository.findByUsername(username) ?: throw UserNotFoundException()

        // Create the post object using the retrieved user
        val post = Post(
            image = image.bytes,
            text = text,
            pipelineId = pipelineId,
            visibility = visibility,
            userId = 1
        )

        postRepository.save(post)

        return "Post created"
    }

    @GetMapping("/public_feed")
    @CanReadAllResources()
    fun getPublicFeed(req: HttpServletRequest): List<Post> {
        return postRepository.findAllByVisibility("PUBLIC") // Ensure this method exists in PostRepository
    }
}
