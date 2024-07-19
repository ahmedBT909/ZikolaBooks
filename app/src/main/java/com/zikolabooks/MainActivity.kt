package com.zikolabooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.zikolabooks.R
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class MainActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this) {}
        loadInterstitialAd()
        // كتاب 1
        val btnViewBook1: Button = findViewById(R.id.btn_view_book1)
        btnViewBook1.setOnClickListener { viewPDF("book1.pdf") }

        // كتاب 2
        val btnViewBook2: Button = findViewById(R.id.btn_view_book2)
        btnViewBook2.setOnClickListener { viewPDF("book2.pdf") }

        // كتاب 3
        val btnViewBook3: Button = findViewById(R.id.btn_view_book3)
        btnViewBook3.setOnClickListener { viewPDF("book3.pdf") }

        //قريبا
        /*
        // معلومات
        val btnInfo: Button = findViewById(R.id.btn_info)
        btnInfo.setOnClickListener {
            val intent = Intent(this@MainActivity, InfoActivity::class.java)
            startActivity(intent)
        }
        */

    }

    private fun viewPDF(pdfFileName: String) {
        val sharedPreferences = getSharedPreferences("PDFPreferences", Context.MODE_PRIVATE)
        val currentPage = sharedPreferences.getInt(pdfFileName, 0) // Default to page 0 if not found

        val intent = Intent(this, PDFActivity::class.java)
        intent.putExtra("pdfFileName", pdfFileName)
        intent.putExtra("currentPage", currentPage)
        startActivity(intent)
    }
    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        // Replace with your Interstitial Ad Unit ID
        InterstitialAd.load(this, "ca-app-pub-1243149741218557/2841103253", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.message?.let { Log.d("MainActivity", it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("MainActivity", "Ad was loaded.")
                mInterstitialAd = interstitialAd

                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d("MainActivity", "Ad was dismissed.")
                        mInterstitialAd = null
                        loadInterstitialAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                        Log.d("MainActivity", "Ad failed to show.")
                        mInterstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d("MainActivity", "Ad showed fullscreen content.")
                    }
                }

                showInterstitialAd()
            }
        })
    }
    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        } else {
            Log.d("MainActivity", "The interstitial ad wasn't ready yet.")
        }
    }

}
