package com.picastlo.userservice.service

import com.picastlo.userservice.model.Profile
import com.picastlo.userservice.repository.ProfileRepository
import com.picastlo.userservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ProfileService(private val profileRepository: ProfileRepository) {
    fun findByUserId(userId: Long): Profile? {
        return profileRepository.findByUserId(userId)
    }
}