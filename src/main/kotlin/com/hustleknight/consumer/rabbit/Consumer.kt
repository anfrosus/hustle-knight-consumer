package com.hustleknight.consumer.rabbit

import com.hustleknight.consumer.redis.RedisService
import com.hustleknight.consumer.service.BattleService
import com.hustleknight.consumer.sse.SseService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.http.HttpStatus

class Consumer(
    private val redisService: RedisService,
    private val sseService: SseService,
    private val battleService: BattleService,
) {

    @RabbitListener(queues = ["test-queue"])
    fun handleMessage(battleId: Long) {
        try {
            val battleInfo = redisService.getBattleInfo(battleId)
            if (!battleInfo.isStopped) {
                //전투가 중단되지 않았다면
                val dropResponseDto = battleService.applyBattleResult(battleInfo)
                battleInfo.dropped = dropResponseDto

                redisService.checkEmitterExistence(battleId)
                sseService.checkHasEmitter(battleId)
                sseService.pushBattleResult(battleId, battleInfo)
            }
        } catch (e: Exception) {
            //TODO: 실패한 battle에 대한 정보를 따로 저장하던지 의 조치가 필요
//            log.error(e)
        } finally {
            redisService.deleteBattleInfo(battleId)
        }
    }
}