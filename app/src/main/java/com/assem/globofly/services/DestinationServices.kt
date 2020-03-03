package com.assem.globofly.services

import com.assem.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.GET

interface DestinationServices {

    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>
}