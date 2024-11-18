package com.picastlo.userservice.presentation.controller


import com.picastlo.userservice.config.filters.JWTUtils
import com.picastlo.userservice.config.filters.UserLogin
import com.picastlo.userservice.presentation.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException


@RestController
@RequestMapping("/users")
class UserController(private val securityContextRepository: SecurityContextRepository,
                     private val anAuthenticationProvider: DaoAuthenticationProvider,
                     private val jwtUtils: JWTUtils
) {

    var token: String? = null

    // Endpoint to login user
    @PostMapping("/users/login")
    fun login(@RequestBody userLogin: UserLogin, response: HttpServletResponse, request: HttpServletRequest): ResponseEntity<Any> {
        val context = SecurityContextHolder.createEmptyContext()
        try {
            val authToken = UsernamePasswordAuthenticationToken(userLogin.username, userLogin.password)
            val auth = SecurityContextHolder.getContext().authentication
            if (auth != null) {
                val authentication = anAuthenticationProvider.authenticate(authToken)
                if (authentication.isAuthenticated) {
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                    securityContextRepository.saveContext(context, request, response);
                    jwtUtils.addResponseToken(authentication, response)
                    token = response.getHeader("Authorization")
                    return ResponseEntity.ok(mapOf("message" to "Login successful", "username" to userLogin.username, "token" to token))
                }
            }
        } catch (e: InternalAuthenticationServiceException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid username or password"))
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid login attempt"))
    }

    @GetMapping("/current")
    fun getCurrentUser(): ResponseEntity<Any> {
        return ResponseEntity.ok(mapOf("username" to SecurityContextHolder.getContext().authentication.details.toString()))
    }

    @ExceptionHandler(InternalAuthenticationServiceException::class)
    fun handleAuthenticationException(ex: InternalAuthenticationServiceException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid username or password"))
    }
}
