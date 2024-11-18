package com.picastlo.pipelineservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.picastlo.pipelineservice.presentation.model.Pipeline
import com.picastlo.pipelineservice.presentation.service.PipelineService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PipelineServiceApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var pipelineService: PipelineService

    private lateinit var pipeline: Pipeline

    @Test
    fun contextLoads() {
    }

    @BeforeEach
    fun setUp() {

        // set up a simple pipeline
        pipeline = Pipeline(
            id = 1,
            name = "Pipeline for testing",
            description = "This is a test pipeline",
            ownerId = 1,
            transformations = "Resize 800x600",
            initialImage = ByteArray(0)
        )
    }

    @Test
    fun  `should return pipeline by ID`() {

        Mockito.`when`(pipelineService.getPipelineById(1)).thenReturn(pipeline)

        val result = mockMvc
            .perform(get("/pipelines/{id}", 1))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        assertEquals(result, ObjectMapper().writeValueAsString(pipeline))
    }

    @Test
    fun `should create a new pipeline`() {

        Mockito.`when`(pipelineService.createPipeline(Mockito.any())).thenReturn(pipeline)

        val result = mockMvc
            .perform(post("/pipelines"))
            .andExpect(status().isOk)
            .andReturn().response.contentAsString

        assertEquals(result, ObjectMapper().writeValueAsString(pipeline))

    }

}
