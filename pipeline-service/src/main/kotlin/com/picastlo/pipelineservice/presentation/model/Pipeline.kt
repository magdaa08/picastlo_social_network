package com.picastlo.pipelineservice.presentation.model

import com.picastlo.pipelineservice.clients.UserDTO
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "pipelines")
data class Pipeline(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @JoinColumn(nullable = false)
    var ownerId: Long,

    @Lob
    @Column(nullable = false)
    val transformations: String,

    @Lob
    val initialImage: ByteArray? = null,

    )
