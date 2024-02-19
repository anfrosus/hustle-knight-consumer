package com.hustleknight.consumer.service

import com.hustleknight.consumer.dto.BattleInfo
import com.hustleknight.consumer.dto.DropResponseDto
import com.hustleknight.consumer.exception.CustomException
import com.hustleknight.consumer.exception.ErrorCode
import com.hustleknight.consumer.model.ItemDropTable
import com.hustleknight.consumer.model.Player
import com.hustleknight.consumer.model.PlayerItem
import com.hustleknight.consumer.model.enums.ItemCategory
import com.hustleknight.consumer.repository.MonsterRepository
import com.hustleknight.consumer.repository.PlayerItemRepository
import com.hustleknight.consumer.repository.PlayerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BattleService(
    private val playerRepository: PlayerRepository,
    private val monsterRepository: MonsterRepository,
    private val playerItemRepository: PlayerItemRepository
) {
    @Transactional
    fun applyBattleResult(battle: BattleInfo): DropResponseDto {
        val player = playerRepository.findByIdOrNull(battle.battleDto.playerId)
            ?: throw CustomException(ErrorCode.PLAYER_NOT_FOUND)

        val monster = monsterRepository.findByIdFetchDropTable(battle.battleDto.monsterId)
            ?: throw CustomException(ErrorCode.MONSTER_NOT_FOUND)

        val itemDropTable = monster.itemDropTable
        val goodsDropTable = monster.goodsDropTable

        player.earnRewards(monster)
        player.goNextStageIfBoss(monster)
        val playerItemList = createItemInstance(player, itemDropTable)
        playerItemRepository.saveAll(playerItemList)
        player.earnGoods(goodsDropTable)
        playerRepository.save(player)

        return DropResponseDto.fromEntityToDropped(playerItemList, goodsDropTable)
    }

    private fun createItemInstance(player: Player, itemDropTable: List<ItemDropTable>): List<PlayerItem> {
        val droppedItemList = itemDropTable.mapNotNull {
            if (ItemCategory.isDropped(it.dropRate)) {
                it.item
            } else {
                null
            }
        }

        return droppedItemList.map {
            PlayerItem(
                player = player,
                name = it.name,
                reqLevel = it.reqLevel,
                attrName = it.attrName,
                finalAttrValue = ItemCategory.attrWithOption(it.attrValue),
                category = it.category,
                remainingCnt = it.category.upLimit,
            )
        }.toList()
    }
}