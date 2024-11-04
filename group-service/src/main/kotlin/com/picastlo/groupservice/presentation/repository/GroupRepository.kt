package com.picastlo.groupservice.presentation.repository

import com.picastlo.groupservice.presentation.model.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long>