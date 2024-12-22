package com.picastlo.userservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.picastlo.userservice.config.filters.JWTUtils
import com.picastlo.userservice.config.filters.UserAuthToken
import com.picastlo.userservice.config.filters.UserLogin
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithSecurityContext
import org.springframework.security.test.context.support.WithSecurityContextFactory
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.*

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
                object : TypeReference<LinkedHashMap<String, String>>() {})
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

    @Autowired
    private lateinit var jwtUtils: JWTUtils

    private val objectMapper = ObjectMapper()

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Test
    fun contextLoads() {
    }

    @Test
    fun `test if user exists`() {
        val username = "john_doe"
        try {
            val user = userDetailsService.loadUserByUsername(username)
            println("User exists: ${user}")
        } catch (ex: UsernameNotFoundException) {
            println("User does not exist")
            fail("User should exist")
        }
    }

    @Test
    fun `test successful login`() {

        val userLogin = UserLogin("john_doe", "Password@123")

        mockMvc.perform(
            post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin))
        )
            .andExpect(status().isOk)
            .andExpect{ System.out.println(it.response.contentAsString) }

        if (jwtUtils.token != null)
        {
            mockMvc
                .perform(get("/users/current").header("Authorization", "Bearer ${jwtUtils.token}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.username").value(userLogin.username))
        } else {
            mockMvc
                .perform(get("/users/current"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.username").value(userLogin.username))
        }
    }

    @Test
    fun `test unsuccessful login`() {
        val userLogin = UserLogin("invalidUser", "wrongPassword")

        mockMvc.perform(
            post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin))
        )

        mockMvc
            .perform(get("/users/current"))
            .andExpect(status().isForbidden)
    }

    @Test
    fun `test get user`() {

        mockMvc
            .perform(get("/users/john_doe"))
            .andExpect(status().isOk)
    }


}
