package com.hustleknight.consumer.repository

import com.hustleknight.consumer.model.Monster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MonsterRepository: JpaRepository<Monster, Long> {
    @Query("select m from Monster m left join fetch m.itemDropTable join fetch m.goodsDropTable")
    fun findByIdFetchDropTable(monsterId: Long): Monster?
}