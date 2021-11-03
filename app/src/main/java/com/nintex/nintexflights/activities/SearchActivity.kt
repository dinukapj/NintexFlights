package com.nintex.nintexflights.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.nintex.nintexflights.R
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity() {

    lateinit var tvFromCode: TextView;
    lateinit var tvFromName: TextView;
    lateinit var tvToCode: TextView;
    lateinit var tvToName: TextView;
    lateinit var tvDepartDate: TextView;
    lateinit var tvReturnDate: TextView;
    lateinit var tvWeight: TextView;
    lateinit var tvSeats: TextView;
    lateinit var llSeats: LinearLayout;
    lateinit var llWeight: LinearLayout;
    lateinit var btnSearch: AppCompatButton;

    //cache current search
    private lateinit var originName: String
    private lateinit var destinationName: String
    private lateinit var originCode: String
    private lateinit var destinationCode: String
    private lateinit var departureDate: String
    private lateinit var returnDate: String

    //locations data
    val codeList = arrayOf("MLB", "SYD", "CMB", "AUH", "ADL")
    val nameList = arrayOf("Melbourne", "Sydney", "Colombo", "Abu Dhabi", "Adelaide")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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

        setupEvents();

        setDefaults();
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
            var intent = Intent(this, SearchResultsActivity::class.java)
            intent.putExtra("originCode", originCode)
            intent.putExtra("originName", originName)
            intent.putExtra("destinationCode", destinationCode)
            intent.putExtra("destinationName", destinationName)
            intent.putExtra("departureDate", departureDate)
            intent.putExtra("returnDate", returnDate)
            startActivity(intent)
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

            validateParameters()

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

                val simpleDateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
                var formattedDate = simpleDateFormat.format(Date(selectedDate))

                if (departure) {
                    departureDate = formattedDate
                    tvDepartDate.setText(selectedDate)
                } else {
                    returnDate = formattedDate
                    tvReturnDate.setText(selectedDate)
                }

                validateParameters()
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

    /*
        * Checks if the origin, destination and the dates are selected before searching
        * */
    private fun validateParameters() {
        btnSearch.isEnabled =
            ::originCode.isInitialized && ::destinationCode.isInitialized && ::departureDate.isInitialized && ::returnDate.isInitialized
    }
}