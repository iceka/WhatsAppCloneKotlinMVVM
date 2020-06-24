package com.iceka.whatsappclonekotlin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.iceka.whatsappclonekotlin.R
import com.iceka.whatsappclonekotlin.databinding.ActivityHomeBinding
import com.iceka.whatsappclonekotlin.ui.home.TabAdapter
import com.iceka.whatsappclonekotlin.ui.home.contact.ContactActivity
import com.iceka.whatsappclonekotlin.ui.home.contact.ContactFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var tabAdapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var fabTop: FloatingActionButton
    private lateinit var fabBottom: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout
        fabTop = binding.fabTop
        fabBottom = binding.fabBottom

        tabLayoutSettings()
        fabSettings()
    }

    private fun fabSettings() {
        if (viewPager.currentItem == 1) {
            fabBottom.setOnClickListener {
                startActivity(Intent(this@HomeActivity, ContactActivity::class.java))
            }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        fabBottom.hide()
                        fabTop.hide()
                    }
                    1 -> {
                        fabBottom.setImageResource(R.drawable.ic_baseline_comment_24)
                        fabTop.hide()
                        fabBottom.show()
                        fabBottom.setOnClickListener {
                            startActivity(Intent(this@HomeActivity, ContactActivity::class.java))
                        }
                    }
                    2 -> {
                        fabTop.hide()
                        fabBottom.setImageResource(R.drawable.ic_baseline_photo_camera_24)
                        fabTop.show()
                        fabBottom.show()
                    }
                    else -> {
                        fabTop.hide()
                        fabBottom.setImageResource(R.drawable.ic_baseline_call_24)
                        fabBottom.show()
                    }
                }
            }

        })
    }

    private fun tabLayoutSettings() {
        tabAdapter = TabAdapter(supportFragmentManager)

        viewPager.offscreenPageLimit = 4
        viewPager.adapter = tabAdapter
        viewPager.currentItem = 1

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)?.icon = getDrawable(R.drawable.ic_baseline_photo_camera_24)

        val layout =
            (tabLayout.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0.5f
        layout.layoutParams = layoutParams
    }
}