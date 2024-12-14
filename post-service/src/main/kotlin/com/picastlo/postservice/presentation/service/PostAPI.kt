package com.picastlo.postservice.presentation.service

import com.picastlo.postservice.config.security.CanCreateResources
import com.picastlo.postservice.config.security.CanReadAllResources
import com.picastlo.postservice.config.security.CanReadPubicResources
import com.picastlo.postservice.presentation.model.Post
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("/posts")
interface PostAPI {
    @CanCreateResources
    @PostMapping("/new")
    fun createPost(@RequestBody post: Post, principal: Principal): Post

    @CanCreateResources
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long)

    @CanReadPubicResources
    @GetMapping("/public_feed")
    fun getPublicFeed(req: HttpServletRequest): List<Post>

    @CanReadAllResources
    @GetMapping("/{username}")
    fun getPostsByUsername(@PathVariable username: String, principal: Principal): List<Post>
}