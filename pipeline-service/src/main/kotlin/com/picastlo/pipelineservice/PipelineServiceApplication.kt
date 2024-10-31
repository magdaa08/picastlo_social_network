package com.picastlo.pipelineservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PipelineServiceApplication

fun main(args: Array<String>) {
    runApplication<PipelineServiceApplication>(*args)
}
