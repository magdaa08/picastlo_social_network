package com.picastlo.pipelineservice.presentation.controller

import com.picastlo.pipelineservice.presentation.model.Pipeline
import com.picastlo.pipelineservice.presentation.service.PipelineService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/pipelines")
class PipelineController(private val pipelineService: PipelineService) {

    @GetMapping("/{id}")
    fun getPipelineById(@PathVariable id: Long): Pipeline {
        return pipelineService.getPipelineById(id)
    }

    @PostMapping
    fun createPipeline(@RequestBody pipeline: Pipeline): Pipeline {
        return pipelineService.createPipeline(pipeline)
    }

    @GetMapping("/owner/{username}")
    fun getPipelinesByUsername(@PathVariable username: String): List<Pipeline> {
        val userDTO = pipelineService.getUserDetails(username)
        val pipelines = pipelineService.getPipelinesByOwner(userDTO.id)

        return pipelines
    }

    @PutMapping("/{id}")
    fun updatePipeline(@PathVariable id: Long, @RequestBody updatedPipeline: Pipeline): Pipeline {
        return pipelineService.updatePipeline(id, updatedPipeline)
    }

    @DeleteMapping("/{id}")
    fun deletePipeline(@PathVariable id: Long) {
        return pipelineService.deletePipeline(id)
    }


}