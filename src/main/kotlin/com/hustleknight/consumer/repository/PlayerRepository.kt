package com.hustleknight.consumer.repository

import com.hustleknight.consumer.model.Player
import org.springframework.data.jpa.repository.JpaRepository

interface PlayerRepository: JpaRepository<Player, Long> {
}