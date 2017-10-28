# NumberArcView#
Number view with arc

## Preview ##
![preview](https://github.com/freecats/Resources/blob/master/NumberArcView.png)
## How to get it work？ ##

* in xml layout file

```java
 <com.example.freecats.numberarcview.view.NumberArcView
        android:id="@+id/nav"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:nav_endColor="#0adeff"
        app:nav_height="116dp"
        app:nav_number="100"
        app:nav_shadowColor="#f0f0f0"
        app:nav_startColor="#0086f9"
        app:nav_text="Money"
        app:nav_unit="$"
        app:nav_width="116dp" />
```

<br>Attributes Support as below

| Attributes            |format|
| -------------|------------- |
| nav_startColor      | color |
| nav_endColor     | color |
| nav_shadowColor     |color  |
| nav_numberColor | color|
| nav_textColor  |color  |
| nav_unitColor       |color |
| nav_arcWidth       |dimension  |
| nav_shadowWidth   |dimension |
| nav_textSize      | dimension |
| nav_numberTextSize     | dimension |
| nav_unitTextSize     |dimension  |
| nav_numberColor | dimension|
| nav_width  |dimension  |
| nav_height       |dimension |
| nav_textMarginNumber       |dimension  |
| nav_unitMarginNumber   |dimension |
| nav_isFloat | boolean|
| nav_text  |string  |
| nav_unit       |string |
| nav_number       |float  |
| nav_radian   |integer |

# License



    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
