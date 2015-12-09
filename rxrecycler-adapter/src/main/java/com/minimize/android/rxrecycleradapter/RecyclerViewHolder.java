package com.minimize.android.rxrecycleradapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding mViewDataBinding;
        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            mViewDataBinding = DataBindingUtil.bind(itemView);
        }
}