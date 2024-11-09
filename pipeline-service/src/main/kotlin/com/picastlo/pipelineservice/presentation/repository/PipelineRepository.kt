package com.picastlo.pipelineservice.presentation.repository

import com.picastlo.pipelineservice.presentation.model.Pipeline
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PipelineRepository: JpaRepository<Pipeline, Long> {
    fun findByOwnerId(ownerId: Long): List<Pipeline>
}