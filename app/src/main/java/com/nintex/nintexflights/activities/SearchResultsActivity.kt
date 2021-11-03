package com.nintex.nintexflights.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nintex.nintexflights.R

class SearchResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        actionBar?.hide();

        setupEvents();
    }

    /*
    * Setup click events for UI elements
    * */
    private fun setupEvents() {

    }
}