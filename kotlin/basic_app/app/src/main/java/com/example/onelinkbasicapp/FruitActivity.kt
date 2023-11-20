package com.example.onelinkbasicapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


public abstract class FruitActivity : AppCompatActivity() {
    var dlAttrs: TextView? = null
    var dlTitleText: TextView? = null
    var goToConversionDataText: TextView? = null
    var fruitName: String? = null
    var fruitAmountStr: String? = null
    var fruitAmount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}