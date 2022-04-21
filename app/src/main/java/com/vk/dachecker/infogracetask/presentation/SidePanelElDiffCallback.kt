package com.vk.dachecker.infogracetask.presentation

import androidx.recyclerview.widget.DiffUtil
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class SidePanelElDiffCallback : DiffUtil.ItemCallback<SidePanelItem>() {
    override fun areItemsTheSame(oldItem: SidePanelItem, newItem: SidePanelItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SidePanelItem, newItem: SidePanelItem): Boolean {
        return oldItem == newItem
    }

}