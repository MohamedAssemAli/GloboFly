package com.assem.globofly.ui.destinationDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assem.globofly.R
import com.assem.globofly.data.model.Destination
import com.assem.globofly.data.api.DestinationServices
import com.assem.globofly.data.api.ServiceBuilder
import com.assem.globofly.ui.destinationList.DestinationListActivity
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_detail)

        setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {
        val destinationServices = ServiceBuilder.buildService(
            DestinationServices::class.java)
        val requestCall = destinationServices.getDestination(id)
        requestCall.enqueue(object : Callback<Destination> {
            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Log.d("onFailure", t.message)
                Toast.makeText(
                    this@DestinationDetailActivity,
                    "Error occurred! " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()
                    destination?.let {
                        et_city.setText(destination.city)
                        et_description.setText(destination.description)
                        et_country.setText(destination.country)
                        collapsing_toolbar.title = destination.city
                    }
                } else if (response.code() == 401) {
                    Log.d("onResponse", "session is expired - 401")
                } else {
                    // Status code is in the range of 300 to 500
                    Log.d("onResponse", "failed to retrieve items - 300 : 500")
                }
            }
        })
    }

    private fun initUpdateButton(id: Int) {

        btn_update.setOnClickListener {

            val city = et_city.text.toString()
            val description = et_description.text.toString()
            val country = et_country.text.toString()

            val destinationServices = ServiceBuilder.buildService(
                DestinationServices::class.java)
            val requestCall = destinationServices.updateDestination(id, city, description, country)
            requestCall.enqueue(object : Callback<Destination> {
                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to update item!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Item updated successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Failed to update item!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    private fun initDeleteButton(id: Int) {

        btn_delete.setOnClickListener {

            val destinationServices = ServiceBuilder.buildService(
                DestinationServices::class.java)
            val requestCall = destinationServices.deleteDestination(id)
            requestCall.enqueue(object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Failed to delete item!",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish() // Move back to DestinationListActivity
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Item deleted successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Failed to delete item!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }
}
