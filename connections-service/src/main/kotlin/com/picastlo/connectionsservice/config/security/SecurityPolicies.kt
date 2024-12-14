package com.picastlo.connectionsservice.config.security

import com.picastlo.connectionsservice.config.filters.UserAuthToken
import com.picastlo.connectionsservice.data.UserClient
import com.picastlo.connectionsservice.config.filters.Operation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class capabilitiesService(
    private val userClient: UserClient
) {
    fun canReadAll(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities["*"]
        return operation != null && lessOrEqual(Operation.READ.toString(), operation)
    }

    fun canCreate(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities[user.name]
        return operation != null && lessOrEqual(Operation.CREATE.toString(), operation)
    }

    fun canReadOne(user: Principal, id: Long): Boolean {
        val capabilities = (user as UserAuthToken).capabilities

        val resource = userClient.getUserByID(id)

        if (resource.username != user.name) {
            return false
        }

        val operationOne = capabilities[resource.username]
        val operationAll = capabilities["*"]

        return operationOne != null && lessOrEqual(Operation.READ.toString(), operationOne) ||
                operationAll != null && lessOrEqual(Operation.READ.toString(), operationAll)
    }

    private fun lessOrEqual(op1: String, op2: String): Boolean {
        return op1 == op2 ||
                op1 == Operation.NONE.toString() ||
                op2 == Operation.ALL.toString() ||
                (op1 == Operation.READ.toString() && op2 == Operation.WRITE.toString())
    }
}

@PreAuthorize("@capabilitiesService.canReadAll(principal)")
annotation class CanReadAllResources

@PreAuthorize("@capabilitiesService.canCreate(principal)")
annotation class CanCreateResources

@PreAuthorize("@capabilitiesService.canReadOne(principal, #id)")
annotation class CanReadOneResource
