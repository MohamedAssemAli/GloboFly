package com.assem.globofly.services

import com.assem.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationServices {

    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>

    @GET("destination")
    fun getDestinationByQuery(@Query("country") country: String): Call<List<Destination>>

    @GET("destination")
    fun getDestinationByQueryMap(@QueryMap filter: HashMap<String, String>): Call<List<Destination>>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination): Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") description: String,
        @Field("country") country: String
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit> // unit is similar to bull in java

}