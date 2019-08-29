package com.minimize.android.rxrecycleradapter

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by ahmedrizwan on 09/12/2015.
 */
internal class RxAdapter<DataType, LayoutBinding : ViewDataBinding>(@param:LayoutRes private val mItem_layout: Int, dataSet: List<DataType>) : RecyclerView.Adapter<SimpleViewHolder<DataType, LayoutBinding>>() {
    private var dataSet: List<DataType>
    private val mPublishSubject: PublishSubject<SimpleViewHolder<DataType, LayoutBinding>>
    private var mOnViewHolderInflate: OnViewHolderInflated? = null

    init {
        this.dataSet = dataSet
        mPublishSubject = PublishSubject.create()
    }

    fun setOnViewHolderInflate(onViewHolderInflate: OnViewHolderInflated) {
        mOnViewHolderInflate = onViewHolderInflate
    }

    fun asObservable(): Observable<SimpleViewHolder<DataType, LayoutBinding>> {
        return mPublishSubject
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SimpleViewHolder<DataType, LayoutBinding> {
        val view = LayoutInflater.from(parent.context).inflate(mItem_layout, parent, false)
        if (mOnViewHolderInflate != null) mOnViewHolderInflate!!.onInflated(view, parent, viewType)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<DataType, LayoutBinding>,
                                  position: Int) {
        holder.item = dataSet[position]
        mPublishSubject.onNext(holder)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun updateDataSet(dataSet: List<DataType>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun updateDataSetWithOneEfectedItem(dataSet: List<DataType>, position: Int){
        this.dataSet = dataSet
        notifyItemChanged(position)
    }
}
