package com.vk.dachecker.infogracetask.presentation

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.text.bold
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.R
import com.vk.dachecker.infogracetask.databinding.ToolPositionBinding
import com.vk.dachecker.infogracetask.domain.SidePanelItem


class SidePanelAdapter(
    private val viewModel: SidePanelViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val listener: ClickListener,
) : RecyclerView.Adapter<SidePanelAdapter.PanelHolder>() {

    var isDraggable = false

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
            listener: ClickListener,
            isDraggable: Boolean,
        ) = with(binding) {

            setClickListeners(binding, sidePanelItem, viewModel, listener)
//            setObservers(binding, sidePanelItem, viewModel, viewLifecycleOwner)


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


            when (isDraggable) {
                true -> {
                    switcher.visibility = View.GONE
                    imDragElement.visibility = View.VISIBLE
                }
                false -> {
                    switcher.visibility = View.VISIBLE
                    imDragElement.visibility = View.GONE
                }

            }

//            if(sidePanelItem.switcher){
//                viewModel.changeCount(plusSwitcher)
//            }

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
            listener: ClickListener,
        ) =
            with(binding) {
                imArrow.setOnClickListener {
                    listener.editItem(item, SidePanelViewModel.ElementChange.IsDetailOpen, 0)
                }

                tvTitle.setOnClickListener {
                    listener.editItem(item, SidePanelViewModel.ElementChange.IsDetailOpen, 0)
                }

                tvTitle.setOnLongClickListener {
                    listener.editItem(item, SidePanelViewModel.ElementChange.IsActive, 0)
                    true
                }

                mainPanel.setOnClickListener { view ->
                    val unwrappedDrawable =
                        AppCompatResources.getDrawable(binding.root.context, item.imageId)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)


                    val s = SpannableStringBuilder()
                    if (isPanelTouched) {
                        val color = root.resources.getColor(R.color.green)
                        isPanelTouched = false
                        binding.apply {
//                            DrawableCompat.setTint(wrappedDrawable,
//                                binding.root.context.getColor(R.color.white))
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
//                            DrawableCompat.setTint(wrappedDrawable,
//                                binding.root.context.getColor(R.color.green))
                            s.append(item.title)
                            s.setSpan(ForegroundColorSpan(color),
                                0, item.title.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            tvTitle.text = s
                        }
                    }
                }

                imLineLogo.setOnLongClickListener {
                    listener.editItem(item, SidePanelViewModel.ElementChange.IsActive, 0)
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

                    override fun onStopTrackingTouch(seek: SeekBar?) {
                        if (seek != null) {
                            listener.editItem(item,
                                SidePanelViewModel.ElementChange.TransparencyLevel,
                                seek.progress)
                        }
                    }
                })

                switcher.setOnCheckedChangeListener { _, isChecked ->
                    listener.editItem(item,
                        SidePanelViewModel.ElementChange.IsSwitcherActive,
                        0)

                    viewModel.changeCount(isChecked)
                }
            }

        private fun setObservers(
            binding: ToolPositionBinding,
            item: SidePanelItem,
            viewModel: SidePanelViewModel,
            viewLifecycleOwner: LifecycleOwner,
        ) {
//            viewModel.countSwitcher.observe(viewLifecycleOwner) {
//                Log.d("Switcher", "count switchers $it")
//            }

//            viewModel.switcherManager.observe(viewLifecycleOwner) {
//                binding.switcher.isChecked = it
//            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<SidePanelItem>() {
        override fun areItemsTheSame(oldItem: SidePanelItem, newItem: SidePanelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SidePanelItem, newItem: SidePanelItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanelHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.tool_position,
            parent,
            false
        )
        return PanelHolder(view)
    }

    private var onItemClickListener: ((SidePanelItem) -> Unit)? = null
    override fun onBindViewHolder(holder: PanelHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(
            item,
            viewModel,
            viewLifecycleOwner,
            listener,
            isDraggable
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun moveItemInRecyclerViewList(from: Int, to: Int) {
        val list = differ.currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1, fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }
        differ.submitList(list)
    }

    //устанавливаю слушатель на элемент панели меню
    fun setOnItemClickListener(listener: (SidePanelItem) -> Unit) {
        onItemClickListener = listener
    }


}