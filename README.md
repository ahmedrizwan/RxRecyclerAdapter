# RxRecyclerAdapter
Rx based generic RecyclerView Adapter Library. 

## How does it work?
You subscribe to the RxAdapter instance and it sends back the bindable layout, data item and position from the Adapter. This allows you to compose your items as they arrive! This also means that no more multiple RecyclerView.Adapter implementation-classes in your project!

## How to use it? 
#### Example!
- Enable Databinding by adding these lines to your build.gradle
```Gradle
dataBinding {
      enabled = true
}
```
- Create your dataSet
```java
//Dummy DataSet
List<String> dataSet = new ArrayList<>();
dataSet.add("This");
dataSet.add("is");
dataSet.add("an");
dataSet.add("example");
dataSet.add("of RX!");
```
- Create RxDataSource 
```java
RxDataSource<String> rxDataSource = new RxDataSource<>(dataSet);
//compose, bind and subscribe!
rxDataSource.map(String::toLowerCase)
      .repeat(10)
      //cast call this method with the binding Layout
      .<ItemLayoutBinding>bindRecyclerView(recyclerView, R.layout.item_layout) 
      .subscribe(viewHolder -> {
        ItemLayoutBinding b = viewHolder.getViewDataBinding();
        String item = viewHolder.getItem();
        b.textViewItem.setText(String.valueOf(item));
      });
```
And that's it! The recyclerView is going to show

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/sample/src/main/res/drawable/rx_adapter.png" width=400px  />

####Changing the data dynamically
Simply call updateAdapter after making changes to the dataSet and that'll do the trick!

```java
rxDataSource.map(...).filter(...).take(...).updateAdapter();
```

#### Adapter for multiple View Types
If multiple view types are required for your recyclerView, let's say, we have two types HEADER and ITEM then the coding steps will be :-
- Enable Databinding
- Create a list of ViewHolderInfo
```java 
List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_layout, TYPE_ITEM)); //TYPE_ITEM = 1
viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER)); //TYPE_HEADER = 0
```
- Create an instance of OnGetItemViewType implementation
```java
OnGetItemViewType viewTypeCallback = new OnGetItemViewType() {
      @Override public int getItemViewType(int position) {
        if (position % 2 == 0) //headers are at even pos
          return TYPE_HEADER;
        return TYPE_ITEM;
      }
    };
```
- Compose and call bindRecyclerView passing in recyclerView, viewHolderInfoList and viewTypeCallBack
```java
rxDataSource.map(...).filter(...)
     .bindRecyclerView(mActivityMainBinding.recyclerView, viewHolderInfoList,viewTypeCallback)
     .subscribe(vH -> {
      //Check instance type and bind!
      final ViewDataBinding b = vH.getViewDataBinding();
      if (b instanceof ItemLayoutBinding) {
        final ItemLayoutBinding iB = (ItemLayoutBinding) b;
        iB.textViewItem.setText("ITEM: " + vH.getItem());
      } else if (b instanceof ItemHeaderLayoutBinding) {
        ItemHeaderLayoutBinding hB = (ItemHeaderLayoutBinding) b;
        hB.textViewHeader.setText("HEADER: " + vH.getItem());
      }
    });
```
And the output would look something like

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/sample/src/main/res/drawable/rx_adapter_types.png" width=400px  />

More examples and details [here](https://medium.com/@ahmedrizwan/simplifying-recyclerview-adapters-with-rx-databinding-f02ebed0b386#.6vy6aq3k8) 

##Download 
Repository available on jCenter

```Gradle
compile 'com.minimize.android:rxrecycler-adapter:1.2.1'
```
*If the dependency fails to resolve, add this to your project repositories*
```Gradle
repositories {
  maven {
      url  "http://dl.bintray.com/ahmedrizwan/maven" 
  }
}
```

##License 
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
