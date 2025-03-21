package com.picastlo.connectionsservice.presentation.repository

import com.picastlo.connectionsservice.presentation.model.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long>{
    fun findByName(name: String): Group
}