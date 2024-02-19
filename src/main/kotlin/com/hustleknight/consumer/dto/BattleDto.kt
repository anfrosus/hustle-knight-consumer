package com.hustleknight.consumer.dto

import com.hustleknight.consumer.MonsterType

data class BattleDto(
    var playerId: Long,
    var monsterId: Long,
    var stage: Long,
    var monsterType: MonsterType,
    var hasPlayerWon: Boolean,
    var turn: Int,
    var delayTimeMilliSeconds: Long,
    var expectedGold: Long,
    var expectedExp: Long,
    var battleLog: String,
) {
}