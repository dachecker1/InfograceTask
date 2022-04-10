package com.vk.dachecker.infogracetask.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.databinding.FragmentLayersBinding
import com.vk.dachecker.infogracetask.domain.SidePanelItem
import java.util.*

const val ARG_OBJECT = "object"

class LayersFragment : Fragment() {

    private var touchHelper : ItemTouchHelper? = null
//    private lateinit var list : List<SidePanelItem>

    private var _binding: FragmentLayersBinding? = null
    private val binding: FragmentLayersBinding
        get() = _binding ?: throw RuntimeException("FragmentLayersBinding == null")
    private lateinit var adapter: SidePanelAdapter
    private lateinit var viewModel: SidePanelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLayersBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SidePanelViewModel::class.java]
//        viewModel.setItems()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRCAdapter()


    }

    private fun initRCAdapter() {
        binding.apply {
            adapter = SidePanelAdapter(viewModel, viewLifecycleOwner)

            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter

            viewModel.itemList.observe(viewLifecycleOwner) {
//                list = it
                adapter.listItem = it
                viewModel.setCounter(it.size)
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
//
//         touchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP
//                or ItemTouchHelper.DOWN, 0) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder,
//            ): Boolean {
//                val sourcePosition = viewHolder.absoluteAdapterPosition
//                val targetPosition = target.bindingAdapterPosition
//                Collections.swap(list, sourcePosition, targetPosition)
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            }
//
//        })
//        touchHelper?.attachToRecyclerView(binding.rcView)

        /**
         * реализация drag and drop
         */



        //удалить. неудачная попытка реализации перетаскивания
//        binding.rcView.setOnDragListener( object : View.OnDragListener {
//            override fun onDrag(view: View?, dragEvent: DragEvent?): Boolean {
//                val action = dragEvent?.action
//                when(action) {
//                    DragEvent.ACTION_DRAG_LOCATION -> {
//                        val viewOnTopOf = binding.rcView.findChildViewUnder(dragEvent.x, dragEvent.y)
//                        val i = binding.rcView.getChildAdapterPosition(viewOnTopOf?.rootView!!)
////                        listForAdapter.
//                    }
//                }
//                return true
//            }
//        })

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    companion object {
        @JvmStatic
        fun newInstance() = LayersFragment()
    }
}