package com.picastlo.userservice.presentation.service

import com.picastlo.userservice.presentation.model.Profile
import com.picastlo.userservice.presentation.repository.ProfileRepository
import com.picastlo.userservice.presentation.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ProfileService(private val profileRepository: ProfileRepository) {
    fun findByUserId(userId: Long): Profile? {
        return profileRepository.findByUserId(userId)
    }
}