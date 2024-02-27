package com.example.yuefengproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BaseTabViewPager2Adapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private lateinit var pagerFragment: Fragment
    abstract val size: Int
    abstract fun initFragment(position: Int): Fragment

    override fun getItemCount(): Int {
        return size
    }

    override fun createFragment(position: Int): Fragment {
        pagerFragment = initFragment(position)
        pagerFragment.arguments = Bundle().apply {
            putInt("position", position)
        }
        return pagerFragment
    }

}