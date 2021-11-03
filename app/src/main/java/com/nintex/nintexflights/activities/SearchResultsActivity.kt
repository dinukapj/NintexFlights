package com.nintex.nintexflights.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nintex.nintexflights.R
import com.nintex.nintexflights.adapters.FlightsRVAdapter
import com.nintex.nintexflights.models.FlightResult

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var ivBack : ImageView
    private lateinit var rlProgressView: RelativeLayout
    private lateinit var rvFlights: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        actionBar?.hide();

        ivBack = findViewById(R.id.ivBack)
        rlProgressView = findViewById(R.id.rlProgressView)
        rvFlights = findViewById(R.id.rvFlights)

        setupEvents();

        getFlights()
    }

    /*
    * Setup click events for UI elements
    * */
    private fun setupEvents() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getFlights() {
        val data = ArrayList<FlightResult>()

        //call api to get flight response

        setupRecyclerView(data);
    }

    private fun setupRecyclerView(data: ArrayList<FlightResult>) {
        rvFlights.layoutManager = LinearLayoutManager(this)
        val adapter = FlightsRVAdapter(data)
        rvFlights.adapter = adapter
    }
}