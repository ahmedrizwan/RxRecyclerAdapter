# RxRecyclerAdapter
A Rx based generic RecyclerView Adapter Library. 

## How does it work?
The library uses Databinding to send back the bindable layout, data item and position from the Adapter. 

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
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<RxAdapter<String, ItemLayoutBinding>.ViewItem>() {
            @Override
            public void call(final RxAdapter<String, ItemLayoutBinding>.ViewItem viewItem) {
                // Bind the view items with data here...
                viewItem.getViewDataBinding().textViewItem.setText(viewItem.getItem());
            }
        });
        
// set recyclerView layout manager and adapter 
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(rxAdapter);
```
And that's it!

<img src="https://raw.githubusercontent.com/ahmedrizwan/RxRecyclerAdapter/master/app/src/main/res/drawable/recycler_screenshot.png"  />


##Download 
Repository available on jCenter

```Gradle
compile 'com.minimize.android:rxrecycler-adapter:0.0.3'
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
