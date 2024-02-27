package com.example.yuefengproject.ui.events

import androidx.fragment.app.Fragment
import com.example.yuefengproject.BaseTabViewPager2Adapter

class EventPageAdapter(fragment: Fragment) : BaseTabViewPager2Adapter(fragment) {

    override val size: Int = 4

    override fun initFragment(position: Int): Fragment {
        return when (position) {
            0, 1, 2 -> FoodsPageFragment()
            else -> FoodsPageFragment()
        }
    }
}