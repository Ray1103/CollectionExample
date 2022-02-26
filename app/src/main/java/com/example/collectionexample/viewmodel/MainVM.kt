package com.example.collectionexample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionexample.model.AssetModel
import com.example.collectionexample.rep.AseetsRep
import kotlinx.coroutines.launch


class MainVM : ViewModel() {
   private val _list = MutableLiveData<AssetModel.AssetList>()
    val list: LiveData<AssetModel.AssetList>
        get() = _list


    private val _newList = MutableLiveData<AssetModel.AssetList>()
    val newList: LiveData<AssetModel.AssetList>
        get() = _newList

    var offset = 1
    private val title = MutableLiveData<String>()



    init {
        getData()
    }


    private fun getData(){
        viewModelScope.launch {
            val result = AseetsRep().getAssets() as AssetModel.AssetList
            _list.postValue(result)
        }
    }

    fun getLoadData(){
        viewModelScope.launch {
            val result = AseetsRep().getAssets(offset) as AssetModel.AssetList
            _newList.postValue(result)
        }
        offset += 1

    }
}