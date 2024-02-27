package com.example.yuefengproject.data.model

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.yuefengproject.R
import com.example.yuefengproject.data.entity.MemberInfoEntity
import com.example.yuefengproject.data.repository.MyRepository
import com.example.yuefengproject.ui.my.MyPageRow

class MyViewModel(application: Application, private val myRepository: MyRepository) :
    AndroidViewModel(application) {
    val memberDataList = MutableLiveData<MutableList<MemberInfoEntity>>()
    val list =  MutableLiveData<MutableList<MyPageRow>>()

    init {
        getMemberInfoData("0922123444")
        setData()
    }

    private fun setData() {
        val rowList = mutableListOf<MyPageRow>().apply {
            add(
                MyPageRow.TitleSection(
                    title = "會員服務",
                    itemList = mutableListOf(
                        MyPageRow.Item (
                            iconResId = R.drawable.ui_record,
                            title = "交易記錄"
                        ),
                        MyPageRow.Item (
                            iconResId = R.drawable.ui_point,
                            title = "點數記錄"
                        )
                    )
                )
            )
            add(
                MyPageRow.TitleSection(
                    title = "客戶服務",
                    itemList = mutableListOf(
                        MyPageRow.Item (
                            iconResId = R.drawable.ui_faq,
                            title = "常見問題"
                        ),
                        MyPageRow.Item (
                            iconResId = R.drawable.ui_policy,
                            title = "服務條款"
                        ),
                        MyPageRow.Item (
                            iconResId = R.drawable.ui_mail,
                            title = "客服信箱"
                        )
                    )
                )
            )
        }
        list.value = rowList
    }

    private fun getMemberInfoData(memberPhone: String): LiveData<MutableList<MemberInfoEntity>> {
        myRepository.getMemberInfo(object : MyRepository.OnMemberInfoFinish {
            override fun onFinish(memberInfoEntity: MutableList<MemberInfoEntity>) {
                memberDataList.postValue(memberInfoEntity)
            }

            override fun onFailed(err: String) {
            }
        }, memberPhone)
        return memberDataList
    }

    companion object {
        //MemberIcon ImageView
        //2022 - 由於UI會先跑一次，所以url: String? 要有問號~不然會掛掉
        @BindingAdapter("load_image2")
        @JvmStatic
        fun loadMemberIconImage(imageView: ImageView, url: String?) {
            if (url != null) {
                Glide.with(imageView.context.applicationContext)
                    .asBitmap()
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .circleCrop()
                    .load(url)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView)
            }
        }
    }
}