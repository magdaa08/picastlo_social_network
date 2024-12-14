package com.picastlo.userservice.config.filters

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtils(@Value("\${jwt.secret}") val jwtSecret: String,
               @Value("\${jwt.expiration}") val expiration: Long,
               @Value("\${jwt.subject}") val subject: String) {

    var token: String? = null

    val key = Base64.getEncoder().encodeToString(jwtSecret.toByteArray())

    fun addResponseToken(authentication: Authentication, response: HttpServletResponse) {

        val claims = HashMap<String, Any?>()
        claims["username"] = authentication.name
        if (authentication.name == "guest")
            claims["capabilities"] = listOf(Capability(0L, "READ"))
        else
            claims["capabilities"] = listOf(Capability(0L, "ALL"))


        token = Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        response.addHeader("Authorization", "Bearer $token")
    }
}

data class Capability(val resource:Long, val operation: String)