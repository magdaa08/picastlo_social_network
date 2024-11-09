package com.picastlo.pipelineservice.presentation.controller

import com.picastlo.pipelineservice.presentation.model.Pipeline
import com.picastlo.pipelineservice.presentation.service.PipelineService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pipelines")
class PipelineController(private val pipelineService: PipelineService) {

    @PostMapping
    fun createPipeline(@RequestBody pipeline: Pipeline): Pipeline {
        return pipelineService.createPipeline(pipeline)
    }

    @GetMapping("/{id}")
    fun getPipelineById(@PathVariable id: Long): Pipeline {
        return pipelineService.getPipelineById(id)
    }

    @GetMapping("/owner/{ownerId}")
    fun getPipelinesByOwner(@PathVariable ownerId: Long): List<Pipeline> {
        return pipelineService.getPipelinesByOwner(ownerId)
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