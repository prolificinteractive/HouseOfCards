HouseOfCards
============

Credit Card Validation
-----

![FRANK UNDERWOOD](http://img4.wikia.nocookie.net/__cb20140215085441/house-of-cards/images/9/9f/Season_2_Chapter_26.jpg)

Tools for working with a house of (credit) cards.

Usage
-----

It's as simple as:

```
editText.addTextChangedListener(new CreditCardTextWatcher());
```

You can also pass in your own instance of `CreditCardUtil` to control what card types are used:

```
CreditCardUtil cardUtil = new CreditCardUtil(CreditCardUtil.VISA);
editText.addTextChangedListener(new CreditCardTextWatcher(cardUtil));
```

And you're not just limited to built in types! Just implement `CreditCard`!

License
=======

>Copyright 2015 Prolific Interactive
>
>Licensed under the Apache License, Version 2.0 (the "License");
>you may not use this file except in compliance with the License.
>You may obtain a copy of the License at
>
>   http://www.apache.org/licenses/LICENSE-2.0
>
>Unless required by applicable law or agreed to in writing, software
>distributed under the License is distributed on an "AS IS" BASIS,
>WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
>See the License for the specific language governing permissions and
>limitations under the License.