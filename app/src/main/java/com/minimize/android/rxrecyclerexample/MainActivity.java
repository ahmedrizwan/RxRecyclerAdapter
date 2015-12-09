package com.minimize.android.rxrecyclerexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.minimize.android.rxrecycleradapter.RxAdapter;
import com.minimize.android.rxrecyclerexample.databinding.ActivityMainBinding;
import com.minimize.android.rxrecyclerexample.databinding.ItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        List<String> myList = new ArrayList<>();
        myList.add("Hello");
        myList.add("Rx");
        myList.add("Example");

        RxAdapter<String, ItemLayoutBinding> rxAdapter = new RxAdapter<>(R.layout.item_layout, myList);
        rxAdapter.asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RxAdapter<String, ItemLayoutBinding>.Item>() {
                    @Override
                    public void call(final RxAdapter<String, ItemLayoutBinding>.Item item) {
                        item.getViewDataBinding().textViewItem.setText(item.getItem());
                        // item position = item.getPosition();
                    }
                });

        mActivityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mActivityMainBinding.recyclerView.setAdapter(rxAdapter);
    }
}
