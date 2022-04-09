package com.vk.dachecker.infogracetask.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vk.dachecker.infogracetask.R
import com.vk.dachecker.infogracetask.databinding.ActivityMainBinding
import com.vk.dachecker.infogracetask.domain.DialogHelper

class MainActivity : FragmentActivity() {
    private lateinit var viewModel : SidePanelViewModel
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
//    private val dialogHelper = DialogHelper(this, viewModel)
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SidePanelViewModel::class.java]

        initViewPager()
        setObservers()
        setClickListeners()
        fillActivity()
    }

    private fun fillActivity(){
        binding.tvCountVisible.text = getString(
            R.string.tab_count_visible_elements,
            viewModel.invisibleElements.value
        )
    }

    private fun setClickListeners() {
        binding.bottomMenuSwitcher.setOnClickListener {
            viewModel.changeSwitcherStatus()
        }

//        binding.btnSearch.setOnClickListener {
//            dialogHelper.createDialog()
//        }
    }

    private fun setObservers() = with(viewModel){
        switcherManager.observe(this@MainActivity) {
            binding.bottomMenuSwitcher.isChecked = it
        }

        invisibleElements.observe(this@MainActivity) {
            binding.tvCountVisible.text = getString(R.string.tab_count_visible_elements, it)
        }


    }

    private fun initViewPager() {
        adapter = NumberAdapter(this)
        viewPager = findViewById(R.id.pager) //переделать на binding
        viewPager.adapter = adapter
        val tabNames: Array<String> = arrayOf(
            resources.getString(R.string.tab_layer),
            resources.getString(R.string.tab_podlozhki),
            resources.getString(R.string.tab_missions)
        )

        //tab.text = название вкладки
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}