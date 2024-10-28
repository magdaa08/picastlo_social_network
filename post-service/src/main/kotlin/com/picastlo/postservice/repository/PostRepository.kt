package com.picastlo.postservice.repository

import com.picastlo.postservice.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findByUserId(userId: Long): List<Post>
    fun findAllByVisibility(visibility: String): List<Post>

}
