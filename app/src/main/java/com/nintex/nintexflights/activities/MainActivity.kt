package com.nintex.nintexflights.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nintex.nintexflights.R
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        detectDevice();
    }

    private fun detectDevice() {
        if (this@MainActivity.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            startActivity(Intent(this, TabletSearchActivity::class.java));
        } else {
            startActivity(Intent(this, SearchActivity::class.java));
        }
    }
}