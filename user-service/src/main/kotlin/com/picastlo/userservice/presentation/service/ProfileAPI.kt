package com.picastlo.userservice.presentation.service


import com.picastlo.userservice.config.security.CanReadResources
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/users")
interface ProfileAPI {

    @CanReadResources
    @GetMapping("profile/{id}")
    fun getProfileByUserID(@PathVariable id: Long): ProfileDTO?

    @CanReadResources
    @GetMapping("/profiles")
    fun getAllProfiles(): List<ProfileDTO>

}

data class ProfileDTO(val id: Long, val userId: Long?, val bio: String?, val avatar: String?)