package com.picastlo.userservice.presentation.service


import com.picastlo.userservice.config.security.CanReadResources
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/profiles")
interface ProfileAPI {

    @CanReadResources
    @GetMapping("/{id}")
    fun getProfileByUserID(@PathVariable id: Long): ProfileDTO?

    @CanReadResources
    @GetMapping
    fun getAllProfiles(): List<ProfileDTO>

}

data class ProfileDTO(val id: Long, val userId: Long?, val bio: String?, val avatar: String?)