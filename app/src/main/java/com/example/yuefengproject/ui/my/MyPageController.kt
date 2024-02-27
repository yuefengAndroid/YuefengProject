package com.example.yuefengproject.ui.my

import com.airbnb.epoxy.AsyncEpoxyController

class MyPageController : AsyncEpoxyController() {
    private var list: List<MyPageRow> = emptyList()
    var onItemClickListener: ((data: MyPageRow.Item) -> Unit)? = null

    fun setData(list: List<MyPageRow>){
        this.list = list
        requestModelBuild()
    }
    override fun buildModels() {

        list.forEachIndexed { index, row ->
            when (row) {
                is MyPageRow.TitleSection -> {
                    myServerSection {
                        id(index)
                        title(row.title)
                        list(row.itemList)
                        onClickEventListener(this@MyPageController.onItemClickListener)
                    }
                }
                else -> {}
            }
        }

    }
}