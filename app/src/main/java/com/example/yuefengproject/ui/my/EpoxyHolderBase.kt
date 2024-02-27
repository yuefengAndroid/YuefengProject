package com.example.yuefengproject.ui.my

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import com.airbnb.epoxy.EpoxyHolder

abstract class EpoxyHolderBase : EpoxyHolder() {

    lateinit var itemView: View
        private set
    lateinit var context: Context
        private set

    override fun bindView(itemView: View) {
        this.itemView = itemView
        this.context = itemView.context
    }

    protected fun <T : View> bind(@IdRes id: Int): Lazy<T> {
        return lazy { itemView.findViewById(id) as T }
    }
}