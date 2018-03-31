package com.minimize.android.rxrecycleradapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

open class SimpleViewHolder<T, out V : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val viewDataBinding: V? = DataBindingUtil.bind(itemView)

    var item: T? = null
}