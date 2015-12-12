package com.minimize.android.rxrecycleradapter;

import android.support.annotation.LayoutRes;

public class ViewHolderInfo {
    @LayoutRes
    private int layoutRes;
    private int type;

    public ViewHolderInfo(@LayoutRes final int layoutRes, final int type) {
        this.layoutRes = layoutRes;
        this.type = type;
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public int getType() {
        return type;
    }

}