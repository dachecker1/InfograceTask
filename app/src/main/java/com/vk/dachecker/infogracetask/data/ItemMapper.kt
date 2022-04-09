package com.vk.dachecker.infogracetask.data

import com.vk.dachecker.infogracetask.domain.SidePanelItem

class ItemMapper {

    fun mapEntityToDbModel(item: SidePanelItem) = ItemDbModel(
        id = item.id,
        itemId = item.itemId,
        imageId = item.imageId,
        title = item.title,
        isDetailOpen = item.isDetailOpen,
        isActive = item.isActive,
        switcher = item.switcher,
        synchronization = item.synchronization,
        seekBar = item.seekBar,
        amount = item.amount,
        zoom1 = item.zoom1,
        zoom2 = item.zoom2

    )

    fun mapDbModelToEntity(itemDbModel: ItemDbModel) = SidePanelItem(
        id = itemDbModel.id,
        itemId = itemDbModel.itemId,
        imageId = itemDbModel.imageId,
        title = itemDbModel.title,
        isDetailOpen = itemDbModel.isDetailOpen,
        isActive = itemDbModel.isActive,
        switcher = itemDbModel.switcher,
        synchronization = itemDbModel.synchronization,
        seekBar = itemDbModel.seekBar,
        amount = itemDbModel.amount,
        zoom1 = itemDbModel.zoom1,
        zoom2 = itemDbModel.zoom2,
    )

    fun mapListDbModelToListEntity(list: List<ItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(list: ArrayList<SidePanelItem>) = list.map {
        mapEntityToDbModel(it)
    }
}