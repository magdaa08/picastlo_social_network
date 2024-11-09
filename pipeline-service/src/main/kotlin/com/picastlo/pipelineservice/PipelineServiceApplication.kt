package com.picastlo.pipelineservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
class PipelineServiceApplication

fun main(args: Array<String>) {
    runApplication<PipelineServiceApplication>(*args)
}
