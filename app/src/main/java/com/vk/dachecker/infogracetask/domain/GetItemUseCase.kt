package com.vk.dachecker.infogracetask.domain

class GetItemUseCase(private val itemRepository: ItemRepository) {

    suspend fun getItem(itemId : Int) : SidePanelItem {
        return itemRepository.getItem(itemId)
    }
}