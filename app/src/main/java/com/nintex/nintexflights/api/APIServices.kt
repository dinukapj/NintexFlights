package com.nintex.nintexflights.api

import com.nintex.nintexflights.models.FlightResult
import com.nintex.nintexflights.models.FlightResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {

    @GET("/api/Flight")
    fun getFlights(@Query("DepartureAirportCode") departureAirportCode:String, @Query("ArrivalAirportCode") arrivalAirportCode:String, @Query("DepartureDate") departureDate:String, @Query("ReturnDate") returnDate:String): Call<MutableList<FlightResult>>

}