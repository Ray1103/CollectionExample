package com.example.collectionexample.rep

import com.example.collectionexample.api.ApiServer
import com.example.collectionexample.api.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AseetsRep {
    private val api = RetrofitManager.client.create(ApiServer::class.java)

    suspend fun getAssets(offset : Int = 0) =
        withContext(Dispatchers.IO) {
            val result = api.getCollection(offset = offset)
            if (result.isSuccessful) {
                return@withContext result.body()
            } else {

            }
        }
}