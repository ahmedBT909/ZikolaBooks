package com.zikolabooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.StartAppSDK
import com.startapp.sdk.ads.banner.Banner
import com.zikolabooks.R

class MainActivity : AppCompatActivity() {
    private lateinit var bannerAd: Banner
    private lateinit var bannerAd1: Banner
    private lateinit var startAppAd: StartAppAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StartAppSDK.init(this, "206542832", false)

        bannerAd = findViewById(R.id.startAppBanner)
        bannerAd.loadAd()
        bannerAd1 = findViewById(R.id.startAppBanner1)
        bannerAd1.loadAd()

        startAppAd = StartAppAd(this)

        // كتاب 1
        val btnViewBook1: Button = findViewById(R.id.btn_view_book1)
        btnViewBook1.setOnClickListener {
            showInterstitialAd()
            viewPDF("book1.pdf")
        }

        // كتاب 2
        val btnViewBook2: Button = findViewById(R.id.btn_view_book2)
        btnViewBook2.setOnClickListener {
            showInterstitialAd()
            viewPDF("book2.pdf")
        }

        // كتاب 3
        val btnViewBook3: Button = findViewById(R.id.btn_view_book3)
        btnViewBook3.setOnClickListener {
            showInterstitialAd()
            viewPDF("book3.pdf")
        }

        // معلومات
        val btnInfo: Button = findViewById(R.id.btn_info)
        btnInfo.setOnClickListener {
            showInterstitialAd()
            val intent = Intent(this@MainActivity, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showInterstitialAd() {
        startAppAd.showAd() // عرض الإعلان البيني
    }

    private fun viewPDF(pdfFileName: String) {
        val sharedPreferences = getSharedPreferences("PDFPreferences", Context.MODE_PRIVATE)
        val currentPage = sharedPreferences.getInt(pdfFileName, 0) // Default to page 0 if not found

        val intent = Intent(this, PDFActivity::class.java)
        intent.putExtra("pdfFileName", pdfFileName)
        intent.putExtra("currentPage", currentPage)
        startActivity(intent)
    }
}
