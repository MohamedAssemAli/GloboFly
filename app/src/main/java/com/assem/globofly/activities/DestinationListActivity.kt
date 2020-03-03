package com.assem.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val requestCall = destinationServices.getDestinationList()
        requestCall.enqueue(object : Callback<List<Destination>> {
            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    val destinationList = response.body()!!
                    destiny_recycler_view.adapter = DestinationAdapter(destinationList)
                }
            }

        })
    }
}
