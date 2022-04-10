package com.vk.dachecker.infogracetask.domain

import android.app.AlertDialog
import android.widget.Toast
import com.vk.dachecker.infogracetask.databinding.ToolSearchDialogBinding
import com.vk.dachecker.infogracetask.presentation.MainActivity
import com.vk.dachecker.infogracetask.presentation.SidePanelViewModel

class DialogHelper(activity: MainActivity, private val viewModel: SidePanelViewModel) {
    private val act = activity

    fun createDialog(){
        val builder = AlertDialog.Builder(act)
        val binding = ToolSearchDialogBinding.inflate(act.layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        binding.btnSearch.setOnClickListener {
            val enteredText =  binding.edSearch.text
            if (enteredText != null && enteredText.length > 2) {
                viewModel.searchInfo(enteredText.toString())
                dialog.dismiss()
            } else {
                Toast.makeText(act, "Продуктолог придумает, что сюда написать", Toast.LENGTH_LONG).show()
            }
        }
        dialog.show()
    }
}