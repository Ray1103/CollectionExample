package com.example.collectionexample.model

import java.io.Serializable


class  AssetModel{
    data class AssetList(
        val assets : ArrayList<AssetObject>
    )
    data class AssetObject(
        val image_url : String,
        val name : String,
        val collection: Collection,
        val description : String,
        val permalink : String
    ) : Serializable


    data class Collection(
        val name : String
    )
}

