package com.vk.dachecker.infogracetask.domain.usecase

import com.vk.dachecker.infogracetask.domain.ItemRepository
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class AddItemUseCase (private val itemRepository: ItemRepository){

    suspend fun addItem(item: SidePanelItem) {
        itemRepository.addItem(item)
    }
}