package com.hustleknight.consumer.dto

import com.hustleknight.consumer.model.GoodsDropTable
import com.hustleknight.consumer.model.PlayerGoods
import com.hustleknight.consumer.model.enums.GoodsCategory

data class PlayerGoodsResponseDto(
    val goodsCategory: GoodsCategory,
    val amount: Long
) {
    companion object {
        fun fromEntity(playerGoods: PlayerGoods): PlayerGoodsResponseDto {
            return PlayerGoodsResponseDto(
                goodsCategory = playerGoods.category,
                amount = playerGoods.amount
            )
        }

        fun fromGoodsDropTable(goodsDropTable: GoodsDropTable): PlayerGoodsResponseDto {
            return PlayerGoodsResponseDto(
                goodsCategory = goodsDropTable.goods.category,
                amount = goodsDropTable.amount
            )
        }
    }

}