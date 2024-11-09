package com.picastlo.pipelineservice.presentation.controller

import com.picastlo.pipelineservice.presentation.model.Pipeline
import com.picastlo.pipelineservice.presentation.service.PipelineService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/pipelines")
class PipelineController(private val pipelineService: PipelineService) {

    @GetMapping("/{id}")
    fun getPipelineById(@PathVariable id: Long, principal: Principal): Pipeline {
        return pipelineService.getPipelineById(id)
    }

    @PostMapping
    fun createPipeline(@RequestBody pipeline: Pipeline, principal: Principal): Pipeline {
        return pipelineService.createPipeline(pipeline)
    }

    @GetMapping("/owner/{ownerId}")
    fun getPipelinesByOwner(@PathVariable ownerId: Long, principal: Principal): List<Pipeline> {
        return pipelineService.getPipelinesByOwner(ownerId)
    }

    @PutMapping("/{id}")
    fun updatePipeline(@PathVariable id: Long, @RequestBody updatedPipeline: Pipeline, principal: Principal): Pipeline {
        return pipelineService.updatePipeline(id, updatedPipeline)
    }

    @DeleteMapping("/{id}")
    fun deletePipeline(@PathVariable id: Long, principal: Principal) {
        return pipelineService.deletePipeline(id)
    }


}