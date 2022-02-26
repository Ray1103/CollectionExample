package com.example.collectionexample.api


import com.example.collectionexample.model.AssetModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServer {

    @Headers(
        "X-API-KEY:5b294e9193d240e39eefc5e6e551ce83"
    )
    @GET("/assets")
    suspend fun getCollection(
        @Query("owner") owner: String = "0x960DE9907A2e2f5363646d48D7FB675Cd2892e91",
        @Query("limit") limit : Int = 20,
        @Query ("offset") offset : Int=0
    ): Response<AssetModel.AssetList>
}