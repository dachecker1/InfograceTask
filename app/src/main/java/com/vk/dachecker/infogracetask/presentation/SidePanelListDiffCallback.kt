package com.vk.dachecker.infogracetask.presentation

import androidx.recyclerview.widget.DiffUtil
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class SidePanelListDiffCallback(
    private val oldList: List<SidePanelItem>,
    private val newList: List<SidePanelItem>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}