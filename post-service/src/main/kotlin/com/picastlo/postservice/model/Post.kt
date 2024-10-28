package com.picastlo.postservice.model

import jakarta.persistence.*
import java.sql.Blob

@Entity
@Table(name = "posts")
data class Post (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Lob
    val image: ByteArray?,

    val text: String,

    val pipelineId: String,

    val visibility: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)