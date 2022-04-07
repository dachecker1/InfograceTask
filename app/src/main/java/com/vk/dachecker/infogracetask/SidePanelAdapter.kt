package com.vk.dachecker.infogracetask

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.text.bold
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.databinding.ToolPositionBinding


class SidePanelAdapter() : RecyclerView.Adapter<SidePanelAdapter.PanelHolder>() {
    var listItem = ArrayList<SidePanelItem>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is LinearLayoutManager && itemCount > 0) {
            val llm = manager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visiblePosition = llm.findFirstCompletelyVisibleItemPosition()
                    if (visiblePosition > -1) {
                        val v = llm.findViewByPosition(visiblePosition)
                        //do something
                        val first = llm.findFirstVisibleItemPosition()
                        val last = llm.findLastVisibleItemPosition()
                        val firstVisible = llm.findFirstCompletelyVisibleItemPosition()
                        val lastVisible = llm.findLastCompletelyVisibleItemPosition()

                        Log.d("MyTag", "Элемент ${first}")
                        Log.d("MyTag", "Элемент ${last}")
                        Log.d("MyTag", "Элемент ${firstVisible}")
                        Log.d("MyTag", "Элемент ${lastVisible}")

                        v!!.setBackgroundColor(Color.parseColor("#777777"))
                    }
                }
            })
        }
    }


    class PanelHolder(view: View) : RecyclerView.ViewHolder(view) {
        var isPanelTouched = false
        var isActive = true

        private val binding = ToolPositionBinding.bind(view)

        @SuppressLint("StringFormatInvalid", "StringFormatMatches")
        fun bind(sidePanelItem: SidePanelItem) = with(binding) {
            imLineLogo.setImageResource(sidePanelItem.imageId)
            tvTitle.text = sidePanelItem.title
            val transparency = sidePanelItem.seekBar
            tvTransparency.text = root.context.getString(R.string.sloy_transparency, transparency)
            seekBar.progress = sidePanelItem.seekBar
            tvSynchronization.text =
                root.context.getString(R.string.sloy_synch_date, sidePanelItem.synchronization)
            tvAmount.text = root.context.getString(R.string.sloy_elements, sidePanelItem.amount)
            tvZoom.text =
                root.context.getString(R.string.sloy_zoom, sidePanelItem.zoom1, sidePanelItem.zoom2)
            if (!sidePanelItem.isDetailOpen) {
                layerOption.visibility = View.GONE
                imArrow.setImageResource(R.drawable.ic_arrow)
            }
            when (sidePanelItem.switcher) {
                true -> switcher.isChecked = true
                false -> switcher.isChecked = false
                else -> switcher.setButtonDrawable(R.drawable.ic_center_to_gps)
            }

            setClickListeners(binding, sidePanelItem)


        }

        private fun setClickListeners(binding: ToolPositionBinding, item: SidePanelItem) =
            with(binding) {
                imArrow.setOnClickListener {
                    openOptionPanel()
                }

                tvTitle.setOnClickListener {
                    openOptionPanel()
                }

                tvTitle.setOnLongClickListener  {
                    deactivatePanel()
                    true
                }

                mainPanel.setOnClickListener {

                    val s = SpannableStringBuilder()
                    if (isPanelTouched) {
                        val color = root.resources.getColor(R.color.green)
                        isPanelTouched = false
                        binding.apply {
//                        imLineLogo.   <- изменить цвет иконки!!! КАК?
                            s.bold { append(item.title) }
                            s.setSpan(ForegroundColorSpan(color),
                                0, item.title.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            tvTitle.text = s
                        }
                    } else {
                        val color = root.resources.getColor(R.color.white)
                        isPanelTouched = true
                        binding.apply {
//                        imLineLogo.   <- изменить цвет иконки!!! КАК?
                            s.append(item.title)
                            s.setSpan(ForegroundColorSpan(color),
                                0, item.title.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            tvTitle.text = s
                        }
                    }
                }

                mainPanel.setOnLongClickListener  {
                    deactivatePanel()
                    true
                }


                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                        binding.tvTransparency.text = root.context.getString(R.string.sloy_transparency, progress)
                    }
                    override fun onStartTrackingTouch(seek: SeekBar?) {

                    }
                    override fun onStopTrackingTouch(seek: SeekBar?) {

                    }
                } )
            }

        private fun deactivatePanel() = with(binding){
            if(isActive){
                isActive = false
                imInvisible.visibility = View.VISIBLE
                mainPanel.alpha = 0.5F
            } else {
                isActive = true
                imInvisible.visibility = View.GONE
                mainPanel.alpha = 1F
            }
        }

        private fun openOptionPanel() = with(binding) {
            if (layerOption.isVisible) {
                layerOption.visibility = View.GONE
                imArrow.setImageResource(R.drawable.ic_arrow)
            } else {
                layerOption.visibility = View.VISIBLE
                imArrow.setImageResource(R.drawable.ic_arrow_up)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tool_position, parent, false)
        return PanelHolder(view)
    }

    override fun onBindViewHolder(holder: PanelHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }


}