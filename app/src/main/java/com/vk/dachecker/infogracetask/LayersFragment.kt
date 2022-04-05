package com.vk.dachecker.infogracetask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.vk.dachecker.infogracetask.databinding.ActivityMainBinding
import com.vk.dachecker.infogracetask.databinding.FragmentLayersBinding

const val ARG_OBJECT = "object"

class LayersFragment : Fragment() {

    private var _binding: FragmentLayersBinding? = null
    private val binding: FragmentLayersBinding
        get() = _binding ?: throw RuntimeException("FragmentLayersBinding == null")
    private val adapter = SidePanelAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRCAdapter()
    }

    private fun initRCAdapter() {
        val items = SidePanelItemFactory(requireContext())

        binding.apply {
            rcView.layoutManager = LinearLayoutManager(requireContext())
            rcView.adapter = adapter

            adapter.listItem = items.getItems()
        }
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