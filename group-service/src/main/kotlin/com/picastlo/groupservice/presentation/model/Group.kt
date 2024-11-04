package com.picastlo.groupservice.presentation.model

import jakarta.persistence.*

@Entity
@Table(name = "groups")
data class Group(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val name: String,

    @ElementCollection
    @CollectionTable(
        name = "group_members",
        joinColumns = [JoinColumn(name = "group_id")]
    )
    @Column(name = "user_id")
    val memberIds: MutableList<Long> = mutableListOf()
)