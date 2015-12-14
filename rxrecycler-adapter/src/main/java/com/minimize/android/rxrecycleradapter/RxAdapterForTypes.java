package com.minimize.android.rxrecycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.subjects.PublishSubject;

/**
 * Created by ahmedrizwan on 10/12/2015.
 */
public class RxAdapterForTypes<T> extends RecyclerView.Adapter<TypesViewHolder<T>> {

    private List<T> mDataSet;
    private List<ViewHolderInfo> mViewHolderInfoList;
    private OnGetItemViewType mViewTypeCallback;
    private PublishSubject<TypesViewHolder<T>> mPublishSubject;
    private OnViewHolderInflated mOnViewHolderInflate;

    public RxAdapterForTypes(final List<T> dataSet, List<ViewHolderInfo> viewHolderInfoList, OnGetItemViewType viewTypeCallback) {
        mDataSet = dataSet;
        mViewHolderInfoList = viewHolderInfoList;
        mViewTypeCallback = viewTypeCallback;
        mPublishSubject = PublishSubject.create();
    }

    public void setOnViewHolderInflate(OnViewHolderInflated onViewHolderInflate) {
        mOnViewHolderInflate = onViewHolderInflate;
    }

    public rx.Observable<TypesViewHolder<T>> asObservable() {
        return mPublishSubject.asObservable();
    }

    @Override
    public TypesViewHolder<T> onCreateViewHolder(final ViewGroup parent,
                                                 final int viewType) {
        for (ViewHolderInfo viewHolderInfo : mViewHolderInfoList) {
            if (viewType == viewHolderInfo.getType()) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(viewHolderInfo.getLayoutRes(), parent, false);
                if (mOnViewHolderInflate != null)
                    mOnViewHolderInflate.onInflated(view,parent, viewType);
                return new TypesViewHolder<>(view);
            }
        }
        throw new RuntimeException("View Type in RxAdapter not found!");
    }

    @Override
    public void onBindViewHolder(final TypesViewHolder<T> holder, final int position) {
        holder.setItem(mDataSet.get(position));
        mPublishSubject.onNext(holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return mViewTypeCallback.getItemViewType(position);
    }

    public List<T> getDataSet() {
        return mDataSet;
    }

    public void updateDataSet(List<T> dataSet) {
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

}