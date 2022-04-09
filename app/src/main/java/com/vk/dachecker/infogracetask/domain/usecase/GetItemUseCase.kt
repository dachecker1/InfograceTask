package com.vk.dachecker.infogracetask.domain.usecase

import com.vk.dachecker.infogracetask.domain.ItemRepository
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class GetItemUseCase(private val itemRepository: ItemRepository) {

    suspend fun getItem(itemId : Int) : SidePanelItem {
        return itemRepository.getItem(itemId)
    }
}