package com.example.onelinkbasicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class FruitActivity: AppCompatActivity() {

    //האידי שנשלח מהמחלקות האחרות מתקבל לכאן ובשורה 13 הוא מתחבר אל התצוגה של אותו הקלאס
    protected abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId());
    }
}