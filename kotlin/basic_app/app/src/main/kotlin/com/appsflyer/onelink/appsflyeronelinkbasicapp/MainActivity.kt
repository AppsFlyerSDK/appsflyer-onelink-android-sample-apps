package com.appsflyer.onelink.appsflyeronelinkbasicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun goToApples() {
        try {
            val intent =Intent(this, ApplesActivity::class.java)
            startActivity(intent)
        }
        catch (e:Exception){
            Toast.makeText(applicationContext,"Entered Catch",Toast.LENGTH_SHORT).show()
        }
    }

    public fun goToBananas() {
        try {
            val intent =Intent(this, BananasActivity::class.java)
            startActivity(intent)
        }
        catch (e:Exception){
            Toast.makeText(applicationContext,"Entered Catch",Toast.LENGTH_SHORT).show()
        }
    }

    public fun goToPeaches() {
        try {
            val intent =Intent(this, PeachesActivity::class.java)
            startActivity(intent)
        }
        catch (e:Exception){
            Toast.makeText(applicationContext,"Entered Catch",Toast.LENGTH_SHORT).show()
        }
    }
}
