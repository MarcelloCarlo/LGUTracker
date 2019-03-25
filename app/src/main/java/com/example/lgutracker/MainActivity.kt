package com.example.lgutracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {


    var mywebview: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mywebview = findViewById<WebView>(R.id.webView)
        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mywebview!!.loadUrl("http://192.168.254.118")
    }
}
