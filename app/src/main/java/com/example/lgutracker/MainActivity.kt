package com.example.lgutracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {


    var mywebview: WebView? = null
    var url = "http://192.168.254.118:8080/lguTrackerForm.html"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mywebview = findViewById<WebView>(R.id.webView)

        /* mywebview!!.webViewClient = object : WebViewClient() {
             override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                 view?.loadUrl(url)
                 return true
             }
         }
         */

        mywebview!!.setWebViewClient(MyBrowser())
        mywebview!!.settings.javaScriptEnabled = true
        mywebview!!.settings.allowContentAccess = true
        mywebview!!.settings.domStorageEnabled = true
        mywebview!!.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_fav -> {
            // do stuff
            this.showCreateCategoryDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    @SuppressLint("InflateParams")
    fun showCreateCategoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change Server URL")
        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialog_new_category, null)
        val urlServerText = view.findViewById(R.id.urlServerText) as EditText
        builder.setView(view)
        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newUrl = urlServerText.text
            var isValid = true
            if (newUrl.isBlank()) {
                urlServerText.error = "error"
                isValid = false
            } else {
                url = newUrl.toString()
            }

            if (isValid) {
                // do something
                mywebview!!.loadUrl(url)

                dialog.dismiss()
            }

        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show();
    }


    private inner class MyBrowser : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }


    }
}
