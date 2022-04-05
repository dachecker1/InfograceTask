package com.vk.dachecker.infogracetask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.databinding.ToolPositionBinding

class SidePanelAdapter : RecyclerView.Adapter<SidePanelAdapter.PanelHolder>() {
    var listItem = ArrayList<SidePanelItem>()

    class PanelHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ToolPositionBinding.bind(view)
        fun bind(sidePanelItem: SidePanelItem) = with(binding){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tool_position, parent, false)
        return PanelHolder(view)
    }

    override fun onBindViewHolder(holder: PanelHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }



}