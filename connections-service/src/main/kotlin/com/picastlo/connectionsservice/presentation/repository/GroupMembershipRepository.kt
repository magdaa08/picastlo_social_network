package com.picastlo.connectionsservice.presentation.repository

import com.picastlo.connectionsservice.presentation.model.GroupMembership
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupMembershipRepository: JpaRepository<GroupMembership, Long> {
    fun findByUserId(userId: Long?): List<GroupMembership>
}