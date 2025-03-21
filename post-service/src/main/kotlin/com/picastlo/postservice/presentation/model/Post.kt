package com.picastlo.postservice.presentation.model

import jakarta.persistence.*
import java.sql.Blob

@Entity
@Table(name = "posts")
data class Post (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Lob
    val image: String?,

    val text: String,

    val pipelineId: Long,

    val visibility: String,

    @Column(name = "user_id")
    val userId: Long
)