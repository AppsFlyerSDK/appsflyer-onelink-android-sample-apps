package com.example.onelinkbasicapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.onelinkbasicapp.ui.theme.OnelinkBasicAppTheme
import kotlin.math.log
import com.example.onelinkbasicapp.AppsFlyerConstants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
//        val afDevKey = AppsFlyerConstants.afDevKey;
//                Log.d("Test",afDevKey)
    }
    public fun goToApples(view: View?) {

        try {
            val intent =Intent(this, ApplesActivity::class.java);
            startActivity(intent);
        }
        catch (e:Exception){
            Toast.makeText(applicationContext,"Entered Catch",Toast.LENGTH_SHORT).show()
        }

    }
    public fun goToBananas(view: View?) {

        try {
            val intent =Intent(this, BananasActivity::class.java);
            startActivity(intent);
        }
        catch (e:Exception){
            Toast.makeText(applicationContext,"Entered Catch",Toast.LENGTH_SHORT).show()
        }

    }
    public fun goToPeaches(view: View?) {

        try {
            val intent =Intent(this, PeachesActivity::class.java);
            startActivity(intent);
        }
        catch (e:Exception){
            Toast.makeText(applicationContext,"Entered Catch",Toast.LENGTH_SHORT).show()
        }

    }

}

