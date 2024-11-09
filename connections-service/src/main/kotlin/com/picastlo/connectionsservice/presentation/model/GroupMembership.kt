package com.picastlo.connectionsservice.presentation.model

import jakarta.persistence.*


@Entity
@Table(name = "group_memberships")
data class GroupMembership (

    @Id
    @GeneratedValue
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    val group: Group,

    val userId: Long? = null,

)