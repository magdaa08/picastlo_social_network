package com.picastlo.userservice.config


import com.picastlo.userservice.config.filters.JWTAuthenticationFilter
import com.picastlo.userservice.config.filters.JWTUtils
import com.picastlo.userservice.config.filters.UserPasswordAuthenticationFilterToJWT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
open class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    open fun securityFilterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager,
        utils: JWTUtils
    ): SecurityFilterChain {
        http.invoke {
            csrf { disable() }
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<BasicAuthenticationFilter>(UserPasswordAuthenticationFilterToJWT("/users/login", authenticationManager, utils))
            addFilterBefore<BasicAuthenticationFilter>(JWTAuthenticationFilter(utils))
            httpBasic { }
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user1 = User.builder()
            .username("john_doe")
            .password(BCryptPasswordEncoder().encode("Password@123"))
            .roles("USER")
            .build()
        val user2 = User.builder()
            .username("jane_smith")
            .password(BCryptPasswordEncoder().encode("MySecret#456"))
            .roles("USER")
            .build()
        val user3 = User.builder()
            .username("robert_jones")
            .password(BCryptPasswordEncoder().encode("HelloWorld$789"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user1,user2,user3)
    }

    @Bean
    fun authenticationManager(): AuthenticationManager? {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(BCryptPasswordEncoder())

        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setPasswordEncoder(BCryptPasswordEncoder())
        authenticationProvider.setUserDetailsService(userDetailsService())
        return authenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}