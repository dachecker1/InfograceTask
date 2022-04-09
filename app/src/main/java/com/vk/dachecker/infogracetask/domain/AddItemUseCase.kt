package com.vk.dachecker.infogracetask.domain

import android.view.WindowInsets

class AddItemUseCase (private val itemRepository: ItemRepository){

    suspend fun addItem(item: SidePanelItem) {
        itemRepository.addItem(item)
    }
}