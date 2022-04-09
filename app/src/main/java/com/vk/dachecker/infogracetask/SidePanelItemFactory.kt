package com.vk.dachecker.infogracetask

import android.content.Context
import com.vk.dachecker.infogracetask.domain.SidePanelItem

class SidePanelItemFactory(context: Context) {

    private val panelItemList = arrayListOf<SidePanelItem>(
        SidePanelItem(
            id = 1,
            imageId = R.drawable.ic_grometry_collection,
            title = context.getString(R.string.sloy_delyan),
            isDetailOpen = false,
            isActive = true,
            switcher = null,
            synchronization = "12.02.2022",
            seekBar = 60,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18,
            itemId = 1
        ),
        SidePanelItem(
            id = 2,
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            isActive = true,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18,
            itemId = 2
        ),
        SidePanelItem(
            id = 3,
            imageId = R.drawable.ic_line_border,
            title = context.getString(R.string.sloy_firewall),
            isDetailOpen = false,
            isActive = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18,
            itemId = 3
        )
    )

    fun getItems() = panelItemList


}