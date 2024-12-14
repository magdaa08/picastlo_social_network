package com.picastlo.postservice

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.picastlo.postservice.config.filters.JWTUtils
import com.picastlo.postservice.config.filters.UserAuthToken
import com.picastlo.postservice.config.filters.UserLogin
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
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
                object : TypeReference<LinkedHashMap<String, String>>() {})
            val principal = UserAuthToken(annotation.username, authorities, capabilities)
            context.authentication = UserAuthToken(annotation.username, authorities, capabilities)
        }
        return context
    }
}

@SpringBootTest
@AutoConfigureMockMvc
class PostServiceApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @Test
    fun contextLoads() {
    }

    @Test
    fun `test public feed`() {
        mockMvc
            .perform(get("/posts/public_feed"))
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "john_doe", roles = ["USER"])
    fun `test private feed`() {

        val username = "john_doe"

        mockMvc
            .perform(get("/posts/$username"))
            .andExpect(status().isOk)
    }

    @Test
    fun `test successful login`() {

        val userLogin = UserLogin("john_doe", "Password@123")

        mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin))
        )
            .andExpect(status().isOk)
    }
}
