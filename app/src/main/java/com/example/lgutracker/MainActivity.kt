package com.example.lgutracker

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private var mywebview: WebView? = null
    private var url = "http://192.168.254.118:8080/lguTrackerForm.html"
    private var sharedPrefs: SharedPreferences? = null
    private var setReloadServer: SharedPreferences.Editor? = null
    private val LINK_ADDRESS = "LINK_ADDRESS"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

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

        R.id.reload_select -> {
            // do stuff
            justReload()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun justReload() {
        mywebview!!.reload()
    }

    @SuppressLint("InflateParams", "CommitPrefEdits")
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
            if (newUrl.isBlank()) {
                urlServerText.error = "error"
                dialog.dismiss()
            } else {
                url = newUrl.toString()
            }
                // do something
            /*findViewById<WebView>(R.id.webView)
            mywebview!!.loadUrl(url)
            mywebview!!.webViewClient = MyBrowser()*/
            setReloadServer = sharedPrefs?.edit()
            setReloadServer!!.putString(LINK_ADDRESS, url)
            setReloadServer!!.apply()
            val newServerInflate = LayoutInflater.from(this).inflate(R.layout.new_server_fragment, null, false)
            mywebview = findViewById<WebView>(R.id.webViewNew)
            mywebview!!.setWebViewClient(MyBrowser())
            mywebview!!.loadUrl(url)
            //onCreate( Bundle())
            Toast.makeText(this, "Loading: " + url, Toast.LENGTH_SHORT).show()

        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show();
    }

    override fun onBackPressed() {
        if (mywebview!!.canGoBack()) {
            mywebview!!.goBack()
        } else {
            super.onBackPressed()
        }

    }


    private inner class MyBrowser : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }


    }
}
