package com.nintex.nintexflights.helpers

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date;

object FlightSearchUtil {

    fun isSearchValid(
        originCode: String,
        destinationCode: String,
        departureDate: String,
        returnDate: String
    ): Boolean {
        //check if all the parameters are provided
        if (originCode.isNotEmpty() && destinationCode.isNotEmpty() && departureDate.isNotEmpty() && returnDate.isNotEmpty()) {
            //check if all the parameters are distinct
            if (originCode != destinationCode && departureDate != returnDate) {

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

                var departure: Date = sdf.parse(departureDate)
                var returnD: Date = sdf.parse(returnDate)

                if (returnD.after(departure)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        * Checks if the origin, destination and the dates are selected before searching
        * */
    fun shouldEnableSearchButton(
        originCode: String,
        destinationCode: String,
        departureDate: String,
        returnDate: String
    ): Boolean {
        if (originCode.isNotEmpty() && destinationCode.isNotEmpty() && departureDate.isNotEmpty() && returnDate.isNotEmpty()) {
            return true;
        }
        return false;
    }

}