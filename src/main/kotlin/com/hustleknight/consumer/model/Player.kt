package com.hustleknight.consumer.model

import com.hustleknight.consumer.MonsterType
import com.hustleknight.consumer.exception.CustomException
import com.hustleknight.consumer.exception.ErrorCode
import com.hustleknight.consumer.model.enums.MaxValues
import jakarta.persistence.*

@Entity
@Table(name = "PLAYER", indexes = [Index(name = "IDX_PLAYER_RAID_TIER", columnList = "PLAYER_RAID_TIER")])
class Player(
    @Column(name = "PLAYER_LEVEL")
    var level: Int = 1,

    @Column(name = "PLAYER_AD")
    var atkDmg: Long = 5L,

    @Column(name = "PLAYER_AS")
    var atkSpd: Long = 5,

    @Column(name = "PLAYER_HP")
    var hitPnt: Long = 50L,

    @Column(name = "PLAYER_MAX_STAGE")
    var maxStage: Long = 1L,

    @Column(name = "PLAYER_CUR_STAGE")
    var curStage: Long = 1L,

    @Column(name = "PLAYER_EXP")
    var exp: Long = 0L,

    @Column(name = "PLAYER_GOLD")
    var gold: Long = 0L,

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var playerGoodsList: MutableList<PlayerGoods> = mutableListOf(),

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun earnRewards(monster: Monster) {
        exp += monster.expReward
        levelUp()
        gold += monster.goldReward
    }

    fun goNextStageIfBoss(monster: Monster) {
        if (monster.stage > maxStage) {
            throw CustomException(ErrorCode.PLAYER_ILLEGAL_STAGE)
        } else if (monster.stage == maxStage && monster.type == MonsterType.BOSS) {
            maxStage++
            curStage = maxStage
        }
    }

    fun addPlayerGoods(playerGoods: PlayerGoods) {
        playerGoodsList.add(playerGoods)
    }

    private fun levelUp() {
        var requiredExp = this.requiredExp()
        while (exp >= requiredExp && level < MaxValues.MAX_LEVEL.value) {
            exp -= requiredExp
            level++
            atkDmg += 1
            atkSpd += 1
            hitPnt += 5
            requiredExp = this.requiredExp()
        }
    }

    fun requiredExp(): Long {
        return (level * level * 50).toLong()
    }

    fun earnGoods(goodsDropTable: GoodsDropTable) {
        if (goodsDropTable.isDropped()) {
            this.playerGoodsList.first {
                it.category == goodsDropTable.goods.category
            }.increase(goodsDropTable.amount)
        }
    }
}