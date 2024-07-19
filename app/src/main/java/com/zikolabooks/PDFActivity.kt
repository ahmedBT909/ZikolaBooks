package com.zikolabooks

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.IOException

class PDFActivity : AppCompatActivity() {

    private lateinit var pdfView: PDFView
    private lateinit var assetManager: AssetManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfactivity)

        pdfView = findViewById(R.id.pdfView)
        assetManager = resources.assets

        val pdfFileName = intent.getStringExtra("pdfFileName")
        val currentPage = intent.getIntExtra("currentPage", 0)

        // Load and display the PDF
        loadPDF(pdfFileName, currentPage)
    }

    private fun loadPDF(pdfFileName: String?, currentPage: Int) {
        if (pdfFileName != null) {
            try {
                val inputStream = assetManager.open(pdfFileName)

                // Load the PDF into PDFView starting from currentPage
                pdfView.fromStream(inputStream)
                    .defaultPage(currentPage)
                    .onPageChange { page, _ ->
                        // Save the current page whenever it changes
                        saveCurrentPage(pdfFileName, page)
                    }
                    .load()

            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("PDFActivity", "Error opening PDF: ${e.message}")
            }
        }
    }

    private fun saveCurrentPage(pdfFileName: String, currentPage: Int) {
        val sharedPreferences = getSharedPreferences("PDFPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(pdfFileName, currentPage)
        editor.apply()
    }


}

