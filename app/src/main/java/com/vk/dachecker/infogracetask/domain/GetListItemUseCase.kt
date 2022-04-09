package com.vk.dachecker.infogracetask.domain

import androidx.lifecycle.LiveData

class GetListItemUseCase(private val itemRepository: ItemRepository) {

    fun getItemList() : LiveData<List<SidePanelItem>> {
        return itemRepository.getListItem()
    }
}