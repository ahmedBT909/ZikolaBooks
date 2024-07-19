package com.zikolabooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.zikolabooks.R



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        // معلومات
        val btnInfo: Button = findViewById(R.id.btn_info)
        btnInfo.setOnClickListener {
            val intent = Intent(this@MainActivity, InfoActivity::class.java)
            startActivity(intent)
        }


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
