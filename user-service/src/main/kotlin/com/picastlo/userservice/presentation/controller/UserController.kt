package com.picastlo.userservice.presentation.controller


import com.picastlo.userservice.config.filters.JWTUtils
import com.picastlo.userservice.config.filters.UserLogin
import com.picastlo.userservice.presentation.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException


@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService,
                     private val anAuthenticationManager: AuthenticationManager,
                     private val jwtUtils: JWTUtils
) {

    // Endpoint to login user
    @PostMapping("/login")
    fun login(@RequestBody userLogin: UserLogin, response: HttpServletResponse): ResponseEntity<Any> {
        try {
            // Create authentication token
            val authToken = UsernamePasswordAuthenticationToken(userLogin.username, userLogin.password)
            System.out.println(authToken)
            // Authenticate the user
            val auth = SecurityContextHolder.getContext().authentication
            System.out.println(auth)
            // If authentication is successful, add token to the response
            if (auth != null) {
                val authentication = anAuthenticationManager.authenticate(authToken)
                if (authentication.isAuthenticated) {
                    SecurityContextHolder.getContext().authentication = authentication
                    jwtUtils.addResponseToken(authentication, response)
                    return ResponseEntity.ok(mapOf("message" to "Login successful", "username" to userLogin.username))
                }
            }
        } catch (e: AuthenticationException) {
            System.out.println("HERE IS THE PROBLEM" + e)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid username or password"))
        }
        System.out.println("HERE IS THE PROBLEM?")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid login attempt"))
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleAuthenticationException(ex: BadCredentialsException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "Invalid username or password"))
    }
}
