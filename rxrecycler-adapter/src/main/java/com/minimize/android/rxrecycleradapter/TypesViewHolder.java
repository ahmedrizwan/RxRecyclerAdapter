package com.minimize.android.rxrecycleradapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ahmedrizwan on 13/12/2015.
 */
public class TypesViewHolder<T> extends RecyclerView.ViewHolder {
    private ViewDataBinding mViewDataBinding;

    public T getItem() {
        return mItem;
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    private T mItem;

    protected void setItem(final T item) {
        mItem = item;
    }

    public TypesViewHolder(final View itemView) {
        super(itemView);
        mViewDataBinding = DataBindingUtil.bind(itemView);
    }
}
