package com.nintex.nintexflights.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.nintex.nintexflights.R
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class SearchActivity : AppCompatActivity() {

    private lateinit var tvFromCode: TextView;
    private lateinit var tvFromName: TextView;
    private lateinit var tvToCode: TextView;
    private lateinit var tvToName: TextView;
    private lateinit var tvDepartDate: TextView;
    private lateinit var tvReturnDate: TextView;
    private lateinit var tvWeight: TextView;
    private lateinit var tvSeats: TextView;
    private lateinit var llSeats: LinearLayout;
    private lateinit var llWeight: LinearLayout;
    private lateinit var btnSearch: AppCompatButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        actionBar?.hide();

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
            startActivity(intent)
        }
    }

    /*
    * Show a location selection dialog which takes in a boolean parameter that indicates if this dialog is targeting the user's origin or destination
    * */
    private fun showLocationSelector(origin: Boolean) {
        val codeList = arrayOf("MLB", "SYD", "CMB", "AUH", "ADL")
        val nameList = arrayOf("Melbourne", "Sydney", "Colombo", "Abu Dhabi", "Adelaide")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle(if (origin) "Choose origin" else "Choose destination")
        mBuilder.setSingleChoiceItems(codeList, -1) { dialogInterface, i ->
            if (origin) {
                tvFromCode.text = codeList[i]
                tvFromName.text = nameList[i]
            } else {
                tvToCode.text = codeList[i]
                tvToName.text = nameList[i]
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

}