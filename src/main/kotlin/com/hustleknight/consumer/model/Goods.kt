package com.hustleknight.consumer.model

import com.hustleknight.consumer.model.enums.GoodsCategory
import jakarta.persistence.*


@Entity
@Table(name = "GOODS")
class Goods(

    @Column(name = "GOODS_CATEGORY")
    @Enumerated(EnumType.STRING)
    var category: GoodsCategory,

    @Column(name = "GOODS_DESCRIPTION")
    var description: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}