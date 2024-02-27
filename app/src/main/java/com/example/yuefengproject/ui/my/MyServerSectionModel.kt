package com.example.yuefengproject.ui.my

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isInvisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.yuefengproject.R
import com.example.yuefengproject.databinding.ItemMySubTitleBinding

@SuppressLint("NonConstantResourceId")
@EpoxyModelClass
abstract class MyServerSectionModel : EpoxyModelWithHolder<MyServerSectionModel.ViewHolder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.item_myserver_view
    }

    @EpoxyAttribute
    var title: String? = null

    @EpoxyAttribute
    var list: List<MyPageRow.Item>? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClickEventListener: ((MyPageRow.Item) -> Unit)? = null

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        holder.item01.text  = title

        /** 建立子項目*/
        val layoutInflater = LayoutInflater.from(holder.context)
        holder.subItemContainer.removeAllViews()
        val subSize = list?.size ?: 1
        list?.forEachIndexed { index, item ->
            val itemMySubTitleBinding = ItemMySubTitleBinding.inflate(layoutInflater)
            itemMySubTitleBinding.subItemContainer.setOnClickListener {
                onClickEventListener?.invoke(item)
            }
            itemMySubTitleBinding.icon.setImageResource(item.iconResId)
            itemMySubTitleBinding.itemSubTitle.text = item.title
            holder.subItemContainer.addView(itemMySubTitleBinding.root)

            /** 最後一筆, 隱藏底線 */
            if (index == subSize - 1) {
                itemMySubTitleBinding.divider.isInvisible = true
            }
        }
    }

    class ViewHolder : EpoxyHolderBase() {
        val item01 by bind<TextView>(R.id.item01)
        val subItemContainer by bind<LinearLayout>(R.id.sub_title_container)
    }
}