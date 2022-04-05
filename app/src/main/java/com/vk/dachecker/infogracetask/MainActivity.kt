package com.vk.dachecker.infogracetask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.vk.dachecker.infogracetask.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
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