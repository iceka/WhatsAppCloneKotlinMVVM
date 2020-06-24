package com.iceka.whatsappclonekotlin.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.iceka.whatsappclonekotlin.ui.home.call.CallTabFragment
import com.iceka.whatsappclonekotlin.ui.home.camera.CameraTabFragment
import com.iceka.whatsappclonekotlin.ui.home.chat.ChatTabFragment
import com.iceka.whatsappclonekotlin.ui.home.status.StatusTabFragment

class TabAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CameraTabFragment()
            1 -> ChatTabFragment()
            2 -> StatusTabFragment()
            else -> CallTabFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> null
            1 -> "Chats"
            2 -> "Status"
            else -> "Calls"
        }
    }
}