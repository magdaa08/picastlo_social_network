package com.picastlo.userservice.presentation.model

import jakarta.persistence.*

@Entity
@Table(name = "profiles")
data class Profile(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "user_id")
    val userId: Long?,
    val bio: String? = null,
    val avatar: String? = null
)
