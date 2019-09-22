package com.minimize.android.rxrecyclerexample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import com.minimize.android.rxrecycleradapter.OnGetItemViewType
import com.minimize.android.rxrecycleradapter.RxDataSource
import com.minimize.android.rxrecycleradapter.RxDataSourceSectioned
import com.minimize.android.rxrecycleradapter.ViewHolderInfo
import com.minimize.android.rxrecyclerexample.databinding.ActivityMainBinding
import com.minimize.android.rxrecyclerexample.databinding.ItemHeaderLayoutBinding
import com.minimize.android.rxrecyclerexample.databinding.ItemLayoutBinding
import io.reactivex.disposables.CompositeDisposable
import java.util.*


class MainActivity : AppCompatActivity() {

    private val bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mActivityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)

        //ViewHolderInfo List
        val viewHolderInfoList = ArrayList<ViewHolderInfo>()
        viewHolderInfoList.add(ViewHolderInfo(R.layout.item_layout, TYPE_ITEM))
        viewHolderInfoList.add(ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER))

        //Dummy DataSet
        val dataSet = mutableListOf<String>()
        dataSet.add("Lorem")
        dataSet.add("ipsum")
        dataSet.add("dolor")
        dataSet.add("sit")
        dataSet.add("amet")

        mActivityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Simple data source
        val rxDataSource = RxDataSource<ItemLayoutBinding, String>(R.layout.item_layout, dataSet)

        bag.add(
                rxDataSource
                        .map {
                            it.toUpperCase()
                        }
                        .repeat(4)
                        .asObservable()
                        .subscribe {
                            val binding = it.viewDataBinding ?: return@subscribe
                            binding.textViewItem.text = it.item
                        }
        )


        // Sectioned data source
        val rxDataSourceSectioned = RxDataSourceSectioned(dataSet, viewHolderInfoList, object : OnGetItemViewType() {
            override fun getItemViewType(position: Int): Int {
                if (position % 2 == 0) { // even are headers
                    return TYPE_HEADER
                }
                return TYPE_ITEM
            }
        })

        bag.add(
                rxDataSourceSectioned
                        .asObservable()
                        .subscribe {
                            val viewDataBinding = it.viewDataBinding
                            val data = it.item

                            when (viewDataBinding) {
                                is ItemLayoutBinding -> viewDataBinding.textViewItem.text = "ITEM: $data"
                                is ItemHeaderLayoutBinding -> viewDataBinding.textViewHeader.text = "HEADER: $data"
                            }
                        }
        )


        mActivityMainBinding.sectionedToggle.setOnCheckedChangeListener { compoundButton, checked ->
            if (checked) {
                rxDataSourceSectioned.bindRecyclerView(mActivityMainBinding.recyclerView)
            } else {
                rxDataSource.bindRecyclerView(mActivityMainBinding.recyclerView)
            }
        }

        if (mActivityMainBinding.sectionedToggle.isChecked) {
            rxDataSourceSectioned.bindRecyclerView(mActivityMainBinding.recyclerView)
        } else {
            rxDataSource.bindRecyclerView(mActivityMainBinding.recyclerView)
        }

        val dataSourceDataSet = rxDataSource.dataSet
        val dataSourceSectionedDataSet = rxDataSourceSectioned.dataSet

        RxTextView.afterTextChangeEvents(mActivityMainBinding.searchEditText).subscribe { event ->
            rxDataSource.updateDataSet(rxDataSource.dataSet) //base items should remain the same
                    .filter { s -> s.toLowerCase().contains(event.view().text) }
                    .updateAdapter()
            rxDataSourceSectioned.updateDataSet(dataSourceSectionedDataSet) //base items should remain the same
                    .filter { s -> s.toLowerCase().contains(event.view().text) }
                    .updateAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

}
