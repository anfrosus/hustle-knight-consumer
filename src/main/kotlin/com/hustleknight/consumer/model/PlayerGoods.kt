package com.hustleknight.consumer.model

import com.hustleknight.consumer.exception.CustomException
import com.hustleknight.consumer.exception.ErrorCode
import com.hustleknight.consumer.model.enums.GoodsCategory
import jakarta.persistence.*

@Entity
@Table(name = "PLAYER_GOODS")
class PlayerGoods(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ID")
    var player: Player,

    @Enumerated(EnumType.STRING)
    @Column(name = "PLAYER_GOODS_CATEGORY")
    var category: GoodsCategory,

    @Column(name = "PLAYER_GOODS_AMOUNT")
    var amount: Long

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    init {
        player.addPlayerGoods(this)
    }

    fun increase(long: Long){
        amount += long
    }

}