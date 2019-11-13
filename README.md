
# RxRecyclerAdapter

[![Release](https://img.shields.io/badge/jCenter-1.3.1-brightgreen.svg)](https://bintray.com/sbrukhanda/maven/FragmentViewPager)
[![GitHub license](https://img.shields.io/badge/license-Apache%20Version%202.0-blue.svg)](https://github.com/sbrukhanda/fragmentviewpager/blob/master/LICENSE.txt)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxRecyclerAdapter-green.svg?style=flat)](https://android-arsenal.com/details/1/2084)

Rx based generic RecyclerView Adapter Library.

## How to use it?
#### Example!
- Enable Databinding by adding these lines to your build.gradle
```kotlin
dataBinding {
      enabled = true
}
```
- Create the layout file
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="@dimen/activity_horizontal_margin">

        <TextView android:id="@+id/textViewItem"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  tools:text="Recycler Item"/>

    </LinearLayout>
</layout>
```
- Create your dataSet
```kotlin
//Dummy DataSet
val dataSet = mutableListOf<String>()
dataSet.add("Lorem")
dataSet.add("ipsum")
dataSet.add("dolor")
dataSet.add("sit")
dataSet.add("amet")
```
- Create RxDataSource
```kotlin
// Simple data source
val rxDataSource = RxDataSource<ItemLayoutBinding, String>(R.layout.item_layout, dataSet)

rxDataSource
        .map { it.toUpperCase() }
        .repeat(4)
        .asObservable()
        .subscribe {
            val binding = it.viewDataBinding ?: return@subscribe
            binding.textViewItem.text = it.item
        }
```
And that's it! The recyclerView is going to show

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/sample/src/main/res/drawable/rx_adapter.png" width=400px  />

#### Changing the data dynamically
Simply call updateAdapter after making changes to the dataSet and that'll do the trick!

```java
rxDataSource.map(...).filter(...).take(...).updateAdapter();
```

#### Adapter for multiple View Types
If multiple view types are required for your recyclerView, let's say, we have two types HEADER and ITEM then the coding steps will be :-
- Enable Databinding
- Create a list of ViewHolderInfo
```kotlin
//ViewHolderInfo List
val viewHolderInfoList = ArrayList<ViewHolderInfo>()
viewHolderInfoList.add(ViewHolderInfo(R.layout.item_layout, TYPE_ITEM))
viewHolderInfoList.add(ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER))
```
- Create an instance of RxDataSourceSectioned implementation
```kotlin
 // Sectioned data source
val rxDataSourceSectioned = RxDataSourceSectioned(dataSet, viewHolderInfoList, object : OnGetItemViewType() {
    override fun getItemViewType(position: Int): Int {
        if (position % 2 == 0) { // even are headers
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }
})
```
- Compose and call bindRecyclerView passing in recyclerView, viewHolderInfoList and viewTypeCallBack
```kotlin
rxDataSourceSectioned
    .asObservable()
    .subscribe {
        val viewDataBinding = it.viewDataBinding
        val data = it.item

        when (viewDataBinding) {
            is ItemLayoutBinding -> viewDataBinding.textViewItem.text = "ITEM: " + data
            is ItemHeaderLayoutBinding -> viewDataBinding.textViewHeader.text = "HEADER: " + data
        }
    }
```
And the output would look something like

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/sample/src/main/res/drawable/rx_adapter_types.png" width=400px  />

More examples and details [here](https://medium.com/@ahmedrizwan/simplifying-recyclerview-adapters-with-rx-databinding-f02ebed0b386#.6vy6aq3k8)

### How to update adapter?
You can update all the data set by call updateDataSet(), then call updateUdapter()  to update the adapter (get "notifiyDataSetChange()" effect)
```kotlin
rxDataSource.updateDataSet(newDataSet)
rxDataSource.updateAdapter()
```
  If you want to update one item of the data set, you can use updateDataSet(updatedList, effectedPosition, transactionType),
```kotlin
rxDataSource.updateDataSet(
  dataSet.apply { removeAt(deletedPosition) },
  deletedPosition,
  RxDataSource.TransactionTypes.DELETE
)
```
## Download
Repository available on jCenter

```Gradle
implementation 'com.minimize.android:rxrecycler-adapter:1.3.2'
```
*If the dependency fails to resolve, add this to your project repositories*
```Gradle
repositories {
  maven {
      url  "http://dl.bintray.com/ahmedrizwan/maven"
  }
}
```

## License
```
Copyright 2015 Ahmed Rizwan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
