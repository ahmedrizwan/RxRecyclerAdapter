package com.minimize.android.rxrecycleradapter

abstract class OnGetItemViewType {
    abstract fun getItemViewType(position: Int): Int
}