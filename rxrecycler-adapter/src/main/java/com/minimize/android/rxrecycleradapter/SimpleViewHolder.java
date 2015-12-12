package com.minimize.android.rxrecycleradapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SimpleViewHolder<T, V extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private V mViewDataBinding;

    public V getViewDataBinding() {
        return mViewDataBinding;
    }

    public T getItem() {
        return mItem;
    }

    private T mItem;

    protected void setItem(final T item) {
        mItem = item;
    }

    public SimpleViewHolder(final View itemView) {
        super(itemView);
        mViewDataBinding = DataBindingUtil.bind(itemView);
    }
}