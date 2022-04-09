package com.vk.dachecker.infogracetask.domain.usecase

import androidx.lifecycle.LiveData
import com.vk.dachecker.infogracetask.domain.ItemRepository
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class GetListItemUseCase(private val itemRepository: ItemRepository) {

        fun getItemList() : LiveData<List<SidePanelItem>> {
        return itemRepository.getListItem()
    }
}

