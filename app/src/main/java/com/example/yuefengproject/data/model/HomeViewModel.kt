package com.example.yuefengproject.data.model

import android.app.Application
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.yuefengproject.BaseScaleInTransformer
import com.example.yuefengproject.R
import com.example.yuefengproject.data.entity.StoreCouponEntity
import com.example.yuefengproject.data.repository.HomeRepository
import com.example.yuefengproject.ui.home.HomeAdBannerItemAdapter
import com.to.aboomy.pager2banner.Banner
import com.to.aboomy.pager2banner.IndicatorView

class HomeViewModel (application: Application, private val homeRepository: HomeRepository) :
    AndroidViewModel(application) {
    val adBannerList = MutableLiveData<MutableList<StoreCouponEntity>>() //第一區塊廣告

    private var lastClickTime: Long = 0

    init {
        getADBannerList()

    }

    //首頁的廣告輪播
    private fun getADBannerList(): LiveData<MutableList<StoreCouponEntity>> {
        homeRepository.getAdBannerData(object : HomeRepository.OnActivityBannerFinish{
            override fun onFinish(storeCouponEntity: MutableList<StoreCouponEntity>) {
                adBannerList.postValue(storeCouponEntity)
            }
        })
        return adBannerList
    }

    //首頁的廣告輪播點擊事件
    fun onActivityBannerClick(storeCouponEntity: StoreCouponEntity) {

    }

    companion object {
        //第一區塊-廣告輪播
        @BindingAdapter("activity_banner_model", "activity_banner_adapter", "indicatorView")
        @JvmStatic
        fun setAdBanner(
            bannerLayout: RelativeLayout?,
            homeViewModel: HomeViewModel,
            storeCouponEntityList: MutableList<StoreCouponEntity>?,
            indicator: View
        ) {
            val banner = bannerLayout as Banner
            var adapter = banner.adapter as? HomeAdBannerItemAdapter

            if (adapter == null) {
                adapter = HomeAdBannerItemAdapter()
                banner.setAutoTurningTime(3000)
                    .setIndicator(
                        (indicator.findViewById<IndicatorView>(R.id.indicator2))
                            .setIndicatorColor(Color.WHITE)
                            .setIndicatorSelectorColor(Color.RED), false
                    )
                banner.setPageMargin(40, 40).addPageTransformer(BaseScaleInTransformer())
                banner.adapter = adapter
            }

            if (storeCouponEntityList != null) {
                adapter.setList(storeCouponEntityList, homeViewModel)
            }
        }

        //ImageView的共用設定
        @BindingAdapter("load_image")
        @JvmStatic
        fun loadImageByGlide(imageView: ImageView, url: String) {
            Glide.with(imageView.context.applicationContext)
                .asBitmap()
                .transition(BitmapTransitionOptions.withCrossFade())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        }

    }
}