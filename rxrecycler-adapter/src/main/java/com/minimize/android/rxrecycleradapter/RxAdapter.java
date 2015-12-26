package com.minimize.android.rxrecycleradapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by ahmedrizwan on 09/12/2015.
 */
public class RxAdapter<DataType, LayoutBinding extends ViewDataBinding>
    extends RecyclerView.Adapter<SimpleViewHolder<DataType, LayoutBinding>> {

  private int mItem_layout;
  private List<DataType> mDataSet;

  private PublishSubject<SimpleViewHolder<DataType, LayoutBinding>> mPublishSubject;
  private OnViewHolderInflated mOnViewHolderInflate;

  public RxAdapter(@LayoutRes final int item_layout, final List<DataType> dataSet) {
    mItem_layout = item_layout;
    mDataSet = dataSet;
    mPublishSubject = PublishSubject.create();
  }

  public void setOnViewHolderInflate(OnViewHolderInflated onViewHolderInflate) {
    mOnViewHolderInflate = onViewHolderInflate;
  }

  public Observable<SimpleViewHolder<DataType, LayoutBinding>> asObservable() {
    return mPublishSubject.asObservable();
  }

  @Override
  public SimpleViewHolder<DataType, LayoutBinding> onCreateViewHolder(final ViewGroup parent,
      final int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(mItem_layout, parent, false);
    if (mOnViewHolderInflate != null) mOnViewHolderInflate.onInflated(view, parent, viewType);
    return new SimpleViewHolder<DataType, LayoutBinding>(view);
  }

  @Override public void onBindViewHolder(final SimpleViewHolder<DataType, LayoutBinding> holder,
      final int position) {
    holder.setItem(mDataSet.get(position));
    mPublishSubject.onNext(holder);
  }

  @Override public int getItemCount() {
    return mDataSet.size();
  }

  @Override public int getItemViewType(final int position) {
    return super.getItemViewType(position);
  }

  public List<DataType> getDataSet() {
    return mDataSet;
  }

  public void updateDataSet(List<DataType> dataSet) {
    mDataSet = dataSet;
    notifyDataSetChanged();
  }
}
