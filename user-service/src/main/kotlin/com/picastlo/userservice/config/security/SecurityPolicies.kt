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
    fun canRead(user: Principal): Boolean {
        val capabilities = (user as UserAuthToken).capabilities
        val operation = capabilities["0"]
        return operation != null && lessOrEqual(Operation.READ.toString(), operation)
    }

    private fun lessOrEqual(op1: String, op2: String): Boolean {
        return op1 == op2 ||
                op1 == Operation.NONE.toString() ||
                op2 == Operation.ALL.toString() ||
                (op1 == Operation.READ.toString() && op2 == Operation.WRITE.toString())
    }
}

@PreAuthorize("@capabilitiesService.canRead(principal)")
annotation class CanReadResources
