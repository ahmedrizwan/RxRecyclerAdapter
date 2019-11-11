package com.minimize.android.rxrecycleradapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import java.util.Collections.emptyList


/**
 * Created by ahmedrizwan on 26/12/2015.
 */
class RxDataSource<LayoutBinding : ViewDataBinding, DataType>(@LayoutRes private val itemLayout: Int, var dataSet: List<DataType>) {
    /***
     * Call this if you need access to the Adapter!
     * Warning: might return null!
     *
     * @return RxAdapter Instance
     */
    private val rxAdapter: RxAdapter<DataType, LayoutBinding> = RxAdapter(itemLayout, dataSet)

    /***
     * Call this when binding with a single Item-Type
     *
     * @param recyclerView RecyclerView instance
     * @param item_layout Layout id
     * @param <LayoutBinding> ViewDataBinding Type for the layout
     * @return Observable for binding viewHolder
    </LayoutBinding> */
    fun bindRecyclerView(
            recyclerView: RecyclerView
    ): Observable<SimpleViewHolder<DataType, LayoutBinding>> {
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
        this.dataSet = dataSet
        return this
    }


    /***
     * For setting base dataSet and update adapter
     */
    fun updateDataSet(updatedList: List<DataType>, effectedItem: Int, transactionType: TransactionTypes): RxDataSource<LayoutBinding, DataType> {
        this.dataSet = updatedList
        when (transactionType) {
            TransactionTypes.REPLACE_ALL -> notifyDataSetChanged()
            TransactionTypes.MODIFY -> notifyItemChanged(effectedItem)
            TransactionTypes.DELETE -> notifyItemRemoved(effectedItem)
            TransactionTypes.ADD -> notifyItemInserted(effectedItem)
        }
        return this
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

    fun map(mapper: (DataType) -> DataType): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).map(mapper).toList().blockingGet()
        rxAdapter.updateDataSet(dataSet)
        return this
    }

    fun filter(predicate: (DataType) -> Boolean): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).filter(predicate).toList().blockingGet()
        rxAdapter.updateDataSet(dataSet)
        return this
    }

    fun last(): RxDataSource<LayoutBinding, DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).blockingLast())
        return this
    }

    fun first(): RxDataSource<LayoutBinding, DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).blockingFirst())
        return this
    }

    fun lastOrDefault(defaultValue: DataType): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet)
                .takeLast(1)
                .defaultIfEmpty(defaultValue)
                .toList()
                .blockingGet()
        return this
    }

    fun limit(count: Int): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).take(count.toLong()).toList().blockingGet()
        return this
    }

    fun repeat(count: Long): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).repeat(count).toList().blockingGet()
        return this
    }

    fun empty(): RxDataSource<LayoutBinding, DataType> {
        dataSet = emptyList<DataType>()
        return this
    }

    fun concatMap(func: (DataType) -> Observable<out DataType>): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).concatMap(func).toList().blockingGet()
        return this
    }

    fun concatWith(observable: Observable<out DataType>): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).concatWith(observable).toList().blockingGet()
        return this
    }

    fun distinct(): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).distinct().toList().blockingGet()
        return this
    }

    fun elementAt(index: Long): RxDataSource<LayoutBinding, DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).elementAt(index).blockingGet())
        return this
    }

    fun elementAtOrDefault(index: Long, defaultValue: DataType): RxDataSource<LayoutBinding, DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).elementAt(index, defaultValue)
                .blockingGet())
        return this
    }

    fun first(defaultItem: DataType): RxDataSource<LayoutBinding, DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).first(defaultItem).blockingGet())
        return this
    }

    fun flatMap(func: (DataType) -> Observable<out DataType>): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).flatMap(func).toList().blockingGet()
        return this
    }

    fun reduce(initialValue: DataType, reducer: (DataType, DataType) -> DataType): RxDataSource<LayoutBinding, DataType> {
        dataSet = listOf(Observable.fromIterable(dataSet).reduce(initialValue, reducer).blockingGet())
        return this
    }

    fun take(count: Long): RxDataSource<LayoutBinding, DataType> {
        dataSet = Observable.fromIterable(dataSet).take(count).toList().blockingGet()
        return this
    }

    companion object {
        const val ALL_ITEMS_EFFECTED = -1
    }

    enum class TransactionTypes {
        REPLACE_ALL,
        DELETE,
        MODIFY,
        ADD
    }

}
