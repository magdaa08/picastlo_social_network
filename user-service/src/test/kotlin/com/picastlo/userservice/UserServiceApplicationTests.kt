package com.picastlo.userservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.picastlo.userservice.config.filters.JWTUtils
import com.picastlo.userservice.config.filters.Operation
import com.picastlo.userservice.config.filters.UserAuthToken
import com.picastlo.userservice.config.filters.UserLogin
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = UserWithCapabilitiesFactory::class)
annotation class WithMockUserAndCapabilities(val username: String, val capabilities: String, val role:String)

class UserWithCapabilitiesFactory : WithSecurityContextFactory<WithMockUserAndCapabilities> {
    override fun createSecurityContext(annotation: WithMockUserAndCapabilities?): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        if (annotation != null ) {
            val authorities = listOf(SimpleGrantedAuthority("ROLE_${annotation.role}"))
            val capabilities = ObjectMapper().readValue(
                annotation.capabilities,
                object : TypeReference<LinkedHashMap<String, Operation>>() {})
            val principal = UserAuthToken(annotation.username, authorities, capabilities)
            context.authentication = UserAuthToken(annotation.username, authorities, capabilities)
        }
        return context
    }
}

@SpringBootTest
@AutoConfigureMockMvc
class ServiceApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    @MockBean
    private lateinit var jwtUtils: JWTUtils

    private val objectMapper = ObjectMapper()

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Test
    fun `test if user exists`() {
        val username = "john_doe" // Replace with your username
        try {
            val user = userDetailsService.loadUserByUsername(username)
            println("User exists: ${user.password}")
            assertNotNull(user) // This will pass if the user exists
        } catch (ex: UsernameNotFoundException) {
            println("User does not exist")
            fail("User should exist")
        }
    }

    @Test
    fun contextLoads() {
    }

    @Test
    @WithMockUserAndCapabilities(
        username = "admin",
        role = "ADMIN",
        capabilities = "{\"*\":\"ALL\"}",
    )
    fun `test successful login`() {
        // Mock user credentials
        val userLogin = UserLogin("john_doe", "Password@123")

        val auth = SecurityContextHolder.getContext().authentication
        System.out.println(auth)

        // Perform the request
        mockMvc.perform(
            post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin))
        )
            .andDo {
                val auth2 = SecurityContextHolder.getContext().authentication
                System.out.println(auth2)
            }


    }

    @Test
    fun `test login`() {
        // Mock user credentials
        val userLogin = UserLogin("john_doe", "Password@123")


        // Perform the request
        mockMvc.perform(
            post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin))
        )
            .andExpect(status().isOk)

    }

    @Test
    fun `test unsuccessful login`() {
        // Mock invalid user credentials
        val userLogin = UserLogin("invalidUser", "wrongPassword")

        // Mock the behavior of authenticationManager.authenticate() to throw a specific exception
        Mockito.`when`(
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(userLogin.username, userLogin.password)
            )
        ).thenThrow(BadCredentialsException("Invalid username or password"))


        // Perform the request
        mockMvc.perform(
            post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin))
        )
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error").value("Invalid username or password"))
    }


}
