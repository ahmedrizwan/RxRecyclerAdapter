package com.minimize.android.rxrecycleradapter;

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
 * Created by ahmedrizwan on 23/12/2015.
 *
 */
public class RxDataSourceForTypes<DataType> {

    private final List<ViewHolderInfo> mViewHolderInfoList;
    private final OnGetItemViewType mViewTypeCallback;
    RxAdapterForTypes<DataType> mRxAdapter;

    public RxDataSourceForTypes(final List<DataType> dataSet, final List<ViewHolderInfo> viewHolderInfoList, OnGetItemViewType viewTypeCallback) {
        mViewHolderInfoList = viewHolderInfoList;
        mViewTypeCallback = viewTypeCallback;
        mRxAdapter = new RxAdapterForTypes<>(dataSet, viewHolderInfoList, viewTypeCallback);
    }

    public final RxDataSourceForTypes<DataType> last() {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        mRxAdapter.updateDataSet(Observable.from(dataSet)
                .last()
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> last(Func1<? super DataType, Boolean> predicate) {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        mRxAdapter.updateDataSet(Observable.from(dataSet)
                .last(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> lastOrDefault(DataType defaultValue) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> lastOrDefault(DataType defaultValue, Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .filter(predicate)
                .takeLast(1)
                .singleOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> limit(int count) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .limit(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final <R> RxDataSourceForTypes<R> map(Func1<? super DataType, ? extends R> func) {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        List<R> newDataSet = Observable.from(dataSet)
                .map(func)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public Observable<TypesViewHolder<DataType>> getRxAdapter(final RecyclerView recyclerView) {
        recyclerView.setAdapter(mRxAdapter);
        return mRxAdapter.asObservable();
    }

    public RxDataSourceForTypes<DataType> empty() {
        mRxAdapter.updateDataSet((List<DataType>) Collections.emptyList());
        return this;
    }

    public final <R> RxDataSourceForTypes<R> concatMap(Func1<? super DataType, ? extends Observable<? extends R>> func) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .concatMap(func)
                .toList()
                .toBlocking()
                .first();

        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final RxDataSourceForTypes<DataType> concatWith(Observable<? extends DataType> t1) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .concatWith(t1)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public RxDataSourceForTypes<DataType> distinct() {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .distinct()
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public RxDataSourceForTypes<DataType> distinct(Func1<? super DataType, ? extends Object> keySelector) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .distinct(keySelector)
                .toList()
                .toBlocking()
                .first());
        return this;
    }


    public final RxDataSourceForTypes<DataType> elementAt(int index) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .elementAt(index)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> elementAtOrDefault(int index, DataType defaultValue) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .elementAtOrDefault(index, defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public RxDataSourceForTypes<DataType> filter(Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .filter(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> first() {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .first()
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> first(Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .first(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> firstOrDefault(DataType defaultValue) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .firstOrDefault(defaultValue)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> firstOrDefault(DataType defaultValue, Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .firstOrDefault(defaultValue, predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final <R> RxDataSourceForTypes<R> flatMap(Func1<? super DataType, ? extends Observable<? extends R>> func) {
        List<R> newList = Observable.from(mRxAdapter.getDataSet())
                .flatMap(func)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<R>(newList, mViewHolderInfoList, mViewTypeCallback);
    }

    @Beta
    public final <R> RxDataSourceForTypes<R> flatMap(Func1<? super DataType, ? extends Observable<? extends R>> func, int maxConcurrent) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(func, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final <R> RxDataSourceForTypes<R> flatMap(
            Func1<? super DataType, ? extends Observable<? extends R>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends R>> onError,
            Func0<? extends Observable<? extends R>> onCompleted) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(onNext, onError, onCompleted)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    @Beta
    public final <R> RxDataSourceForTypes<R> flatMap(
            Func1<? super DataType, ? extends Observable<? extends R>> onNext,
            Func1<? super Throwable, ? extends Observable<? extends R>> onError,
            Func0<? extends Observable<? extends R>> onCompleted, int maxConcurrent) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(onNext, onError, onCompleted, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final <U, R> RxDataSourceForTypes<R> flatMap(final Func1<? super DataType, ? extends Observable<? extends U>> collectionSelector,
                                                        final Func2<? super DataType, ? super U, ? extends R> resultSelector) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final <U, R> RxDataSourceForTypes<R> flatMap(final Func1<? super DataType, ? extends Observable<? extends U>> collectionSelector,
                                                        final Func2<? super DataType, ? super U, ? extends R> resultSelector, int maxConcurrent) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMap(collectionSelector, resultSelector, maxConcurrent)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final <R> RxDataSourceForTypes<R> flatMapIterable(Func1<? super DataType, ? extends Iterable<? extends R>> collectionSelector) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMapIterable(collectionSelector)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final <U, R> RxDataSourceForTypes<R> flatMapIterable(Func1<? super DataType, ? extends Iterable<? extends U>> collectionSelector,
                                                                Func2<? super DataType, ? super U, ? extends R> resultSelector) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .flatMapIterable(collectionSelector, resultSelector)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final RxDataSourceForTypes<DataType> reduce(Func2<DataType, DataType, DataType> accumulator) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .reduce(accumulator)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    //
    public final <R> RxDataSourceForTypes<R> reduce(R initialValue, Func2<R, ? super DataType, R> accumulator) {
        List<R> newDataSet = Observable.from(mRxAdapter.getDataSet())
                .reduce(initialValue, accumulator)
                .toList()
                .toBlocking()
                .first();
        return new RxDataSourceForTypes<>(newDataSet, mViewHolderInfoList, mViewTypeCallback);
    }

    public final RxDataSourceForTypes<DataType> repeat(final long count) {
        List<DataType> dataSet = mRxAdapter.getDataSet();
        mRxAdapter.updateDataSet(Observable.from(dataSet)
                .repeat(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> repeat(final long count, Scheduler scheduler) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .repeat(count, scheduler)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> take(final int count) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .take(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> takeFirst(Func1<? super DataType, Boolean> predicate) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .takeFirst(predicate)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

    public final RxDataSourceForTypes<DataType> takeLast(final int count) {
        mRxAdapter.updateDataSet(Observable.from(mRxAdapter.getDataSet())
                .takeLast(count)
                .toList()
                .toBlocking()
                .first());
        return this;
    }

}
