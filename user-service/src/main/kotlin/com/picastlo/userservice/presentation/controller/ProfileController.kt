package com.picastlo.userservice.presentation.controller

import com.picastlo.userservice.presentation.repository.ProfileRepository
import com.picastlo.userservice.presentation.repository.UserRepository
import com.picastlo.userservice.presentation.service.ProfileAPI
import com.picastlo.userservice.presentation.service.ProfileDTO
import com.picastlo.userservice.presentation.service.UserAPI
import com.picastlo.userservice.presentation.service.UserDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class ProfileController(private val profileRepository: ProfileRepository) : ProfileAPI {

    override fun getProfileByUserID(id: Long): ProfileDTO? {
        val profile = profileRepository.findById(id).get()
        return profile.id?.let { ProfileDTO(it, profile.userId, profile.bio, profile.avatar) }
    }

    override fun getAllProfiles(): List<ProfileDTO> {
        val profiles = mutableListOf<ProfileDTO>()
        val repoProfiles = profileRepository.findAll().toList()

        for (profile in repoProfiles) {
            profile.id?.let { ProfileDTO(it, profile.userId, profile.bio, profile.avatar) }?.let { profiles.add(it) }
        }
        return profiles
    }

}
