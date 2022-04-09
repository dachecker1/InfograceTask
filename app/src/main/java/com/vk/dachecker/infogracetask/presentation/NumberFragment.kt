package com.vk.dachecker.infogracetask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.vk.dachecker.infogracetask.R

//const val ARG_OBJECT = "object"
//фрагмент используется для каждого нового экрана
class NumberFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.main_text)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = NumberFragment()
    }
}