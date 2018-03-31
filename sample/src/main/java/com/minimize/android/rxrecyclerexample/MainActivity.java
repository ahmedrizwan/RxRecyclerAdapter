package com.minimize.android.rxrecyclerexample;

import com.minimize.android.rxrecycleradapter.RxDataSource;
import com.minimize.android.rxrecycleradapter.ViewHolderInfo;
import com.minimize.android.rxrecyclerexample.databinding.ActivityMainBinding;
import com.minimize.android.rxrecyclerexample.databinding.ItemLayoutBinding;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    final int TYPE_HEADER = 0;
    final int TYPE_ITEM = 1;
    List<String> dataSet;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mActivityMainBinding = DataBindingUtil.setContentView(this,
                                                                                  R.layout.activity_main);

        //ViewHolderInfo List
        List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_layout, TYPE_ITEM));
        viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER));

        //Dummy DataSet
        dataSet = new ArrayList<>();
        dataSet.add("This");
        dataSet.add("is");
        dataSet.add("an");
        dataSet.add("example");
        dataSet.add("of RX!");

        mActivityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RxDataSource<String> rxDataSource = new RxDataSource<>(dataSet);
        rxDataSource.map(String::toLowerCase).limit(3)
                    //cast call this method with the binding Layout
                .<ItemLayoutBinding>bindRecyclerView(mActivityMainBinding.recyclerView,
                                                     R.layout.item_layout).subscribe(viewHolder -> {
            ItemLayoutBinding b = viewHolder.getViewDataBinding();
            b.textViewItem.setText(viewHolder.getItem());
        });

        dataSet = rxDataSource.getRxAdapter().getDataSet();


    }
}
