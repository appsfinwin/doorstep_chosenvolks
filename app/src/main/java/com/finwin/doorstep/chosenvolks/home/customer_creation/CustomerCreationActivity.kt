package com.finwin.doorstep.chosenvolks.home.customer_creation

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.finwin.doorstep.chosenvolks.R
import com.finwin.doorstep.chosenvolks.databinding.ActivityCustomerCreationBinding
import com.finwin.doorstep.chosenvolks.home.customer_creation.action.CustomerCretaionAction
import com.finwin.doorstep.chosenvolks.home.customer_creation.select_image.ImageActivity
import java.io.File
import java.io.FileInputStream
import java.util.*

class CustomerCreationActivity : AppCompatActivity() {
    lateinit var webView : WebView
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    val REQUEST_CODE_PROFILE_IC = 111
    val REQUEST_CODE_SIGNATURE = 112
    val REQUEST_CODE_ID_PROOD_ONE = 113
    val REQUEST_CODE_ID_PROOD_TWO = 114
    lateinit var viewModel: CustomerCreationViewModel
    lateinit var binding: ActivityCustomerCreationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_customer_creation)
       viewModel= ViewModelProvider(this)[CustomerCreationViewModel::class.java]
        binding.viewmodel=viewModel
//        webView = binding.webview
//        val webSettings: WebSettings = webView.getSettings()
//        webSettings.javaScriptEnabled = true
//        webView.loadUrl("http://chosenvolks.digicob.in/CustomerForm")
//        //webView.loadUrl("http://softadmin.pickcartshopy.com/");
//        //webView.setWebViewClient(MyWebViewClient);
//        //webView.loadUrl("http://softadmin.pickcartshopy.com/");
//        //webView.setWebViewClient(MyWebViewClient);
//        webView.setWebViewClient(WebViewClient())
//
//        webView.getSettings().setLoadWithOverviewMode(true)
//        webView.getSettings().setUseWideViewPort(true)
//
//
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                if (url.startsWith("tel:")) {
//                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
//                    startActivity(intent)
//                    return true
//                }
//                return false
//            }
//        }

        viewModel.mAction.observe(this, androidx.lifecycle.Observer {
            when (it.action) {
                CustomerCretaionAction.CLICK_PROFILE_IMAGE -> {
                    selectPic(REQUEST_CODE_PROFILE_IC)
                }
                CustomerCretaionAction.CLICK_SIGNATURE -> {
                    selectPic(REQUEST_CODE_SIGNATURE)
                }
                CustomerCretaionAction.CLICK_ID_PROOF_ONE -> {
                    selectPic(REQUEST_CODE_ID_PROOD_ONE)
                }
                CustomerCretaionAction.CLICK_ID_PROOF_TWO -> {
                    selectPic(REQUEST_CODE_ID_PROOD_TWO)
                }
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

    fun checkAndRequestPermissions(): Boolean {
        val permissionSendMessage = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val locationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }


    private fun selectPic(requestCode: Int) {
        if (checkAndRequestPermissions()) {
            val b = AlertDialog.Builder(Objects.requireNonNull(this))
            b.setTitle("Select Image")
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.select_pic_view, null)
            b.setView(dialogView)
            val linCam = dialogView.findViewById<View>(R.id.linr_cam) as LinearLayout
            val linGalry = dialogView.findViewById<View>(R.id.linr_galry) as LinearLayout
            val alertDialog = b.create()
            linCam.setOnClickListener {
                alertDialog.dismiss()
                val id = Intent(this@CustomerCreationActivity, ImageActivity::class.java)
                id.putExtra("IMG_SELECTION_TYPE", "IMG_SELECT_CAMERA")
                startActivityForResult(id, requestCode)
            }
            linGalry.setOnClickListener {
                alertDialog.dismiss()
                val id = Intent(this@CustomerCreationActivity, ImageActivity::class.java)

                id.putExtra("IMG_SELECTION_TYPE", "IMG_SELECT_GALRY")
                startActivityForResult(id, requestCode)
            }
            alertDialog.show()
        }
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (event.action == KeyEvent.ACTION_DOWN) {
//            when (keyCode) {
//                KeyEvent.KEYCODE_BACK -> {
//                    if (webView.canGoBack()) {
//                        webView.goBack()
//                    } else {
//                        finish()
//                    }
//                    return true
//                }
//            }
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            REQUEST_CODE_PROFILE_IC -> {
                if (resultCode == RESULT_OK) {
                    binding.imgProfilePhoto.visibility = View.VISIBLE
                    try {
                        val sUri = data?.getStringExtra("img_result")
                        Glide.with(this@CustomerCreationActivity)
                            .load(sUri)
                            .into(binding.imgProfilePhoto)
                        val file = File(sUri)
                        val buffer = ByteArray(file.length().toInt() + 100)
                        val length = FileInputStream(file).read(buffer)
                        val base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT)
                        viewModel.profikePic64.set("data:image/png;base64,$base64")


                        val decodedString = Base64.decode(base64, Base64.DEFAULT)
                        val bitmap =
                            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                    } catch (e: Exception) {
                    }
                }
            }
            REQUEST_CODE_SIGNATURE -> {
            }
            REQUEST_CODE_ID_PROOD_ONE -> {
            }
            REQUEST_CODE_ID_PROOD_TWO -> {
            }
        }
    }

}