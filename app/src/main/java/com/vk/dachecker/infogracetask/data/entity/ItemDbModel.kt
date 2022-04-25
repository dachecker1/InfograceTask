package com.vk.dachecker.infogracetask.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item")
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "itemId")
    val itemId: Int,
    @ColumnInfo(name = "imageId")
    val imageId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "isDetailOpen")
    val isDetailOpen: Boolean,
    @ColumnInfo(name = "isActive")
    val isActive: Boolean,
    @ColumnInfo(name = "switcher")
    val switcher: Boolean,
    @ColumnInfo(name = "synchronization")
    val synchronization: String,
    @ColumnInfo(name = "seekBar")
    val seekBar: Int,
    @ColumnInfo(name = "amount")
    val amount: Int,
    @ColumnInfo(name = "zoom1")
    val zoom1: Int,
    @ColumnInfo(name = "zoom2")
    val zoom2: Int,
    @ColumnInfo(name = "isDrag")
    val isDrag: Boolean,
) : Serializable
