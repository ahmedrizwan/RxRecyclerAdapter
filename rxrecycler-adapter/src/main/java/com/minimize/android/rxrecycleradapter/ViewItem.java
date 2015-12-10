package com.minimize.android.rxrecycleradapter;

import android.databinding.ViewDataBinding;

public class ViewItem<T> {

        public ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;
        }

        public T getItem() {
            return mItem;
        }

        private final ViewDataBinding mViewDataBinding;
        private final T mItem;
        private final int mPosition;


        public ViewItem(final ViewDataBinding viewHolder, final T item, final int position) {
            mViewDataBinding = viewHolder;
            mItem = item;
            mPosition = position;
        }

        public int getPosition() {
            return mPosition;
        }
    }