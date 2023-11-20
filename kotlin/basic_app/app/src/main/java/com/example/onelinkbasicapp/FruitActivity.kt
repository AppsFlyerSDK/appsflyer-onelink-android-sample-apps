package com.example.onelinkbasicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class FruitActivity: AppCompatActivity() {

    protected abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId());
    }
}