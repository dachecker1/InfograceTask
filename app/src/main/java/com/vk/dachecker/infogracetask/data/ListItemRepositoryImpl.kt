package com.vk.dachecker.infogracetask.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.vk.dachecker.infogracetask.SidePanelItemFactory
import com.vk.dachecker.infogracetask.domain.ItemRepository
import com.vk.dachecker.infogracetask.domain.SidePanelItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListItemRepositoryImpl(val application: Application) : ItemRepository {

    private val listDao = ItemsDataBase.getInstance(application).listDao()
    private val mapper = ItemMapper()

    override suspend fun editItem(item: SidePanelItem) {
        listDao.addItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getItem(itemId: Int): SidePanelItem {
        val dbModel = listDao.getItem(itemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getListItem(): LiveData<List<SidePanelItem>> {
        val list = listDao.getItemsList()
        if (list.isNullOrEmpty()) {
            val factory = SidePanelItemFactory(application)
            GlobalScope.launch {
                factory.getItems().forEach {
                    addItem(it)
                }
            }
        }
        return listDao.getItemsListLiveData().map {
            mapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun addItem(item: SidePanelItem) {
        listDao.addItem(mapper.mapEntityToDbModel(item))
    }


}