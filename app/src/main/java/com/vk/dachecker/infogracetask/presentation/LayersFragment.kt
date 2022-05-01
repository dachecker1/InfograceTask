package com.vk.dachecker.infogracetask.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.databinding.FragmentLayersBinding
import com.vk.dachecker.infogracetask.domain.SidePanelItem

const val ARG_OBJECT = "object"

class LayersFragment : Fragment(), ClickListener {

    private var _binding: FragmentLayersBinding? = null
    private val binding: FragmentLayersBinding
        get() = _binding ?: throw RuntimeException("FragmentLayersBinding == null")
    private lateinit var adapter: SidePanelAdapter
    private lateinit var viewModel: SidePanelViewModel

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                val adapter = recyclerView.adapter as SidePanelAdapter
                val from = viewHolder.absoluteAdapterPosition
                val to = target.absoluteAdapterPosition
                adapter.moveItemInRecyclerViewList(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f
            }
        }
        ItemTouchHelper(simpleItemTouchCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLayersBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SidePanelViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRCAdapter()
        attachDragAndDrop()

    }


    private fun initRCAdapter() {
        binding.apply {
            adapter = SidePanelAdapter(viewModel, viewLifecycleOwner, this@LayersFragment)

            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter

            viewModel.itemList.observe(viewLifecycleOwner) {
                adapter.differ.submitList(it)
                viewModel.setCounter(it.size)
            }

            viewModel.filtered.observe(viewLifecycleOwner) {
                adapter.differ.submitList(it)
            }

            viewModel.dragListIsActive.observe(viewLifecycleOwner) {
                adapter.isDraggable = it
                adapter.notifyDataSetChanged()

            }
        }

        /**
         * прокрутка. Считаем количество элементов, не влезших в экран
         */
        binding.rcView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val ll = binding.rcView.layoutManager as LinearLayoutManager
                viewModel.amountVisibleElements(
                    first = ll.findFirstVisibleItemPosition(),
                    last = ll.findLastVisibleItemPosition(),
                    firstVisible = ll.findFirstCompletelyVisibleItemPosition(),
                    lastVisible = ll.findLastCompletelyVisibleItemPosition()
                )
            }
        })
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    override fun editItem(
        item: SidePanelItem,
        change: SidePanelViewModel.ElementChange,
        progress: Int,
    ) {
        viewModel.editItem(item, change, progress)
    }

    private fun attachDragAndDrop() {
        try {
            viewModel.dragListIsActive.observe(viewLifecycleOwner) { isActive ->
                if (isActive) {
                    itemTouchHelper.attachToRecyclerView(binding.rcView)
//                    itemTouchHelper.attachToRecyclerView()
                } else {
                    itemTouchHelper.attachToRecyclerView(null)
                }
            }

        } catch (e: Exception) {
            Log.e(null, "something was wrong wint attaching to recyclerView ItemTouchHelper")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LayersFragment()
    }
}