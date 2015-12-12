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
- Create RxAdapter (make sure your item_layout is bindable)
```java
// a list of data to be displayed in recyclerView...
List<String> myList = new ArrayList<>();
myList.add("Hello");
myList.add("Rx");
myList.add("Example");

// create adapter by telling it what the data type and ViewDataBinding type is...
RxAdapter<String, ItemLayoutBinding> rxAdapter = new RxAdapter<>(R.layout.item_layout, myList);
// Note: ItemLayoutBinding is generated from item_layout.xml by the Databinding Library
```
- Call asObservable on the adapter and subscribe
```java
rxAdapter.asObservable()
        .subscribe( viewItem -> {
                // Bind the view items with data here...
                final ItemLayoutBinding binding = viewItem.getViewDataBinding();
                final String text = viewItem.getItem();
                
                binding.textView.setText(text);
            });
        
// set recyclerView layout manager and adapter 
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(rxAdapter);
```
And that's it!

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/app/src/main/res/drawable/recycler_adapter.png" width=400px  />

#### Adapter for multiple View Types
If multiple view types are required for your recyclerView, then use RxAdapterForTypes. For example, if we have two types HEADER and ITEM then the coding steps will be :-
- Enable Databinding
- Create a list of ViewHolderInfo
```java 
List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_layout, TYPE_ITEM)); //TYPE_ITEM = 1
viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER)); //TYPE_HEADER = 0
```
- Create RxAdapterForTypes by passing in dataSet, viewHolderInfoList and implementation for getViewItemType
```java
RxAdapterForTypes<String> rxAdapter = new RxAdapterForTypes<>(dataSet, viewHolderInfoList, new OnGetItemViewType() {
            @Override
            public int getItemViewType(final int position) {
                if (position % 2 == 0) //headers are at even pos
                    return TYPE_HEADER;
                return TYPE_ITEM;
            }
        });
```
- Call asObservable and subscribe
```java
rxAdapter.asObservable()
        .subscribe(viewItem -> {
            //Check instance type and bind!
            final ViewDataBinding binding = viewItem.getViewDataBinding();
            if (binding instanceof ItemLayoutBinding) {
                final ItemLayoutBinding itemBinding = (ItemLayoutBinding) binding;
                itemBinding.textViewItem.setText("ITEM: " + viewItem.getItem());
            } else if (binding instanceof ItemHeaderLayoutBinding) {
                final ItemHeaderLayoutBinding headerBinding = (ItemHeaderLayoutBinding) binding;
                headerBinding.textViewHeader.setText("HEADER: " + viewItem.getItem());
            }
        });
```
##Download 
Repository available on jCenter

```Gradle
compile 'com.minimize.android:rxrecycler-adapter:0.1'
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
