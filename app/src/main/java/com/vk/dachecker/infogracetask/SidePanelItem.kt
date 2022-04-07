package com.vk.dachecker.infogracetask

data class SidePanelItem(
    val imageId: Int,
    val title: String,
    val isDetailOpen:Boolean,
    val switcher : Boolean?,
    val synchronization : String,
    val seekBar : Int,
    val amount: Int,
    val zoom1: Int,
    val zoom2: Int
)
