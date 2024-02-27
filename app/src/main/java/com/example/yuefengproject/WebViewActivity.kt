package com.example.yuefengproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yuefengproject.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    lateinit var urlLoad: String
    lateinit var webViewTitle: String
    lateinit var binding: ActivityWebViewBinding
    lateinit var myApplication: BaseMyProjectApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
    }
}