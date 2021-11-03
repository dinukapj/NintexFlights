package com.nintex.nintexflights.models

data class FlightResult(
    val AirlineLogoAddress: String,
    val AirlineName: String,
    val InboundFlightsDuration: String,
    val OutboundFlightsDuration: String,
    val Stops: Int,
    val TotalAmount: Float,
)