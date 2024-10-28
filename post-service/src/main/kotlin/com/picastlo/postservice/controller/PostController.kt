package com.picastlo.postservice.controller

import com.picastlo.postservice.model.Post
import com.picastlo.postservice.service.PostService
import com.picastlo.postservice.repository.PostRepository

import com.picastlo.userservice.repository.UserRepository

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
//import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("posts")
class PostController(
    val postRepository: PostRepository,
    val userRepository: UserRepository,
    // val jwtTokenProvider: JwtTokenProvider // Uncomment when JWT logic is implemented
) {

    @PostMapping("/new")
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
            user = user // Ensure the user object is correctly retrieved
        )

        postRepository.save(post)

        return "Post created"
    }

    @GetMapping("/public_feed")
    fun getPublicFeed(req: HttpServletRequest): List<Post> {
        return postRepository.findAllByVisibility("PUBLIC") // Ensure this method exists in PostRepository
    }
}
