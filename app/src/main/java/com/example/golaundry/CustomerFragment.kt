package com.example.golaundry

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceAdapter: ServiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_customer, container, false)

        recyclerView = view.findViewById(R.id.rv_service)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with data from string arrays
        serviceAdapter = ServiceAdapter(
            resources.getStringArray(R.array.data_title),
            resources.getStringArray(R.array.data_desc)
        )

        recyclerView.adapter = serviceAdapter

        // Set onClickListener for the WhatsApp button
        val whatsappButton: Button = view.findViewById(R.id.btn_whatsapp)
        whatsappButton.setOnClickListener {
            openWhatsApp()
        }

        return view
    }

    // Custom RecyclerView Adapter
    private inner class ServiceAdapter(private val titles: Array<String>, private val descriptions: Array<String>) :
        RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_service, parent, false)
            return ServiceViewHolder(view)
        }

        override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
            holder.titleTextView.text = titles[position]
            holder.descTextView.text = descriptions[position]
        }

        override fun getItemCount(): Int {
            return titles.size
        }

        // ViewHolder class
        inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
            val descTextView: TextView = itemView.findViewById(R.id.desc_text_view)
        }
    }
    // Open WhatsApp method
    private fun openWhatsApp() {
        val phoneNumber = "081269655855" // Replace with the desired phone number
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
