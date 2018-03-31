package com.minimize.android.rxrecycleradapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by ahmedrizwan on 26/12/2015.
 */
public class RxDataSource<DataType>
{

    private List<DataType> mDataSet;
    private RxAdapter mRxAdapter;
    private RxAdapterForTypes<DataType> mRxAdapterForTypes;

    public RxDataSource(List<DataType> dataSet)
    {
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
            @NonNull final RecyclerView recyclerView, @NonNull @LayoutRes final int item_layout)
    {
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
    @Nullable
    public RxAdapter getRxAdapter()
    {
        return mRxAdapter;
    }

    /***
     * Call this if you need access to the Adapter!
     * Warning: might return null!
     *
     * @return RxAdapter Instance
     */
    @Nullable
    public RxAdapterForTypes<DataType> getRxAdapterForTypes()
    {
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
            @NonNull final OnGetItemViewType viewTypeCallback)
    {
        mRxAdapter = null;
        mRxAdapterForTypes = new RxAdapterForTypes<>(mDataSet, viewHolderInfoList,
                                                     viewTypeCallback);
        recyclerView.setAdapter(mRxAdapterForTypes);
        return mRxAdapterForTypes.asObservable();
    }

    /***
     * For setting base dataSet
     */
    public RxDataSource<DataType> updateDataSet(List<DataType> dataSet)
    {
        mDataSet = dataSet;
        return this;
    }

    /***
     * For updating Adapter
     */
    public void updateAdapter()
    {
        if (mRxAdapter != null)
        {
            //update the update
            mRxAdapter.updateDataSet(mDataSet);
        }
        else if (mRxAdapterForTypes != null)
        {
            mRxAdapterForTypes.updateDataSet(mDataSet);
        }
    }

    // Transformation methods

    public RxDataSource<DataType> map(Function<? super DataType, ? extends DataType> func)
    {
        mDataSet = Observable.fromIterable(mDataSet).map(func).toList().blockingGet();
        return this;
    }

    public RxDataSource<DataType> filter(Predicate<? super DataType> predicate)
    {
        mDataSet = Observable.fromIterable(mDataSet).filter(predicate).toList().blockingGet();
        return this;
    }

    public final RxDataSource<DataType> last() {
        mDataSet = Collections.singletonList(Observable.fromIterable(mDataSet).blockingLast());
        return this;
    }

    public final RxDataSource<DataType> lastOrDefault(DataType defaultValue) {
        mDataSet = Observable.fromIterable(mDataSet)
                             .takeLast(1)
                             .defaultIfEmpty(defaultValue)
                             .toList()
                             .blockingGet();
        return this;
    }

    public final RxDataSource<DataType> limit(int count) {
        mDataSet = Observable.fromIterable(mDataSet).take(count).toList().blockingGet();
        return this;
    }

    public final RxDataSource<DataType> repeat(final long count)
    {
        List<DataType> dataSet = mDataSet;
        mDataSet = Observable.fromIterable(dataSet).repeat(count).toList().blockingGet();
        return this;
    }

    public RxDataSource<DataType> empty()
    {
        mDataSet = Collections.emptyList();
        return this;
    }

}
