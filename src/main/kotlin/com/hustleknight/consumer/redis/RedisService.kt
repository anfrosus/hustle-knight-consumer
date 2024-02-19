package com.hustleknight.consumer.redis

import com.hustleknight.consumer.dto.BattleInfo
import com.hustleknight.consumer.exception.CustomException
import com.hustleknight.consumer.exception.ErrorCode
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val battleRedisTemplate: RedisTemplate<Long, BattleInfo>,
    private val emitterRedisTemplate: RedisTemplate<String, Boolean>
) {

    fun getBattleInfo(battleId: Long): BattleInfo {
        val result = battleRedisTemplate.opsForValue().get(battleId)
            ?: throw CustomException(ErrorCode.REDIS_BATTLE_NOT_FOUND)

        return result
    }

    fun deleteBattleInfo(battleId: Long) {
        if (!battleRedisTemplate.delete(battleId)) {
            throw CustomException(ErrorCode.REDIS_BATTLE_NOT_FOUND)
        }
    }

    fun setEmitterExistence(playerId: Long) {
        val key = emitterKey(playerId)
        when (emitterRedisTemplate.opsForValue().setIfAbsent(key, true)) {
            true -> {}
            false -> throw CustomException(ErrorCode.REDIS_EMITTER_ALREADY_EXIST)
            null -> throw CustomException(ErrorCode.REDIS_CAN_NOT_SAVE)
        }
    }

    fun deleteEmitterExistence(playerId: Long) {
        val key = emitterKey(playerId)
        emitterRedisTemplate.delete(key)
    }

    fun checkEmitterExistence(playerId: Long) {
        val key = emitterKey(playerId)
        if (!emitterRedisTemplate.hasKey(key)) {
            throw CustomException(ErrorCode.EMITTER_NOT_FOUND)
        }
    }

    private fun emitterKey(playerId: Long): String {
        val EMITTER_KEY_PREFIX = "Emitter"
        return EMITTER_KEY_PREFIX + playerId.toString()
    }
}