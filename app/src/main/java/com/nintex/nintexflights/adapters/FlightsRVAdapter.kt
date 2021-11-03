package com.nintex.nintexflights.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nintex.nintexflights.R
import com.nintex.nintexflights.models.FlightResult

class FlightsRVAdapter(private val mList: List<FlightResult>) : RecyclerView.Adapter<FlightsRVAdapter.ViewHolder>() {

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

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.ivAirlineLogo.setImageResource(ItemsViewModel.)

        // sets the text to the textview from our itemHolder class
        //holder.tvPrice.text = ItemsViewModel.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivAirlineLogo: ImageView = itemView.findViewById(R.id.ivAirlineLogo)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvFromCode: TextView = itemView.findViewById(R.id.tvFromCode)
        val tvFromName: TextView = itemView.findViewById(R.id.tvFromName)
        val tvToCode: TextView = itemView.findViewById(R.id.tvToCode)
        val tvToName: TextView = itemView.findViewById(R.id.tvToName)
        val tvFlight: TextView = itemView.findViewById(R.id.tvFlight)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    }
}
