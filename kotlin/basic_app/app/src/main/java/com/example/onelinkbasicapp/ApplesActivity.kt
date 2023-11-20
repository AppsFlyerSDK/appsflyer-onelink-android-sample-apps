package com.example.onelinkbasicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ApplesActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    //מחזיר אל ה-fruitactivity את האידי של אותו מחלקה כדי שיוכל לשנות את התצוגה שלו דרך הfruitactivity
    override protected fun getLayoutResourceId():Int{
        return R.layout.activity_apples;
    }
}