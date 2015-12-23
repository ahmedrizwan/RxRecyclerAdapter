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
- Create RxDataSource object (make sure your item_layout is bindable)
```java
//Dummy DataSet
List<String> dataSet = new ArrayList<>();
dataSet.add("Hello");
dataSet.add("");
dataSet.add("This");
dataSet.add("is  An");
dataSet.add("example");
dataSet.add("of RX!");

//RxDataSource
//Note: ItemLayoutBinding is generated from item_layout.xml by the Databinding Library
RxDataSource<String, ItemLayoutBinding> rxDataSource = new RxDataSource<>(R.layout.item_layout, dataSet);
```
- Compose and call getAdapter passing in your recyclerView and subscribe!
```java
rxDataSource.map(String::toLowerCase)
                .map(s -> s.replace(" ", ""))
                .map(s -> s.toCharArray().length)
                .repeat(10)
                .getRxAdapter(recyclerView)
                .subscribe(viewHolder -> {
                    ItemLayoutBinding binding = viewHolder.getViewDataBinding();
                    int item = viewHolder.getItem();
                    binding.textViewItem.setText(item + "");
                });
```

The output :

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/app/src/main/res/drawable/recycler_adapter.png" width=400px  />


#### Adapter for multiple View Types
If multiple view types are required for your recyclerView, then use RxDataSourceForTypes. For example, if we have two types HEADER and ITEM then the coding steps will be :-
- Enable Databinding
- Create a list of ViewHolderInfo
```java 
List<ViewHolderInfo> viewHolderInfoList = new ArrayList<>();
viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_layout, TYPE_ITEM)); //TYPE_ITEM = 1
viewHolderInfoList.add(new ViewHolderInfo(R.layout.item_header_layout, TYPE_HEADER)); //TYPE_HEADER = 0
```
- Create RxDataSourceForTypes by passing in dataSet, viewHolderInfoList and implementation for getViewItemType
```java
RxDataSourceForTypes<String> rxDataSourceForTypes = new RxDataSourceForTypes<>(dataSet, viewHolderInfoList, new OnGetItemViewType() {
            @Override
            public int getItemViewType(final int position) {
                if (position % 2 == 0) //headers are at even pos
                    return TYPE_HEADER;
                return TYPE_ITEM;
            }
        });
```
- Compose and call getAdapter... Subscribe!
```java
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
```
And the output would look like

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/app/src/main/res/drawable/recycler_adapter_types.png" width=400px  />
##Download 
Repository available on jCenter

```Gradle
compile 'com.minimize.android:rxrecycler-adapter:1.1.0'
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
