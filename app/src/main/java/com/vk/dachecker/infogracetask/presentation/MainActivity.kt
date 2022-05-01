package com.vk.dachecker.infogracetask.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vk.dachecker.infogracetask.R
import com.vk.dachecker.infogracetask.databinding.ActivityMainBinding
import com.vk.dachecker.infogracetask.domain.DialogHelper

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SidePanelViewModel
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var dialogHelper: DialogHelper
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")

    lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        viewModel = ViewModelProvider(this)[SidePanelViewModel::class.java]

        setSupportActionBar(binding.activityMainToolbar)
        setToggle()

        initViewPager()
        setObservers()
        setClickListeners()
        fillActivity()

    }

    private fun setToggle() {
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.activityMainToolbar,
            R.string.open,
            R.string.close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }

        binding.activityMainToolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun fillActivity() {
        binding.tvCountVisible.text = getString(
            R.string.tab_count_visible_elements,
            viewModel.invisibleElements.value
        )
    }

    private fun setClickListeners() {

        binding.bottomMenuSwitcher.setOnClickListener {
            viewModel.changeSwitcherStatus()
        }

        binding.btnSearch.setOnClickListener { view ->
            /**
             * вариант поиска со старым дизайном, не стал реализации вырезать
             */
//            dialogHelper = DialogHelper(this, viewModel)
//            dialogHelper.createDialog()

            viewModel.searchMode()
            if (viewModel.searchModeIsActive.value!!) {
                binding.btnSearch.setImageResource(R.drawable.btn_active_search)
                binding.cvSearch.visibility = View.VISIBLE
                textWatcher()
            } else {
                binding.btnSearch.setImageResource(R.drawable.btn_search)
                binding.cvSearch.visibility = View.GONE
            }
        }

        binding.dragList.setOnClickListener { view ->
            viewModel.dragList()
            if (viewModel.dragListIsActive.value!!) {
                view.setBackgroundColor(getColor(R.color.green))
                binding.bottomMenuSwitcher.visibility = View.GONE
            } else {
                binding.bottomMenuSwitcher.visibility = View.VISIBLE
                view.setBackgroundColor(getColor(R.color.dark_blue))
            }
        }
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
        viewPager = binding.pager
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

    private fun textWatcher() {
        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                if (text != null) {
                    viewModel.searchInfo(text.toString())
                } else {
                    viewModel.searchInfo("")
                }
            }

        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}