package com.nintex.nintexflights.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nintex.nintexflights.R
import com.nintex.nintexflights.adapters.FlightsRVAdapter
import com.nintex.nintexflights.api.APIServices
import com.nintex.nintexflights.api.RetrofitService
import com.nintex.nintexflights.models.FlightResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        //call api to get flight response
        val request = RetrofitService.buildService(APIServices::class.java)
        val call = request.getFlights("", "", "", "")
        call.enqueue(object: Callback<MutableList<FlightResult>> {
            override fun onResponse(call: Call<MutableList<FlightResult>>, response: Response<MutableList<FlightResult>>) {
                if (response.isSuccessful){
                    val results: MutableList<FlightResult> = response.body()!!
                    setupRecyclerView(results);
}
            }
            override fun onFailure(call: Call<MutableList<FlightResult>>, t: Throwable) {
                Toast.makeText(this@SearchResultsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setupRecyclerView(data: MutableList<FlightResult>) {
        rvFlights.layoutManager = LinearLayoutManager(this)
        val adapter = FlightsRVAdapter(data)
        rvFlights.adapter = adapter
        rlProgressView.visibility = View.GONE
    }
}