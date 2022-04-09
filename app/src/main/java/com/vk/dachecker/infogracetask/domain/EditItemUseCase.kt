package com.vk.dachecker.infogracetask.domain

class EditItemUseCase(private val itemRepository: ItemRepository) {
    suspend fun editItem(item: SidePanelItem) {
        itemRepository.editItem(item)
    }
}