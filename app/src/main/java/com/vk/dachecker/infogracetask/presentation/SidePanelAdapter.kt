package com.vk.dachecker.infogracetask.presentation

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.text.bold
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.R
import com.vk.dachecker.infogracetask.databinding.ToolPositionBinding
import com.vk.dachecker.infogracetask.domain.SidePanelItem


class SidePanelAdapter(
    private val viewModel: SidePanelViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<SidePanelAdapter.PanelHolder>() {

    var listItem = listOf<SidePanelItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PanelHolder(view: View) : RecyclerView.ViewHolder(view) {

        var isPanelTouched = false
        private val plusSwitcher = 1
        private val minusSwitcher = -1

        private val binding = ToolPositionBinding.bind(view)

        @SuppressLint("StringFormatInvalid", "StringFormatMatches")
        fun bind(
            sidePanelItem: SidePanelItem,
            viewModel: SidePanelViewModel,
            viewLifecycleOwner: LifecycleOwner,
        ) = with(binding) {

            setClickListeners(binding, sidePanelItem, viewModel)
            setObservers(binding, sidePanelItem, viewModel, viewLifecycleOwner)

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
            } else {
                layerOption.visibility = View.VISIBLE
                imArrow.setImageResource(R.drawable.ic_arrow_up)
            }

            when (sidePanelItem.switcher) {
                true -> {
                    switcher.isChecked = true
                    viewModel.changeCount(plusSwitcher)
                }
                false -> {
                    switcher.isChecked = false
                    viewModel.changeCount(minusSwitcher)
                }
            }

            if (sidePanelItem.isActive) {
                imInvisible.visibility = View.VISIBLE
                mainPanel.alpha = 0.5F
            } else {
                imInvisible.visibility = View.GONE
                mainPanel.alpha = 1F
            }
        }

        private fun setClickListeners(
            binding: ToolPositionBinding,
            item: SidePanelItem,
            viewModel: SidePanelViewModel,
        ) =
            with(binding) {
                imArrow.setOnClickListener {
                    viewModel.editItem(item, SidePanelViewModel.ElementChange.IsDetailOpen)
                }

                tvTitle.setOnClickListener {
                    viewModel.editItem(item, SidePanelViewModel.ElementChange.IsDetailOpen)
                }

                tvTitle.setOnLongClickListener {
                    viewModel.editItem(item, SidePanelViewModel.ElementChange.IsActive)
                    true
                }

                mainPanel.setOnClickListener {

                    val s = SpannableStringBuilder()
                    if (isPanelTouched) {
                        val color = root.resources.getColor(R.color.green)
                        isPanelTouched = false
                        binding.apply {
                        imLineLogo.colorFilter = null
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
                        imLineLogo.setColorFilter(R.color.green)
                            s.append(item.title)
                            s.setSpan(ForegroundColorSpan(color),
                                0, item.title.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            tvTitle.text = s
                        }
                    }
                }

                mainPanel.setOnLongClickListener {
                    viewModel.editItem(item, SidePanelViewModel.ElementChange.IsActive)
                    true
                }

                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seek: SeekBar?,
                        progress: Int,
                        fromUser: Boolean,
                    ) {
                        binding.tvTransparency.text =
                            root.context.getString(R.string.sloy_transparency, progress)
                    }

                    override fun onStartTrackingTouch(seek: SeekBar?) {}

                    override fun onStopTrackingTouch(seek: SeekBar?) {}
                })

                switcher.setOnClickListener {
                    if (switcher.isChecked) {
                        viewModel.editItem(item, SidePanelViewModel.ElementChange.IsSwitcherActive)
                    } else {
                        viewModel.editItem(item, SidePanelViewModel.ElementChange.IsSwitcherActive)
                    }
                }


            }

        private fun setObservers(
            binding: ToolPositionBinding,
            item: SidePanelItem,
            viewModel: SidePanelViewModel,
            viewLifecycleOwner: LifecycleOwner,
        ) {

            viewModel.switcherManager.observe(viewLifecycleOwner) {
                binding.switcher.isChecked = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tool_position,
                parent,
                false
            )
        return PanelHolder(view)
    }

    override fun onBindViewHolder(holder: PanelHolder, position: Int) {
        holder.bind(
            listItem[position],
            viewModel,
            viewLifecycleOwner
        )
        Log.d("MyTag", "reloaded list")
    }

    override fun getItemCount(): Int {
        return listItem.size
    }


}