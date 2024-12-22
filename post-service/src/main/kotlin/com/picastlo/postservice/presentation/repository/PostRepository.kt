package com.picastlo.postservice.presentation.repository

import com.picastlo.postservice.presentation.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findByUserId(userId: Long): List<Post>
    fun findAllByVisibility(visibility: String): List<Post>
    override fun findById(id: Long): Optional<Post>
}
