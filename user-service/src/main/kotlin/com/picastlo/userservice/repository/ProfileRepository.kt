package com.picastlo.userservice.repository

import com.picastlo.userservice.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByUserId(userId: Long): Profile?
}
