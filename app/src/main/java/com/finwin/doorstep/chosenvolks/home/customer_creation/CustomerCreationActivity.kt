package com.finwin.doorstep.chosenvolks.home.customer_creation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.chosenvolks.R
import com.finwin.doorstep.chosenvolks.databinding.ActivityCustomerCreationBinding

class CustomerCreationActivity : AppCompatActivity() {
    lateinit var webView : WebView

    lateinit var viewModel: CustomerCreationViewModel
    lateinit var binding: ActivityCustomerCreationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_customer_creation)
       viewModel= ViewModelProvider(this)[CustomerCreationViewModel::class.java]
        binding.viewmodel=viewModel
        webView = binding.webview
        val webSettings: WebSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true
        webView.loadUrl("http://chosenvolks.digicob.in/CustomerForm")
        //webView.loadUrl("http://softadmin.pickcartshopy.com/");
        //webView.setWebViewClient(MyWebViewClient);
        //webView.loadUrl("http://softadmin.pickcartshopy.com/");
        //webView.setWebViewClient(MyWebViewClient);
        webView.setWebViewClient(WebViewClient())

        webView.getSettings().setLoadWithOverviewMode(true)
        webView.getSettings().setUseWideViewPort(true)


        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("tel:")) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
                return false
            }
        })


    }

//    private class MyWebViewClient : WebViewClient() {
//        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//            if ("www.example.com" == Uri.parse(url).host) {
//                // This is my website, so do not override; let my WebView load the page
//                return false
//            }
//            return if (url.startsWith("tel:")) {
//                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
//                startActivity(intent)
//                true
//            } else {
//                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                startActivity(intent)
//                true
//            }
//        }
//    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }


}