package com.picastlo.userservice.config


import com.picastlo.userservice.config.filters.JWTAuthenticationFilter
import com.picastlo.userservice.config.filters.JWTUtils
import com.picastlo.userservice.config.filters.UserPasswordAuthenticationFilterToJWT
import com.picastlo.userservice.presentation.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.SecurityContextRepository
import org.springframework.stereotype.Service


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
open class SecurityConfig {

    @Autowired
    private lateinit var utils: JWTUtils

    @Autowired
    private lateinit var myUserDetailService: MyUserDetailsService

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(
        http: HttpSecurity,
        authenticationProvider: DaoAuthenticationProvider,
        securityContextRepository: SecurityContextRepository
    ): SecurityFilterChain {
        http.invoke {
            securityMatcher("/users/**")
            csrf { disable() }
            headers { httpStrictTransportSecurity { disable() } }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeHttpRequests {
                authorize("/users/login", permitAll)
                authorize(anyRequest, authenticated)
            }
            val customFilter = UserPasswordAuthenticationFilterToJWT("/users/login", authenticationProvider, securityContextRepository, utils)
            addFilterBefore<UsernamePasswordAuthenticationFilter>(customFilter)
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JWTAuthenticationFilter(utils))
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return myUserDetailService;
    }



    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    fun securityContextRepository(): SecurityContextRepository {
        return HttpSessionSecurityContextRepository()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}

@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var users: UserService

    override fun loadUserByUsername(username: String?): UserDetails? =
        username?.let {
            users.findByUsername(it)?.map {
                User.builder()
                    .username(it.username)
                    .password(it.passwordHash)
                    .roles("USER")
                    .build()
            }?.orElse(null)
        }
}