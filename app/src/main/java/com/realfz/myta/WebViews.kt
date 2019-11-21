package com.realfz.myta

import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.destinasi_activity.*

class WebViews : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.destinasi_activity)

        val url1 = intent.getStringExtra("init")
        webViewer.webViewClient = MyWebs()
        val java = webViewer!!.settings
        java.javaScriptEnabled = true
        webViewer.loadUrl(url1)

    }

    override fun onBackPressed() {

    }

    class MyWebs : WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return true
        }
    }
}