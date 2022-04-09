package com.vk.dachecker.infogracetask.domain.usecase

import com.vk.dachecker.infogracetask.domain.ItemRepository
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class EditItemUseCase(private val itemRepository: ItemRepository) {
    suspend fun editItem(item: SidePanelItem) {
        itemRepository.editItem(item)
    }
}