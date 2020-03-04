package com.assem.globofly.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assem.globofly.R
import com.assem.globofly.helpers.SampleData
import com.assem.globofly.models.Destination
import com.assem.globofly.services.DestinationServices
import com.assem.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_create)

        setSupportActionBar(toolbar)
        val context = this

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btn_add.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = et_city.text.toString()
            newDestination.description = et_description.text.toString()
            newDestination.country = et_country.text.toString()

            val destinationService = ServiceBuilder.buildService(DestinationServices::class.java)
            val requestCall = destinationService.addDestination(newDestination)
            requestCall.enqueue(object : Callback<Destination> {
                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(context, "Failed to add item!", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        // Move back to DestinationListActivity
                        finish()
                        Toast.makeText(context, "Item added successfully!", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(context, "Failed to add item!", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}
