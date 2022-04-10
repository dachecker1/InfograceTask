package com.vk.dachecker.infogracetask.domain

import androidx.lifecycle.LiveData

interface ItemRepository {

    suspend fun editItem(item : SidePanelItem)

    suspend fun getItem(item: Int) : SidePanelItem

    fun getListItem() : LiveData<List<SidePanelItem>>

    suspend fun addItem(item: SidePanelItem)


}