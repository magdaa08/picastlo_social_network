package com.picastlo.connectionsservice.presentation.model

import jakarta.persistence.*

@Entity
@Table(name = "friendships")
data class Friendship (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val userId1: Long,
    val userId2: Long
)