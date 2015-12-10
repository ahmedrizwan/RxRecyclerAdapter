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
public class RxAdapterForTypes<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<T> mDataSet;
    private List<ViewHolderInfo> mViewHolderInfoList;
    private OnGetItemViewType mViewTypeCallback;
    private PublishSubject<ViewItem> mPublishSubject;

    public RxAdapterForTypes(final List<T> dataSet, List<ViewHolderInfo> viewHolderInfoList, OnGetItemViewType viewTypeCallback) {
        mDataSet = dataSet;
        mViewHolderInfoList = viewHolderInfoList;
        mViewTypeCallback = viewTypeCallback;
        mPublishSubject = PublishSubject.create();
    }

    public rx.Observable<ViewItem> asObservable(){
        return mPublishSubject.asObservable();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent,
                                                 final int viewType) {
        for (ViewHolderInfo viewHolderInfo : mViewHolderInfoList) {
            if(viewType == viewHolderInfo.getType()){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(viewHolderInfo.getLayoutRes(), parent, false);
                return new RecyclerViewHolder(view);
            }
        }
        throw new RuntimeException("View Type in RxAdapter not found!");
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        mPublishSubject.onNext(new ViewItem<>(holder.mViewDataBinding, mDataSet.get(position), position));
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

    public void updateDataSet(List<T> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }





}