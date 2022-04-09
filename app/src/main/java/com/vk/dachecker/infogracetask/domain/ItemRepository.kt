package com.vk.dachecker.infogracetask.domain

import android.view.WindowInsets
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun editItem(item : SidePanelItem)

    suspend fun getItem(item: Int) : SidePanelItem

    fun getListItem() : LiveData<List<SidePanelItem>>

//    fun getInfo(text: String) : LiveData<List<SidePanelItem>>

//    fun addListItem(list : List<SidePanelItem>)

    suspend fun addItem(item: SidePanelItem)


}