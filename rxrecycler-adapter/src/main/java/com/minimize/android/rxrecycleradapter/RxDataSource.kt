package com.minimize.android.rxrecycleradapter

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import java.util.Collections.emptyList


/**
 * Created by ahmedrizwan on 26/12/2015.
 */
class RxDataSource<LayoutBinding : ViewDataBinding, DataType>(@LayoutRes private val item_layout: Int, var mDataSet: List<DataType>) {
    /***
     * Call this if you need access to the Adapter!
     * Warning: might return null!
     *
     * @return RxAdapter Instance
     */
    private val rxAdapter: RxAdapter<DataType, LayoutBinding> = RxAdapter(item_layout, mDataSet)

    /***
     * Call this when binding with a single Item-Type
     *
     * @param recyclerView RecyclerView instance
     * @param item_layout Layout id
     * @param <LayoutBinding> ViewDataBinding Type for the layout
     * @return Observable for binding viewHolder
    </LayoutBinding> */
    fun bindRecyclerView(
            recyclerView: RecyclerView): Observable<SimpleViewHolder<DataType, LayoutBinding>> {
        recyclerView.adapter = rxAdapter
        return rxAdapter.asObservable()
    }

    fun asObservable(): Observable<SimpleViewHolder<DataType, LayoutBinding>> {
        return rxAdapter.asObservable()
    }
    /***
     * For setting base dataSet
     */
    fun updateDataSet(dataSet: List<DataType>): RxDataSource<LayoutBinding, DataType> {
        mDataSet = dataSet
        return this
    }

    /***
     * For updating Adapter
     */
    fun updateAdapter() {
        //update the update
        rxAdapter.updateDataSet(mDataSet)
    }

    // Transformation methods

    fun map(mapper: (DataType) -> DataType): RxDataSource<LayoutBinding, DataType> {
        mDataSet = Observable.fromIterable(mDataSet).map(mapper).toList().blockingGet()
        rxAdapter.updateDataSet(mDataSet)
        return this
    }

    fun filter(predicate: (DataType) -> Boolean): RxDataSource<LayoutBinding, DataType> {
        mDataSet = Observable.fromIterable(mDataSet).filter(predicate).toList().blockingGet()
        rxAdapter.updateDataSet(mDataSet)
        return this
    }

    fun last(): RxDataSource<LayoutBinding, DataType> {
        mDataSet = listOf(Observable.fromIterable(mDataSet).blockingLast())
        return this
    }

    fun lastOrDefault(defaultValue: DataType): RxDataSource<LayoutBinding, DataType> {
        mDataSet = Observable.fromIterable(mDataSet)
                .takeLast(1)
                .defaultIfEmpty(defaultValue)
                .toList()
                .blockingGet()
        return this
    }

    fun limit(count: Int): RxDataSource<LayoutBinding, DataType> {
        mDataSet = Observable.fromIterable(mDataSet).take(count.toLong()).toList().blockingGet()
        return this
    }

    fun repeat(count: Long): RxDataSource<LayoutBinding, DataType> {
        val dataSet = mDataSet
        mDataSet = Observable.fromIterable(dataSet).repeat(count).toList().blockingGet()
        return this
    }

    fun empty(): RxDataSource<LayoutBinding, DataType> {
        mDataSet = emptyList<DataType>()
        return this
    }

}
