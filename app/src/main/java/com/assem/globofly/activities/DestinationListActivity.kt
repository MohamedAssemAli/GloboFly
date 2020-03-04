package com.assem.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assem.globofly.R
import com.assem.globofly.helpers.DestinationAdapter
import com.assem.globofly.models.Destination
import com.assem.globofly.services.DestinationServices
import com.assem.globofly.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destiny_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()
    }

    private fun loadDestinations() {
        val destinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
//         using query
//        val requestCall = destinationServices.getDestinationByQuery("India")

//         using QueryMap
//        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"
//        val requestCall = destinationServices.getDestinationByQueryMap(filter)

        val requestCall = destinationServices.getDestinationList()
        requestCall.enqueue(object : Callback<List<Destination>> {
            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
                Log.d("onFailure", t.message)
                Toast.makeText(
                    this@DestinationListActivity,
                    "Error occurred! " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    // Status code is in the range of 200 to 299
                    val destinationList = response.body()!!
                    destiny_recycler_view.adapter = DestinationAdapter(destinationList)
                } else if (response.code() == 401) {
                    Log.d("onResponse", "session is expired - 401")
                } else {
                    // Status code is in the range of 300 to 500
                    Log.d("onResponse", "failed to retrieve items - 300 : 500")
                }
            }

        })
    }
}
