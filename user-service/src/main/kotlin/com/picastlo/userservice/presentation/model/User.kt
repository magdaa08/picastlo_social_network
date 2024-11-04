// User.kt
package com.picastlo.userservice.presentation.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val username: String,
    val passwordHash: String,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "friends",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "friend_id")]
    )
    @JsonIgnore // Prevent circular references during JSON serialization
    val friends: MutableSet<User> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
