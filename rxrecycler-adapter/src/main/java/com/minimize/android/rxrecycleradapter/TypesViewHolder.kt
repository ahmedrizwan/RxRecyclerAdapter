package com.minimize.android.rxrecycleradapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by ahmedrizwan on 13/12/2015.
 */
open class TypesViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)

    var item: T? = null
}
