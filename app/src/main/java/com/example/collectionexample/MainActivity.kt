package com.example.collectionexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.collectionexample.databinding.ActivityMainBinding
import com.example.collectionexample.databinding.ItemCollectionBinding
import com.example.collectionexample.model.AssetModel
import com.example.collectionexample.viewmodel.MainVM
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainVM
    private val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vm = ViewModelProvider(this).get(MainVM::class.java)
        binding.vm = vm
        binding.lifecycleOwner = this

        initView()

        vm.list.observe(this, Observer {
            adapter.clear()
            for (i in it.assets) {
                adapter.add(Item_Collection(i))
            }
        })

        vm.newList.observe(this, Observer {
            for (i in it.assets) {
                adapter.add(Item_Collection(i))
            }
        })


        supportFragmentManager.addOnBackStackChangedListener {
            binding.let {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    it.recycler.visibility = View.VISIBLE
                    it.fragmentLayout.visibility = View.GONE
                }

            }
        }
    }


    fun initView() {
        binding.apply {
            recycler.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recycler.adapter = adapter
            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    var lastVisibleItem: Int = 0
                    val staggeredGridLayoutManager =
                        recyclerView.layoutManager as StaggeredGridLayoutManager
                    val lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                    lastVisibleItem = findMax(lastPositions)
                    if ((adapter.itemCount - 1) == lastVisibleItem) {
                        vm!!.getLoadData()
                    }
                }
            })
        }
    }


    fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (i in lastPositions.indices) {
            val value = lastPositions[i]
            if (value > max) {
                max = value
            }
        }

        return max
    }


    inner class Item_Collection(val data: AssetModel.AssetObject) :
        BindableItem<ItemCollectionBinding>() {
        override fun bind(viewBinding: ItemCollectionBinding, position: Int) {
            viewBinding.apply {
                Glide
                    .with(this@MainActivity)
                    .load(data.image_url)
                    .centerCrop()
                    .into(img)

                name.text = data.name

                layout.setOnClickListener {
                    val fm = supportFragmentManager.beginTransaction()
                    val fg = FragmentDetail()
                    val bundle = Bundle()
                    bundle.putSerializable("data", data)
                    fg.arguments = bundle
                    fm.replace(R.id.fragment_layout, fg)
                    fm.addToBackStack("detail")
                    fm.commit()
                    binding.recycler.visibility = View.GONE
                    binding.fragmentLayout.visibility = View.VISIBLE
                }
            }
        }

        override fun getLayout(): Int {
            return R.layout.item_collection
        }

        override fun initializeViewBinding(view: View): ItemCollectionBinding {
            return ItemCollectionBinding.bind(view)
        }

    }
}