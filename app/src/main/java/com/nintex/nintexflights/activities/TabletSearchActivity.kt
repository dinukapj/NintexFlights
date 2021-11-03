package com.nintex.nintexflights.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
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

class TabletSearchActivity : AppCompatActivity() {

    //search side bar
    lateinit var tvFromCode: TextView
    lateinit var tvFromName: TextView
    lateinit var tvToCode: TextView
    lateinit var tvToName: TextView
    lateinit var tvDepartDate: TextView
    lateinit var tvReturnDate: TextView
    lateinit var tvWeight: TextView
    lateinit var tvSeats: TextView
    lateinit var llSeats: LinearLayout
    lateinit var llWeight: LinearLayout
    lateinit var btnSearch: AppCompatButton
    lateinit var rlPlaceholder: RelativeLayout
    lateinit var rlResults: RelativeLayout
    lateinit var tvResultCount: TextView

    //search results
    private lateinit var rlProgressView: RelativeLayout
    private lateinit var rvFlights: RecyclerView

    //cache current search
    private lateinit var originName: String
    private lateinit var destinationName: String
    private lateinit var originCode: String
    private lateinit var destinationCode: String

    //locations data
    val codeList = arrayOf("MLB", "SYD", "CMB", "AUH", "ADL")
    val nameList = arrayOf("Melbourne", "Sydney", "Colombo", "Abu Dhabi", "Adelaide")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablet_search)

        supportActionBar?.hide()

        //Bind UI elements
        tvFromCode = findViewById(R.id.tvFromCode)
        tvFromName = findViewById(R.id.tvFromName)
        tvToCode = findViewById(R.id.tvToCode)
        tvToName = findViewById(R.id.tvToName)
        tvDepartDate = findViewById(R.id.tvDepartDate)
        tvReturnDate = findViewById(R.id.tvReturnDate)
        tvWeight = findViewById(R.id.tvWeight)
        tvSeats = findViewById(R.id.tvSeats)
        llSeats = findViewById(R.id.llSeats)
        llWeight = findViewById(R.id.llWeight)
        btnSearch = findViewById(R.id.btnSearch)
        tvResultCount = findViewById(R.id.tvResultCount)

        rlProgressView = findViewById(R.id.rlProgressView)
        rvFlights = findViewById(R.id.rvFlights)
        rlPlaceholder = findViewById(R.id.rlPlaceholder)
        rlResults = findViewById(R.id.rlResults)

        setDefaults();

        setupEvents()
    }

    /*
    * Setup default values for the selections
    * */
    private fun setDefaults() {
        originCode = codeList[0]
        originName = nameList[0]

        destinationCode = codeList[1]
        destinationName = nameList[1]
    }

    /*
    * Setup click events for UI elements
    * */
    private fun setupEvents() {
        tvFromCode.setOnClickListener {
            showLocationSelector(true)
        }
        tvToCode.setOnClickListener {
            showLocationSelector(false)
        }
        tvDepartDate.setOnClickListener {
            showDatePicker(true)
        }
        tvReturnDate.setOnClickListener {
            showDatePicker(false)
        }
        llWeight.setOnClickListener {
            showWeightSelector()
        }
        llSeats.setOnClickListener {
            showSeatsSelector()
        }
        llSeats.setOnClickListener {
            showSeatsSelector()
        }
        btnSearch.setOnClickListener {
            getFlights()
        }
    }

    /*
    * Show a location selection dialog which takes in a boolean parameter that indicates if this dialog is targeting the user's origin or destination
    * */
    private fun showLocationSelector(origin: Boolean) {
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(if (origin) "Choose origin" else "Choose destination")
        mBuilder.setSingleChoiceItems(codeList, -1) { dialogInterface, i ->
            if (origin) {
                originCode = codeList[i]
                originName = nameList[i]

                tvFromCode.text = originCode
                tvFromName.text = originName
            } else {
                destinationCode = codeList[i]
                destinationName = nameList[i]

                tvToCode.text = destinationCode
                tvToName.text = destinationName
            }
            dialogInterface.dismiss()
        }
        // Set the neutral/cancel button click listener
        mBuilder.setNeutralButton("Cancel") { dialog, which ->
            // Do something when click the neutral button
            dialog.cancel()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    /*
    * Show a date picker dialog which takes in a boolean parameter that indicates if this dialog is targeting the user's departure or return date
    * */
    private fun showDatePicker(departure: Boolean) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this, R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var selectedDate = "$dayOfMonth/$monthOfYear/$year"
                if (departure) {
                    tvDepartDate.setText(selectedDate)
                } else {
                    tvReturnDate.setText(selectedDate)
                }
            },
            year,
            month,
            day
        )

        dpd.show()
    }

    /*
    * Show a luggage allowance selection dialog
    * */
    private fun showWeightSelector() {
        val list = arrayOf("20kg", "60kg", "100kg")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Luggage Allowance")
        mBuilder.setSingleChoiceItems(list, -1) { dialogInterface, i ->
            tvWeight.text = list[i]
            dialogInterface.dismiss()
        }
        // Set the neutral/cancel button click listener
        mBuilder.setNeutralButton("Cancel") { dialog, which ->
            // Do something when click the neutral button
            dialog.cancel()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    /*
    * Show a passenger count selection dialog
    * */
    private fun showSeatsSelector() {
        val list = arrayOf("1", "2", "3", "4", "5+")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Passenger count")
        mBuilder.setSingleChoiceItems(list, -1) { dialogInterface, i ->
            tvSeats.text = list[i]
            dialogInterface.dismiss()
        }
        // Set the neutral/cancel button click listener
        mBuilder.setNeutralButton("Cancel") { dialog, which ->
            // Do something when click the neutral button
            dialog.cancel()
        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun getFlights() {
        rlPlaceholder.visibility = View.GONE
        rlProgressView.visibility = View.VISIBLE

        //call api to get flight response
        val request = RetrofitService.buildService(APIServices::class.java)
        val call = request.getFlights(originCode, destinationCode, "", "")
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
                Toast.makeText(this@TabletSearchActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(data: MutableList<FlightResult>) {
        rvFlights.layoutManager = LinearLayoutManager(this)
        val adapter = FlightsRVAdapter(data)
        rvFlights.adapter = adapter

        //hide progress view after a small delay to avoid any abrupt loading
        val interval: Long = 1500
        Timer().schedule(interval) {
            runOnUiThread {
                rlProgressView.visibility = View.GONE
                rlResults.visibility = View.VISIBLE
            }
        }

    }
}