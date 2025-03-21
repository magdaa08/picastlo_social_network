package com.picastlo.pipelineservice.presentation.service

import com.picastlo.pipelineservice.data.UserClient
import com.picastlo.pipelineservice.data.UserDTO
import com.picastlo.pipelineservice.config.security.CanCreateResources
import com.picastlo.pipelineservice.config.security.CanReadAllResources
import com.picastlo.pipelineservice.config.security.CanReadOneResource
import com.picastlo.pipelineservice.presentation.model.Pipeline
import com.picastlo.pipelineservice.presentation.repository.PipelineRepository
import org.springframework.stereotype.Service

@Service
class PipelineService(
    private val pipelineRepository: PipelineRepository,
    private val userClient: UserClient
) {

    fun getUserDetails(username: String): UserDTO {
        // Calling the user service to get the user ID
        return userClient.getUserByUsername(username)
    }

    fun getPipelineById(id: Long): Pipeline {
        return pipelineRepository.findById(id).orElseThrow { Exception("Pipeline not found") }
    }

    fun createPipeline(pipeline: Pipeline): Pipeline {
        return pipelineRepository.save(pipeline)
    }

    fun getPipelinesByOwner(ownerId: Long): List<Pipeline> {
        return pipelineRepository.findByOwnerId(ownerId)
    }

    fun updatePipeline(id: Long, updatedPipeline: Pipeline): Pipeline {
        val existingPipeline = getPipelineById(id)
        return pipelineRepository.save(existingPipeline.copy(
            name = updatedPipeline.name,
            description = updatedPipeline.description,
            transformations = updatedPipeline.transformations,
            initialImage = updatedPipeline.initialImage
        ))
    }

    fun deletePipeline(id: Long) {
        pipelineRepository.deleteById(id)
    }
}