package com.hustleknight.consumer.repository

import com.hustleknight.consumer.model.PlayerItem
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerItemRepository: JpaRepository<PlayerItem, Long> {
}