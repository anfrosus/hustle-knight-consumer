package com.hustleknight.consumer.dto

import java.io.Serializable

data class BattleInfo(
    val battleId: Long,
    val battleDto: BattleDto,
    var isStopped: Boolean,
    var dropped: DropResponseDto,
) : Serializable {

    constructor(battleDto: BattleDto, dropped: DropResponseDto) : this(battleDto.playerId, battleDto, false, dropped) {

    }
}