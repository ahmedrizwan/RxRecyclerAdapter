package com.minimize.android.rxrecycleradapter


import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.*


/**
 * Created by ahmedrizwan on 26/12/2015.
 */
class RxDataSourceSectioned<DataType>(var dataSet: List<DataType>, viewHolderInfoList: List<ViewHolderInfo>, viewTypeCallback: OnGetItemViewType) {
    /***
     * Call this if you need access to the Adapter!
     * Warning: might return null!
     *
     * @return RxAdapter Instance
     */
    private val rxAdapter: RxAdapterForTypes<DataType> = RxAdapterForTypes(dataSet, viewHolderInfoList, viewTypeCallback)

    /***
     * Call this when you want to bind with multiple Item-Types
     *
     * @param recyclerView RecyclerView instance
     * @param viewHolderInfoList List of ViewHolderInfos
     * @param viewTypeCallback Callback that distinguishes different view Item-Types
     * @return Observable for binding viewHolder
     */
    fun bindRecyclerView(recyclerView: RecyclerView): Observable<TypesViewHolder<DataType>> {
        recyclerView.adapter = rxAdapter
        return rxAdapter.asObservable()
    }

    /***
     * For setting base dataSet and update adapter
     */
    fun updateDataSet(adapterChanges: AdapterChanges<DataType>): RxDataSourceSectioned<DataType> {
        this.dataSet = adapterChanges.list
        when (adapterChanges.transactionType) {
            AdapterChanges.TransactionTypes.REPLACE_ALL -> notifyDataSetChanged()
            AdapterChanges.TransactionTypes.MODIFY -> notifyItemChanged(adapterChanges.effectedItem)
            AdapterChanges.TransactionTypes.DELETE -> notifyItemRemoved(adapterChanges.effectedItem)
            AdapterChanges.TransactionTypes.ADD -> notifyItemInserted(adapterChanges.effectedItem)
        }
        return this
    }

    /***
     * For setting base dataSet
     */
    fun updateDataSet(dataSet: List<DataType>): RxDataSourceSectioned<DataType> {
        this.dataSet = dataSet
        return this
    }

    fun asObservable(): Observable<TypesViewHolder<DataType>> {
        return rxAdapter.asObservable()
    }

    /***
     * For updating Adapter
     */
    fun updateAdapter() {
        //update the update
        notifyDataSetChanged()
    }

    private fun notifyDataSetChanged() {
        //update the update
        rxAdapter.notifyDataSetChanged(dataSet)
    }

    private fun notifyItemChanged(position: Int) {
        //update the update
        rxAdapter.notifyItemChanged(dataSet, position)
    }

    private fun notifyItemRemoved(position: Int) {
        //update the update
        rxAdapter.notifyItemRemoved(dataSet, position)
    }

    private fun notifyItemInserted(position: Int) {
        //update the update
        rxAdapter.notifyItemInserted(dataSet, position)
    }

    // Transformation methods

    fun map(mapper: Function<in DataType, out DataType>): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).map(mapper).toList().blockingGet()
        rxAdapter.notifyDataSetChanged(dataSet)
        return this
    }

    fun filter(predicate: (DataType) -> Boolean): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).filter(predicate).toList().blockingGet()
        rxAdapter.notifyDataSetChanged(dataSet)
        return this
    }

    fun last(): RxDataSourceSectioned<DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).blockingLast())
        return this
    }

    fun first(): RxDataSourceSectioned<DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).blockingFirst())
        return this
    }

    fun lastOrDefault(defaultValue: DataType): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet)
                .takeLast(1)
                .defaultIfEmpty(defaultValue)
                .toList()
                .blockingGet()
        return this
    }

    fun limit(count: Int): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).take(count.toLong()).toList().blockingGet()
        return this
    }

    fun repeat(count: Long): RxDataSourceSectioned<DataType> {
        val dataSet = dataSet
        this.dataSet = Observable.fromIterable(dataSet).repeat(count).toList().blockingGet()
        return this
    }

    fun empty(): RxDataSourceSectioned<DataType> {
        dataSet = Collections.emptyList<DataType>()
        return this
    }

    fun concatMap(func: (DataType) -> Observable<out DataType>): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).concatMap(func).toList().blockingGet()
        return this
    }

    fun concatWith(observable: Observable<out DataType>): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).concatWith(observable).toList().blockingGet()
        return this
    }

    fun distinct(): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).distinct().toList().blockingGet()
        return this
    }

    fun elementAt(index: Long): RxDataSourceSectioned<DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).elementAt(index).blockingGet())
        return this
    }

    fun elementAtOrDefault(index: Long, defaultValue: DataType): RxDataSourceSectioned<DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).elementAt(index, defaultValue)
                .blockingGet())
        return this
    }

    fun first(defaultItem: DataType): RxDataSourceSectioned<DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).first(defaultItem).blockingGet())
        return this
    }

    fun flatMap(func: (DataType) -> Observable<out DataType>): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).flatMap(func).toList().blockingGet()
        return this
    }

    fun reduce(initialValue: DataType, reducer: (DataType, DataType) -> DataType): RxDataSourceSectioned<DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).reduce(initialValue, reducer).blockingGet())
        return this
    }

    fun take(count: Long): RxDataSourceSectioned<DataType> {
        dataSet = Observable.fromIterable(dataSet).take(count).toList().blockingGet()
        return this
    }

}