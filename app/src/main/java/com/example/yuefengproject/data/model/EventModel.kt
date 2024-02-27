package com.example.yuefengproject.data.model

import android.app.Application
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.yuefengproject.R
import com.example.yuefengproject.data.entity.TabEntity
import com.example.yuefengproject.data.repository.EventRepository
import com.example.yuefengproject.ui.events.EventPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EventModel(
    fragment: Fragment,
    application: Application,
    private val eventRepository: EventRepository
) :
    AndroidViewModel(application) {
    val pageAdapter = MutableLiveData<FragmentStateAdapter>()
    val tabNum = MutableLiveData<Int>()
    val tabTitle = MutableLiveData<MutableList<String>>()
    val tabTitleList: ArrayList<String> = ArrayList()

    init {
        //設定title
        tabTitleList.add(application.getString(R.string.menu_food))
        tabTitleList.add(application.getString(R.string.menu_travel))
        tabTitleList.add(application.getString(R.string.menu_second_hand))
        tabTitleList.add(application.getString(R.string.menu_other))
        tabTitle.value = tabTitleList

        val adapter = EventPageAdapter(fragment)
        pageAdapter.value = adapter

    }

    companion object {
        @BindingAdapter("setTab", "tabViewPager2Adapter", "tabTitle")
        @JvmStatic
        fun setTabLayout(tabLayout: TabLayout, viewPager2: ViewPager2, adapter: FragmentStateAdapter?, tabTitle: MutableList<String>?){
            if (adapter != null){
                viewPager2.adapter = adapter
                TabEntity.tabPosition?.let { viewPager2.setCurrentItem(it, false) }
            }

            viewPager2.offscreenPageLimit = 1
            //TabLayoutMediator 能讓tab 與 viewpager2 互動
            TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
                tab.text = tabTitle?.get(position)
            }.attach()

            //設定tabLayout分隔線
            tabLayout.tabMode = TabLayout.MODE_FIXED //會讓tab 超過4個也不會左右滑動~固定在同一頁
            val linearLayout = tabLayout.getChildAt(0) as LinearLayout
            linearLayout.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            linearLayout.dividerDrawable = ContextCompat.getDrawable(
                tabLayout.context,
                R.drawable.layout_divider_vertical
            )
            linearLayout.dividerPadding = 35

            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    TabEntity.tabPosition = position //記住tab位置
                    tabLayout.setScrollPosition(position,0f,false)
                }

            })
        }

        @BindingAdapter("currentTab" )
        @JvmStatic
        fun setTabPosition(tabLayout: TabLayout, tabNum: MutableLiveData<Int>) {
            if (tabNum.value == null) {
                tabNum.value = TabEntity.tabPosition
            }
        }

        @InverseBindingAdapter(attribute = "currentTab")
        @JvmStatic
        fun getTabPosition(tabLayout: TabLayout) = tabLayout.selectedTabPosition

        @BindingAdapter("currentTabAttrChanged")
        @JvmStatic
        fun setTabSelectedListener(tabLayout: TabLayout, listener: InverseBindingListener) {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    listener.onChange()
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }
}