package com.nintex.nintexflights.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nintex.nintexflights.R
import com.nintex.nintexflights.models.FlightResult

class FlightsRVAdapter(
    private val mList: List<FlightResult>,
    private val originCode: String,
    private val originName: String,
    private val destinationCode: String,
    private val destinationName: String
) : RecyclerView.Adapter<FlightsRVAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flight_details_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(mList[position], originCode, originName, destinationCode, destinationName)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivAirlineLogo: ImageView = itemView.findViewById(R.id.ivAirlineLogo)
        val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
        val tvFromCode: TextView = itemView.findViewById(R.id.tvFromCode)
        val tvFromName: TextView = itemView.findViewById(R.id.tvFromName)
        val tvToCode: TextView = itemView.findViewById(R.id.tvToCode)
        val tvToName: TextView = itemView.findViewById(R.id.tvToName)
        val tvFlight: TextView = itemView.findViewById(R.id.tvFlight)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvAirline: TextView = itemView.findViewById(R.id.tvAirline)

        fun bind(
            flight: FlightResult,
            originCode: String,
            originName: String,
            destinationCode: String,
            destinationName: String
        ) {
            Glide.with(itemView.context).load(flight.AirlineLogoAddress).into(ivAirlineLogo)
            tvDuration.text = flight.InboundFlightsDuration
            tvAirline.text = flight.AirlineName
            tvPrice.text = "$" + flight.TotalAmount
            tvFlight.text = "UL324"
            tvFromCode.text = originCode
            tvFromName.text = originName
            tvToCode.text = destinationCode
            tvToName.text = destinationName
        }
    }
}
