package com.vk.dachecker.infogracetask

import android.content.Context

class SidePanelItemFactory(context: Context) {

    private val panelItemList = arrayListOf<SidePanelItem>(
        SidePanelItem(
            imageId = R.drawable.ic_grometry_collection,
            title = context.getString(R.string.sloy_delyan),
            isDetailOpen = false,
            switcher = null,
            synchronization = "12.02.2022",
            seekBar = 60,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18

        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = false,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = false,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = true,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_grometry_collection,
            title = context.getString(R.string.sloy_delyan),
            isDetailOpen = false,
            switcher = false,
            synchronization = "12.02.2022",
            seekBar = 60,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18

        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = false,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = false,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = false,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        ),
        SidePanelItem(
            imageId = R.drawable.ic_waypoint,
            title = context.getString(R.string.sloy_signal_o_lesoizmenenijax),
            isDetailOpen = true,
            switcher = true,
            synchronization = "14.03.2021",
            seekBar = 80,
            amount = 23,
            zoom1 = 16,
            zoom2 = 18
        )
    )

    fun getItems() = panelItemList


}