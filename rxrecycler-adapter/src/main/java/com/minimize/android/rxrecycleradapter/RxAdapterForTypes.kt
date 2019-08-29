package com.minimize.android.rxrecycleradapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by ahmedrizwan on 10/12/2015.
 */
internal class RxAdapterForTypes<T>(dataSet: List<T>, private val mViewHolderInfoList: List<ViewHolderInfo>, private val mViewTypeCallback: OnGetItemViewType) : RecyclerView.Adapter<TypesViewHolder<T>>() {

    var dataSet: List<T>? = null
        private set
    private val mPublishSubject: PublishSubject<TypesViewHolder<T>>
    private var mOnViewHolderInflate: OnViewHolderInflated? = null

    init {
        this.dataSet = dataSet
        mPublishSubject = PublishSubject.create()
    }

    fun setOnViewHolderInflate(onViewHolderInflate: OnViewHolderInflated) {
        mOnViewHolderInflate = onViewHolderInflate
    }

    fun asObservable(): Observable<TypesViewHolder<T>> {
        return mPublishSubject
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TypesViewHolder<T> {
        for (viewHolderInfo in mViewHolderInfoList) {
            if (viewType == viewHolderInfo.type) {
                val view = LayoutInflater.from(parent.context)
                        .inflate(viewHolderInfo.layoutRes, parent, false)
                if (mOnViewHolderInflate != null)
                    mOnViewHolderInflate!!.onInflated(view, parent, viewType)
                return TypesViewHolder(view)
            }
        }
        throw RuntimeException("View Type in RxAdapter not found!")
    }

    override fun onBindViewHolder(holder: TypesViewHolder<T>, position: Int) {
        holder.item = dataSet!![position]
        mPublishSubject.onNext(holder)
    }

    override fun getItemCount(): Int {
        return dataSet!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return mViewTypeCallback.getItemViewType(position)
    }

    fun updateDataSet(dataSet: List<T>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun updateDataSetWiOneEffectedItem(dataSet: List<T>, position: Int) {
        this.dataSet = dataSet
        notifyItemChanged(position)
    }

}