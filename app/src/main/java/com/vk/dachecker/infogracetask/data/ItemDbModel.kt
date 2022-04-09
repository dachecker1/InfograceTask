package com.vk.dachecker.infogracetask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val itemId : Int,
    val imageId: Int,
    val title: String,
    val isDetailOpen:Boolean,
    val isActive: Boolean,
    val switcher : Boolean?,
    val synchronization : String,
    val seekBar : Int,
    val amount: Int,
    val zoom1: Int,
    val zoom2: Int
)
