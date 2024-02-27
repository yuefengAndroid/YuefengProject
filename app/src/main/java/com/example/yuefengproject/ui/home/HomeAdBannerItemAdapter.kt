package com.example.yuefengproject.ui.home

import androidx.lifecycle.AndroidViewModel
import com.example.yuefengproject.BaseRecyclerViewAdapter
import com.example.yuefengproject.R
import com.example.yuefengproject.data.entity.StoreCouponEntity
import com.example.yuefengproject.data.model.HomeViewModel
import com.example.yuefengproject.databinding.RecyclerviewActivityBannerItemBinding

class HomeAdBannerItemAdapter : BaseRecyclerViewAdapter<StoreCouponEntity, RecyclerviewActivityBannerItemBinding>(){

    override val resId: Int = R.layout.recyclerview_activity_banner_item

    override fun bindData(
        holder: BaseViewHolder<RecyclerviewActivityBannerItemBinding>,
        entity: StoreCouponEntity,
        viewModel: AndroidViewModel
    ) {
        holder.binding?.homeViewModel = viewModel as HomeViewModel
        holder.binding?.adBannerEntity = entity
        holder.binding?.executePendingBindings()
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<RecyclerviewActivityBannerItemBinding>,
        position: Int
    ) {
        val entity = itemList[position]
        bindData(holder, entity, itemViewModel)
    }
}