package com.minimize.android.rxrecycleradapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ahmedrizwan on 14/12/2015.
 */
public abstract class OnViewHolderInflated {
    public abstract void onInflated(final View view, final ViewGroup parent, final int viewType);
}