package com.picastlo.groupservice.model

import jakarta.persistence.*

@Entity
@Table(name = "groups")
data class Group(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val name: String,

    @ManyToMany
    @JoinTable(
        name = "group_members",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val members: MutableList<User> = mutableListOf()
)