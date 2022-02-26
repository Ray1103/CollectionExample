package com.example.collectionexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.collectionexample.databinding.FragmentDetailBinding
import com.example.collectionexample.model.AssetModel
import com.example.collectionexample.viewmodel.DetailVM
import com.example.collectionexample.viewmodel.MainVM

class FragmentDetail : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private lateinit var vm : MainVM
    private lateinit var data : AssetModel.AssetObject

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        data = arguments?.getSerializable("data") as AssetModel.AssetObject
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_detail,container,false)
        vm = ViewModelProvider(requireActivity()).get(MainVM::class.java)
        binding.mainVM = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.let {
            it.toolbar.title = data.collection.name
            it.toolbar.setNavigationOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            Glide.with(this)
                .load(data.image_url)
                .into(it.img)

            it.name.text = data.name
            it.description.text = data.description

        }
    }
}