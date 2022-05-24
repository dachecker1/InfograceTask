package com.vk.dachecker.infogracetask.presentation

import com.vk.dachecker.infogracetask.domain.SidePanelItem

interface ClickListener {
    fun editItem(item: SidePanelItem, change: SidePanelViewModel.ElementChange, progress: Int)
}