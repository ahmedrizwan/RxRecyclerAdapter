package com.minimize.android.rxrecycleradapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by ahmedrizwan on 26/12/2015.
 */
public class RxDataSource<DataType> {

  private List<DataType> mDataSet;
  private RxAdapter mRxAdapter;
  private RxAdapterForTypes<DataType> mRxAdapterForTypes;

  public RxDataSource(List<DataType> dataSet) {
    this.mDataSet = dataSet;
  }

  /***
   * Call this when binding with a single Item-Type
   *
   * @param recyclerView RecyclerView instance
   * @param item_layout Layout id
   * @param <LayoutBinding> ViewDataBinding Type for the layout
   * @return Observable for binding viewHolder
   */
  public <LayoutBinding extends ViewDataBinding> Observable<SimpleViewHolder<DataType, LayoutBinding>> bindRecyclerView(
      @NonNull final RecyclerView recyclerView, @NonNull @LayoutRes final int item_layout) {
    mRxAdapterForTypes = null;
    mRxAdapter = new RxAdapter(item_layout, mDataSet);
    recyclerView.setAdapter(mRxAdapter);
    return mRxAdapter.asObservable();
  }

  /***
   * Call this if you need access to the Adapter!
   * Warning: might return null!
   *
   * @return RxAdapter Instance
   */
  @Nullable public RxAdapter getRxAdapter() {
    return mRxAdapter;
  }

  /***
   * Call this if you need access to the Adapter!
   * Warning: might return null!
   *
   * @return RxAdapter Instance
   */
  @Nullable public RxAdapterForTypes<DataType> getRxAdapterForTypes() {
    return mRxAdapterForTypes;
  }

  /***
   * Call this when you want to bind with multiple Item-Types
   *
   * @param recyclerView RecyclerView instance
   * @param viewHolderInfoList List of ViewHolderInfos
   * @param viewTypeCallback Callback that distinguishes different view Item-Types
   * @return Observable for binding viewHolder
   */
  public Observable<TypesViewHolder<DataType>> bindRecyclerView(
      @NonNull final RecyclerView recyclerView,
      @NonNull final List<ViewHolderInfo> viewHolderInfoList,
      @NonNull final OnGetItemViewType viewTypeCallback) {
    mRxAdapter = null;
    mRxAdapterForTypes = new RxAdapterForTypes<>(mDataSet, viewHolderInfoList, viewTypeCallback);
    recyclerView.setAdapter(mRxAdapterForTypes);
    return mRxAdapterForTypes.asObservable();
  }

  /***
   * For setting base dataSet
   */
  public RxDataSource<DataType> updateDataSet(List<DataType> dataSet) {
    mDataSet = dataSet;
    return this;
  }

  /***
   * For updating Adapter
   */
  public void updateAdapter() {
    if (mRxAdapter != null) {
      //update the update
      mRxAdapter.updateDataSet(mDataSet);
    } else if (mRxAdapterForTypes != null) {
      mRxAdapterForTypes.updateDataSet(mDataSet);
    }
  }

  public RxDataSource<DataType> map(Func1<? super DataType, ? extends DataType> func) {
    mDataSet = Observable.from(mDataSet).map(func).toList().toBlocking().first();
    return this;
  }

  public RxDataSource<DataType> filter(Func1<? super DataType, Boolean> predicate) {
    mDataSet = Observable.from(mDataSet).filter(predicate).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> last() {
    mDataSet = Observable.from(mDataSet).last().toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> last(Func1<? super DataType, Boolean> predicate) {
    mDataSet = Observable.from(mDataSet).last(predicate).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> lastOrDefault(DataType defaultValue) {
    mDataSet = Observable.from(mDataSet)
        .takeLast(1)
        .singleOrDefault(defaultValue)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  public final RxDataSource<DataType> lastOrDefault(DataType defaultValue,
      Func1<? super DataType, Boolean> predicate) {
    mDataSet = Observable.from(mDataSet)
        .filter(predicate)
        .takeLast(1)
        .singleOrDefault(defaultValue)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  public final RxDataSource<DataType> limit(int count) {
    mDataSet = Observable.from(mDataSet).limit(count).toList().toBlocking().first();
    return this;
  }

  public RxDataSource<DataType> empty() {
    mDataSet = Collections.emptyList();
    return this;
  }

  public final <R> RxDataSource<DataType> concatMap(
      Func1<? super DataType, ? extends Observable<? extends DataType>> func) {
    mDataSet = Observable.from(mDataSet).concatMap(func).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> concatWith(Observable<? extends DataType> t1) {
    mDataSet = Observable.from(mDataSet).concatWith(t1).toList().toBlocking().first();
    return this;
  }

  public RxDataSource<DataType> distinct() {
    mDataSet = Observable.from(mDataSet).distinct().toList().toBlocking().first();
    return this;
  }

  public RxDataSource<DataType> distinct(Func1<? super DataType, ? extends Object> keySelector) {
    mDataSet = Observable.from(mDataSet).distinct(keySelector).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> elementAt(int index) {
    mDataSet = Observable.from(mDataSet).elementAt(index).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> elementAtOrDefault(int index, DataType defaultValue) {
    mDataSet = Observable.from(mDataSet)
        .elementAtOrDefault(index, defaultValue)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  public final RxDataSource<DataType> first() {
    mDataSet = Observable.from(mDataSet).first().toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> first(Func1<? super DataType, Boolean> predicate) {
    mDataSet = Observable.from(mDataSet).first(predicate).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> firstOrDefault(DataType defaultValue) {
    mDataSet = Observable.from(mDataSet).firstOrDefault(defaultValue).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> firstOrDefault(DataType defaultValue,
      Func1<? super DataType, Boolean> predicate) {
    mDataSet = Observable.from(mDataSet)
        .firstOrDefault(defaultValue, predicate)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  public final RxDataSource<DataType> flatMap(
      Func1<? super DataType, ? extends Observable<? extends DataType>> func) {
    mDataSet = Observable.from(mDataSet).flatMap(func).toList().toBlocking().first();
    return this;
  }

  @Beta public final RxDataSource<DataType> flatMap(
      Func1<? super DataType, ? extends Observable<? extends DataType>> func, int maxConcurrent) {
    mDataSet = Observable.from(mDataSet).flatMap(func, maxConcurrent).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> flatMap(
      Func1<? super DataType, ? extends Observable<? extends DataType>> onNext,
      Func1<? super Throwable, ? extends Observable<? extends DataType>> onError,
      Func0<? extends Observable<? extends DataType>> onCompleted) {
    mDataSet = Observable.from(mDataSet)
        .flatMap(onNext, onError, onCompleted)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  @Beta public final RxDataSource<DataType> flatMap(
      Func1<? super DataType, ? extends Observable<? extends DataType>> onNext,
      Func1<? super Throwable, ? extends Observable<? extends DataType>> onError,
      Func0<? extends Observable<? extends DataType>> onCompleted, int maxConcurrent) {
    mDataSet = Observable.from(mDataSet)
        .flatMap(onNext, onError, onCompleted, maxConcurrent)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  public final <U, R> RxDataSource<DataType> flatMap(
      final Func1<? super DataType, ? extends Observable<? extends U>> collectionSelector,
      final Func2<? super DataType, ? super U, ? extends DataType> resultSelector) {
    mDataSet = Observable.from(mDataSet)
        .flatMap(collectionSelector, resultSelector)
        .toList()
        .toBlocking()
        .first();
    return this;
  }

  public final RxDataSource<DataType> flatMapIterable(
      Func1<? super DataType, ? extends Iterable<? extends DataType>> collectionSelector) {
    mDataSet =
        Observable.from(mDataSet).flatMapIterable(collectionSelector).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> reduce(Func2<DataType, DataType, DataType> accumulator) {
    mDataSet = Observable.from(mDataSet).reduce(accumulator).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> reduce(DataType initialValue,
      Func2<DataType, ? super DataType, DataType> accumulator) {
    mDataSet =
        Observable.from(mDataSet).reduce(initialValue, accumulator).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> repeat(final long count) {
    List<DataType> dataSet = mDataSet;
    mDataSet = Observable.from(dataSet).repeat(count).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> repeat(final long count, Scheduler scheduler) {
    mDataSet = Observable.from(mDataSet).repeat(count, scheduler).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> take(final int count) {
    mDataSet = Observable.from(mDataSet).take(count).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> takeFirst(Func1<? super DataType, Boolean> predicate) {
    mDataSet = Observable.from(mDataSet).takeFirst(predicate).toList().toBlocking().first();
    return this;
  }

  public final RxDataSource<DataType> takeLast(final int count) {
    mDataSet = Observable.from(mDataSet).takeLast(count).toList().toBlocking().first();
    return this;
  }
}
