package com.picastlo.groupservice.config.security

import com.picastlo.groupservice.config.filters.Operation
import com.picastlo.groupservice.config.filters.UserAuthToken
import com.picastlo.groupservice.presentation.model.Group
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class capabilitiesService {
    fun canReadAll(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities[0L]
        return operation != null && lessOrEqual(Operation.READ, operation)
    }

    fun canCreate(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities[0L]
        return operation != null && lessOrEqual(Operation.CREATE, operation)
    }

    fun canReadOne(user: Principal, id: Long): Boolean {
        val capabilities = (user as UserAuthToken).capabilities

        val operationOne = capabilities[id]
        val operationAll = capabilities[0L]

        return operationOne != null && lessOrEqual(Operation.READ, operationOne) ||
                operationAll != null && lessOrEqual(Operation.READ, operationAll)
    }

    // In a post authorize
    // TODO: test this
    fun canReadMultipleTrap(user: Principal, resources: List<Group>): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        resources.forEach {
            val operation = capabilities.get(it.id)
            if (operation == null || !lessOrEqual(Operation.READ, operation))
                return false
        }
        return true
    }

    // In a post filter
    // TODO: complete and test this
    fun canReadMultipleFilter(user: Principal, resources: List<Group>): List<Group> {
        val capabilities = (user as UserAuthToken).capabilities
        return resources.map {
            val operation = capabilities.get(it.id)
            if (operation != null && lessOrEqual(Operation.READ, operation)) it else null
        }.mapNotNull { it }
    }

    private fun lessOrEqual(op1: Operation, op2: Operation): Boolean {
        return op1 == op2 ||
                op1 == Operation.NONE ||
                op2 == Operation.ALL ||
                (op1 == Operation.READ && op2 == Operation.WRITE)
    }
}

@PreAuthorize("@capabilitiesService.canReadAll(principal)")
annotation class CanReadAllResources

@PreAuthorize("@capabilitiesService.canCreate(principal)")
annotation class CanCreateResources

@PreAuthorize("@capabilitiesService.canReadOne(principal, #id)")
annotation class CanReadOneResource
