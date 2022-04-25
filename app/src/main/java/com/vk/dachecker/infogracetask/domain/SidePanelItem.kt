package com.vk.dachecker.infogracetask.domain

data class SidePanelItem(
    val id: Int = UNDEFINED_ID,
    val itemId: Int,
    val imageId: Int,
    val title: String,
    val isDetailOpen: Boolean,
    val isActive: Boolean,
    val switcher: Boolean,
    val synchronization: String,
    val seekBar: Int,
    val amount: Int,
    val zoom1: Int,
    val zoom2: Int,
    val isDrag: Boolean,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
