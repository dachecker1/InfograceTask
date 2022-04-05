package com.vk.dachecker.infogracetask

import android.content.Context

class SidePanelItemFactory(context: Context) {

    private val panelItemList = arrayListOf<SidePanelItem>(
        SidePanelItem(
            imageId = R.drawable.ic_grometry_collection,
            title =   context.getString(R.string.sloy_delyan),
            isDetailOpen = true,
            switcher = false,
            transparency = 60,
            synchronization = context.getString(R.string.sloy_synch_date),
            seekBar = 80,
            amount = 23,
            zoom = 16
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title =  context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = false,
            transparency = 60,
            synchronization = context.getString(R.string.sloy_synch_date),
            seekBar = 80,
            amount = 23,
            zoom = 16
        )
    )

    fun getItems() = panelItemList


}