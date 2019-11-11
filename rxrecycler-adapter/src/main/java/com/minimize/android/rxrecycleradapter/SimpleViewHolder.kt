package com.minimize.android.rxrecycleradapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class SimpleViewHolder<T, out V : ViewDataBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val viewDataBinding: V? = DataBindingUtil.bind(itemView)

    var item: T? = null
}