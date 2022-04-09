package com.vk.dachecker.infogracetask.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.dachecker.infogracetask.databinding.FragmentLayersBinding
import com.vk.dachecker.infogracetask.domain.CoroutineHelper
import com.vk.dachecker.infogracetask.domain.SidePanelItem
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

const val ARG_OBJECT = "object"

class LayersFragment : Fragment() {

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
        viewModel.getItem()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRCAdapter()


    }

    private fun initRCAdapter() {
        binding.apply {
            adapter = SidePanelAdapter(viewModel, viewLifecycleOwner)

            CoroutineHelper.coroutineScope.launch{
               val result =  async {
                    viewModel.getItemFlow()
                }
                adapter.listItem = result.await()
                Log.d("MyTag", result.await().toString())
           }


            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter
        }

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


    companion object {
        @JvmStatic
        fun newInstance() = LayersFragment()
    }
}