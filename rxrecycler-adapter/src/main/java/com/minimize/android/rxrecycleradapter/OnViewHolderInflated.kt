package com.minimize.android.rxrecycleradapter

import android.view.View
import android.view.ViewGroup

/**
 * Created by ahmedrizwan on 14/12/2015.
 */
abstract class OnViewHolderInflated {
    abstract fun onInflated(view: View, parent: ViewGroup, viewType: Int)
}