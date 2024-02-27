package com.example.yuefengproject.ui.my

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yuefengproject.BaseFragment
import com.example.yuefengproject.MyLog
import com.example.yuefengproject.R
import com.example.yuefengproject.data.factory.MyFactory
import com.example.yuefengproject.data.model.MyViewModel
import com.example.yuefengproject.data.repository.MyRepository
import com.example.yuefengproject.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>() {
    override val resId: Int = R.layout.fragment_my
    private lateinit var myViewModel: MyViewModel
    private val controller: MyPageController = MyPageController()

    override fun initAfterBinding() {
        val meRepository = MyRepository()
        val myFactory = MyFactory(requireActivity().application, meRepository)
        myViewModel = ViewModelProvider(this, myFactory).get(MyViewModel::class.java)
        binding.myModel = myViewModel

        binding.lifecycleOwner = this
        myViewModel.memberDataList.observe(this) {
            binding.memberInoEntity = it[0]
        }
        myViewModel.list.observe(this) {
            controller.setData(it)
        }
        binding.run {
            recList.layoutManager = LinearLayoutManager(requireActivity().application,
                LinearLayoutManager.VERTICAL,false)
            controller.onItemClickListener = itemClickListener
            recList.adapter = controller.adapter
        }
    }

    private val itemClickListener: (data: MyPageRow.Item) -> Unit = { item ->
        when (item.iconResId) {
            R.drawable.ui_record -> {
                MyLog.e("ui_record")
            }
            R.drawable.ui_point -> {
                MyLog.e("ui_point")
            }
            R.drawable.ui_faq -> {
                MyLog.e("ui_faq")
            }
            R.drawable.ui_policy -> {
                MyLog.e("ui_policy")
            }
            R.drawable.ui_mail -> {
                MyLog.e("ui_mail")
            }
            else -> {
                MyLog.e("else")
            }
        }

    }
}