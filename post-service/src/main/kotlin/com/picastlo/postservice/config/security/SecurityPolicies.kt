package com.picastlo.postservice.config.security

import com.picastlo.postservice.config.filters.Operation
import com.picastlo.postservice.config.filters.UserAuthToken
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class capabilitiesService {
    fun canReadAll(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities["0"]
        return operation != null && lessOrEqual(Operation.READ.toString(), operation)
    }

    fun canReadPublic(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities["0"]
        return operation != null && lessOrEqual(Operation.READ_PUBLIC.toString(), operation)
    }

    fun canCreate(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities["0"]
        return operation != null && lessOrEqual(Operation.CREATE.toString(), operation)
    }

    private fun lessOrEqual(op1: String, op2: String): Boolean {
        return op1 == op2 ||
                op1 == Operation.NONE.toString() ||
                op2 == Operation.READ_PUBLIC.toString() ||
                op2 == Operation.ALL.toString() ||
                (op1 == Operation.READ.toString() && op2 == Operation.WRITE.toString())
    }
}

@PreAuthorize("@capabilitiesService.canReadAll(principal)")
annotation class CanReadAllResources

@PreAuthorize("@capabilitiesService.canReadPublic(principal)")
annotation class CanReadPubicResources

@PreAuthorize("@capabilitiesService.canCreate(principal)")
annotation class CanCreateResources
