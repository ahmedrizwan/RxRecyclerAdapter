package com.minimize.android.rxrecycleradapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
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
 * Created by ahmedrizwan on 22/12/2015.
 */
public class RxDataSource<DataType, LayoutBinding extends ViewDataBinding> {

    private final int mItem_layout;
    private OnViewHolderInflated mOnViewHolderInflated;
    RxAdapter<DataType, LayoutBinding> mRxAdapter;

    public RxDataSource(@LayoutRes final int item_layout, final List<DataType> dataSet) {
        mItem_layout = item_layout;
        mRxAdapter = new RxAdapter(item_layout, dataSet);
    }

    public RxDataSource(@LayoutRes final int item_layout, final List<DataType> dataSet, OnViewHolderInflated onViewHolderInflated) {
        mItem_layout = item_layout;
        mRxAdapter = new RxAdapter(item_layout, dataSet);
        if(onViewHolderInflated!=null) {
            mRxAdapter.setOnViewHolderInflate(onViewHolderInflated);
            mOnViewHolderInflated = onViewHolderInflated;
        }
    }

    public final RxDataSource<DataType, LayoutBinding> last() {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        mRxAdapter.updateDataSet(Observable.from(dataSet)
                .last()
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> last(Func1<? super DataType, Boolean> predicate) {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        mRxAdapter.updateDataSet(Observable.from(dataSet)
                .last(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> lastOrDefault(DataType defaultValue) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> lastOrDefault(DataType defaultValue, Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .filter(predicate)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> limit(int count) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .limit(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final <R> RxDataSource<R, LayoutBinding> map(Func1<? super DataType, ? extends R> func) {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        List<R> newDataSet = Observable.from(dataSet)
                .map(func)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public Observable<SimpleViewHolder<DataType, LayoutBinding>> getRxAdapter(RecyclerView recyclerView) {
        recyclerView.setAdapter(mRxAdapter);
        return mRxAdapter.asObservable();
    }

    public RxDataSource<DataType, LayoutBinding> empty() {
        mRxAdapter.updateDataSet((List<DataType>) Collections.emptyList());
        return this;
    }

    public final <R> RxDataSource<R, LayoutBinding> concatMap(Func1<? super DataType, ? extends Observable<? extends R>> func) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .concatMap(func)
                .toList()
                .toBlocking()
                .first();

        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final RxDataSource<DataType, LayoutBinding> concatWith(Observable<? extends DataType> t1) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .concatWith(t1)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public RxDataSource<DataType, LayoutBinding> distinct() {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .distinct()
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public RxDataSource<DataType, LayoutBinding> distinct(Func1<? super DataType, ? extends Object> keySelector) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .distinct(keySelector)
                .toList()
                .toBlocking()
                .first());
        return this;
    }


    public final RxDataSource<DataType, LayoutBinding> elementAt(int index) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .elementAt(index)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> elementAtOrDefault(int index, DataType defaultValue) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .elementAtOrDefault(index, defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public RxDataSource<DataType, LayoutBinding> filter(Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .filter(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> first() {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .first()
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> first(Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .first(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> firstOrDefault(DataType defaultValue) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .firstOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> firstOrDefault(DataType defaultValue, Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .firstOrDefault(defaultValue, predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final <R> RxDataSource<R, LayoutBinding> flatMap(Func1<? super DataType, ? extends Observable<? extends R>> func) {
        List<R> newList = Observable.from(mRxAdapter.getDataSet())
                .flatMap(func)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<R, LayoutBinding>(mItem_layout, newList);
    }

    @Beta
    public final <R> RxDataSource<R, LayoutBinding> flatMap(Func1<? super DataType, ? extends Observable<? extends R>> func, int maxConcurrent) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(func, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final <R> RxDataSource<R, LayoutBinding> flatMap(
            Func1<? super DataType, ? extends Observable<? extends R>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends R>> onError,
            Func0<? extends Observable<? extends R>> onCompleted) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(onNext, onError, onCompleted)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    @Beta
    public final <R> RxDataSource<R, LayoutBinding> flatMap(
            Func1<? super DataType, ? extends Observable<? extends R>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends R>> onError,
            Func0<? extends Observable<? extends R>> onCompleted, int maxConcurrent) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(onNext, onError, onCompleted, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final <U, R> RxDataSource<R, LayoutBinding> flatMap(final Func1<? super DataType, ? extends Observable<? extends U>> collectionSelector,
                                                               final Func2<? super DataType, ? super U, ? extends R> resultSelector) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final <U, R> RxDataSource<R, LayoutBinding> flatMap(final Func1<? super DataType, ? extends Observable<? extends U>> collectionSelector,
                                                               final Func2<? super DataType, ? super U, ? extends R> resultSelector, int maxConcurrent) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(collectionSelector, resultSelector, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final <R> RxAdapter<R, LayoutBinding> flatMapIterable(Func1<? super DataType, ? extends Iterable<? extends R>> collectionSelector) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMapIterable(collectionSelector)
                .toList()
                .toBlocking()
                .first();
        return new RxAdapter<>(mItem_layout, newDataSet);
    }

    public final <U, R> RxDataSource<R, LayoutBinding> flatMapIterable(Func1<? super DataType, ? extends Iterable<? extends U>> collectionSelector,
                                                                       Func2<? super DataType, ? super U, ? extends R> resultSelector) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMapIterable(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final RxDataSource<DataType, LayoutBinding> reduce(Func2<DataType, DataType, DataType> accumulator) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .reduce(accumulator)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    //
    public final <R> RxDataSource<R, LayoutBinding> reduce(R initialValue, Func2<R, ? super DataType, R> accumulator) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .reduce(initialValue, accumulator)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSource<>(mItem_layout, newDataSet, mOnViewHolderInflated);
    }

    public final RxDataSource<DataType, LayoutBinding> repeat(final long count) {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        mRxAdapter.updateDataSet(Observable.from(dataSet)
                .repeat(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> repeat(final long count, Scheduler scheduler) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .repeat(count, scheduler)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> take(final int count) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .take(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> takeFirst(Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .takeFirst(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSource<DataType, LayoutBinding> takeLast(final int count) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .takeLast(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

}
