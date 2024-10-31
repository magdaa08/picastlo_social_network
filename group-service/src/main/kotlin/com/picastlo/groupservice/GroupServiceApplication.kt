package com.picastlo.groupservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class GroupServiceApplication

fun main(args: Array<String>) {
    runApplication<GroupServiceApplication>(*args)
}
