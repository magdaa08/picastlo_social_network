package com.picastlo.userservice.config.security

import com.picastlo.userservice.config.filters.Operation
import com.picastlo.userservice.config.filters.UserAuthToken
import com.picastlo.userservice.presentation.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class capabilitiesService(
    private val userRepository: UserRepository
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

        // Fetch the resource from the database using the provided id
        val resource = userRepository.findById(id).orElse(null) // Handle the case where resource might not be found

        // Check if resource was found
        if (resource == null) {
            return false // Resource does not exist, deny access
        }

        // Compare the owner of the resource with user.name
        if (resource.username != user.name) {
            return false // User does not own the resource, deny access
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
