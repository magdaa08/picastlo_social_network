package com.picastlo.connectionsservice.presentation.model

import jakarta.persistence.*

@Entity
@Table(name = "groups")
data class Group(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val name: String,
    val description: String

)