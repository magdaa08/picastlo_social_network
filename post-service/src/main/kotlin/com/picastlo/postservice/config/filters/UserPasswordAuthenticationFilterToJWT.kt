/**
Copyright 2023 JCS

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.picastlo.postservice.config.filters

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.context.SecurityContextRepository
import java.nio.charset.StandardCharsets


data class UserLogin(var username:String, var password:String) {
    constructor() : this("guest","")
}


class UserPasswordAuthenticationFilterToJWT (
    defaultFilterProcessesUrl: String?,
    val anAuthenticationProvider: DaoAuthenticationProvider?,
    val securityContextRepository: SecurityContextRepository,
    @Autowired val utils: JWTUtils
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(request: HttpServletRequest?,
                                       response: HttpServletResponse?): Authentication? {

        val context = SecurityContextHolder.createEmptyContext()

        val body = String(request!!.inputStream.readAllBytes(), StandardCharsets.UTF_8)

        val user = ObjectMapper().readValue(body, UserLogin::class.java)

        try {
            val auth = anAuthenticationProvider?.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))

            return if (auth?.isAuthenticated == true) {
                context.setAuthentication(auth);
                SecurityContextHolder.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                auth
            } else
                null
        } catch (ex: InternalAuthenticationServiceException) {
            return null
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          filterChain: FilterChain?,
                                          auth: Authentication) {

        utils.addResponseToken(auth, response)
    }
}