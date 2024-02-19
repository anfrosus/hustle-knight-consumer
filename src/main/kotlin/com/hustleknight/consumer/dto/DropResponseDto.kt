package com.hustleknight.consumer.dto

data class DropResponseDto(
    var goods: PlayerGoodsResponseDto,
    var itemList: List<PlayerItemResponseDto> = listOf()
) {
    companion object{
        fun fromEntityToExpect(monster: Monster): DropResponseDto{
            val goodsDropTable = monster.goodsDropTable
            val itemList = monster.itemDropTable.map {
                PlayerItemResponseDto.fromItem(it.item)
            }
            return DropResponseDto(
                goods = PlayerGoodsResponseDto.fromGoodsDropTable(goodsDropTable),
                itemList = itemList
            )
        }

        fun fromEntityToDropped(itemList: List<PlayerItem>, goodsDropTable: GoodsDropTable): DropResponseDto{
            val playerItemList = itemList.map {
                PlayerItemResponseDto.fromEntity(it)
            }

            return DropResponseDto(
                goods = PlayerGoodsResponseDto.fromGoodsDropTable(goodsDropTable),
                itemList = playerItemList
            )
        }
    }

}