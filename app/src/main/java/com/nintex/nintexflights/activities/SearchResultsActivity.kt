package com.nintex.nintexflights.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
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
import java.util.*
import kotlin.concurrent.schedule

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var rlProgressView: RelativeLayout
    private lateinit var rvFlights: RecyclerView
    lateinit var tvResultCount: TextView

    //cached current search
    private lateinit var originName: String
    private lateinit var destinationName: String
    private lateinit var originCode: String
    private lateinit var destinationCode: String
    private lateinit var departureDate: String
    private lateinit var returnDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        supportActionBar?.hide()

        //retrieve the query values
        originName = intent.getStringExtra("originName").toString()
        destinationName = intent.getStringExtra("destinationName").toString()
        originCode = intent.getStringExtra("originCode").toString()
        destinationCode = intent.getStringExtra("destinationCode").toString()
        departureDate = intent.getStringExtra("departureDate").toString()
        returnDate = intent.getStringExtra("returnDate").toString()

        ivBack = findViewById(R.id.ivBack)
        rlProgressView = findViewById(R.id.rlProgressView)
        rvFlights = findViewById(R.id.rvFlights)
        tvResultCount = findViewById(R.id.tvResultCount)

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
        rlProgressView.visibility = View.VISIBLE
        //call api to get flight response
        val request = RetrofitService.buildService(APIServices::class.java)
        val call = request.getFlights(originCode, destinationCode, departureDate, returnDate)
        call.enqueue(object : Callback<MutableList<FlightResult>> {
            override fun onResponse(
                call: Call<MutableList<FlightResult>>,
                response: Response<MutableList<FlightResult>>
            ) {
                if (response.isSuccessful) {
                    val results: MutableList<FlightResult> = response.body()!!

                    //set result count
                    tvResultCount.text =
                        "" + results.size + " " + getString(R.string.flights_found) + " " + originName + " to " + destinationName

                    setupRecyclerView(results);
                }
            }

            override fun onFailure(call: Call<MutableList<FlightResult>>, t: Throwable) {
                Toast.makeText(this@SearchResultsActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setupRecyclerView(data: MutableList<FlightResult>) {
        rvFlights.layoutManager = LinearLayoutManager(this)
        val adapter = FlightsRVAdapter(
            data,
            originCode,
            destinationCode,
            destinationCode,
            destinationName
        )
        rvFlights.adapter = adapter

        //hide progress view after a small delay to avoid any abrupt loading
        val interval: Long = 1500
        Timer().schedule(interval) {
            runOnUiThread {
                rlProgressView.visibility = View.GONE
            }
        }
    }
}