package com.picastlo.connectionsservice.presentation.repository

import com.picastlo.connectionsservice.presentation.model.Friendship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepository: JpaRepository<Friendship, Long> {
    fun findByUserId1OrUserId2(userId1: Long, userId2: Long): List<Friendship>
}