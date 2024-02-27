package com.example.yuefengproject.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.yuefengproject.BaseFragment
import com.example.yuefengproject.R
import com.example.yuefengproject.data.factory.HomeFactory
import com.example.yuefengproject.data.model.HomeViewModel
import com.example.yuefengproject.data.repository.HomeRepository
import com.example.yuefengproject.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val resId: Int = R.layout.fragment_home
    private lateinit var viewModel: HomeViewModel
    private lateinit var pickMedia: ActivityResultLauncher<Intent>
    private lateinit var pickMultipleMedia: ActivityResultLauncher<Intent>

    override fun initAfterBinding() {
        val homeRepository = HomeRepository()
        val homeFactory = HomeFactory(requireActivity().application, homeRepository)
        viewModel = ViewModelProvider(this, homeFactory).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel

        // 初始化 pickMedia
        pickMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data?.data
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    // 在此處處理您的選擇的 URI
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        }

        // 初始化 pickMultipleMedia
        pickMultipleMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uris = data?.clipData
                if (uris != null) {
                    val selectedUris = mutableListOf<Uri>()
                    for (i in 0 until uris.itemCount) {
                        val uri = uris.getItemAt(i).uri
                        selectedUris.add(uri)
                        Log.d("PhotoPicker", "Selected URI: $uri")
                    }
                    // 在此處處理您的選擇的 URI 列表 selectedUris
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        }


        binding.homeItme01.setOnClickListener {
            // 啟動活動並選擇媒體
            val mimeType = "image/*" // 您可以設置所需的 MIME 類型
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = mimeType
            }
            pickMedia.launch(intent)
        }

        binding.homeItme02.setOnClickListener {
            // 啟動活動並選擇多個媒體
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*" // 設置要選擇的文件類型
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // 啟用多選
            }
            pickMultipleMedia.launch(intent)
        }
    }
}