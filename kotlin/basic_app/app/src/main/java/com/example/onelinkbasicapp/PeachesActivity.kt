package com.example.onelinkbasicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PeachesActivity : FruitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    //מחזיר אל ה-fruitactivity את האידי של אותו מחלקה כדי שיוכל לשנות את התצוגה שלו דרך הfruit-activity
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_peaches
    }
}