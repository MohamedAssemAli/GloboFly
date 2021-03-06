package com.assem.globofly.data.api

import com.assem.globofly.data.model.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationServices {

    @GET("destination")
    fun getDestinationList(): Call<List<Destination>>

    /*
    // using headers
    @Headers("x-device-type: Android", "x-foo: bar")
    @GET("destination")
    fun getDestinationList(
        @QueryMap filter: HashMap<String, String>,
        @Header("Accept-Language") language: String
    ): Call<List<Destination>>
     */

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