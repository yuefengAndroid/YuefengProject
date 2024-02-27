package com.example.yuefengproject.ui.my

import androidx.annotation.DrawableRes

sealed class MyPageRow {

    data class TitleSection(val title: String, val itemList: MutableList<Item>) : MyPageRow()

    data class Item(@DrawableRes val  iconResId: Int, val title: String) : MyPageRow()
}