package com.vk.dachecker.infogracetask.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.vk.dachecker.infogracetask.domain.ItemRepository
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class ListItemRepositoryImpl(application: Application) :  ItemRepository{

    private val listDao = ItemsDataBase.getInstance(application).listDao()
    private val mapper = ItemMapper()

    override suspend fun editItem(item: SidePanelItem) {
        listDao.addItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getItem(itemId: Int) : SidePanelItem {
        val dbModel = listDao.getItem(itemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getListItem(): LiveData<List<SidePanelItem>> = MediatorLiveData<List<SidePanelItem>>().apply {
        addSource(listDao.getItemsList()){
            value = mapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun addItem(item: SidePanelItem) {
        listDao.addItem(mapper.mapEntityToDbModel(item))
    }

//    override fun addListItem(list: List<SidePanelItem>) {
//        listDao.addListItem(mapper.mapListEntityToListDbModel(list))
//    }
}