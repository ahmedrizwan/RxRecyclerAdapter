package com.minimize.android.rxrecyclerexample;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.minimize.android.rxrecycleradapter.OnGetItemViewType;
import com.minimize.android.rxrecycleradapter.RxDataSource;
import com.minimize.android.rxrecycleradapter.RxDataSourceForTypes;
import com.minimize.android.rxrecycleradapter.ViewHolderInfo;
import com.minimize.android.rxrecyclerexample.databinding.ActivityMainBinding;
import com.minimize.android.rxrecyclerexample.databinding.ItemHeaderLayoutBinding;
import com.minimize.android.rxrecyclerexample.databinding.ItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final int TYPE_HEADER = 0;
    final int TYPE_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //ViewHolderInfo List
        List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_layout, TYPE_ITEM));
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER));

        //Dummy DataSet
        List<String> dataSet = new ArrayList<>();
        dataSet.add("Hello");
        dataSet.add("");
        dataSet.add("This");
        dataSet.add("is  An");
        dataSet.add("example");
        dataSet.add("of RX!");

        /***   For a single item-type binding - Adapter creation would be like :-
         //Create rxAdapter
         RxAdapterForTypes<String> rxAdapter = new RxAdapterForTypes<>(dataSet, viewHolderInfoList, new OnGetItemViewType() {
        @Override public int getItemViewType(final int position) {
        if (position % 2 == 0) //headers are at even pos
        return TYPE_HEADER;
        return TYPE_ITEM;
        }
        });

         //Call asObservable and subscribe
         rxAdapter.asObservable()
         .subscribe(vH -> {
         //Check instance type and bind!
         final ViewDataBinding b = vH.getViewDataBinding();
         if (b instanceof ItemLayoutBinding) {
         final ItemLayoutBinding iB = (ItemLayoutBinding) b;
         iB.textViewItem.setText("ITEM: " + vH.getItem());
         } else if (b instanceof ItemHeaderLayoutBinding) {
         final ItemHeaderLayoutBinding hB = (ItemHeaderLayoutBinding) b;
         hB.textViewHeader.setText("HEADER: " + vH.getItem());
         }
         });
         ***/
        //Set adapter
        mActivityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RxDataSource<String, ItemLayoutBinding> rxDataSource = new RxDataSource<>(R.layout.item_layout, dataSet);
        rxDataSource.repeat(10)
                .map(String::toLowerCase)
                .map(s -> s.replace(" ", ""))
                .filter(s -> !s.isEmpty())
                .take(5)
                .getRxAdapter(mActivityMainBinding.recyclerView)
                .subscribe(viewHolder -> {
                    ItemLayoutBinding binding = viewHolder.getViewDataBinding();
                    String item = viewHolder.getItem();
                    binding.textViewItem.setText(item + "");
                });

        RxDataSourceForTypes<String> rxDataSourceForTypes = new RxDataSourceForTypes<>(dataSet, viewHolderInfoList, new OnGetItemViewType() {
            @Override
            public int getItemViewType(final int position) {
                if (position % 2 == 0) //headers are at even pos
                    return TYPE_HEADER;
                return TYPE_ITEM;
            }
        });
        rxDataSourceForTypes.repeat(2)
                .map(String::toUpperCase)
                .filter(s -> !s.isEmpty())
                .getRxAdapter(mActivityMainBinding.recyclerView)
                .subscribe(
                        vH -> {
                            //Check instance type and bind!
                            final ViewDataBinding b = vH.getViewDataBinding();
                            if (b instanceof ItemLayoutBinding) {
                                final ItemLayoutBinding iB = (ItemLayoutBinding) b;
                                iB.textViewItem.setText("ITEM: " + vH.getItem());
                            } else if (b instanceof ItemHeaderLayoutBinding) {
                                final ItemHeaderLayoutBinding hB = (ItemHeaderLayoutBinding) b;
                                hB.textViewHeader.setText("HEADER: " + vH.getItem());
                            }
                        });
    }

}
