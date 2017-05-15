# House Of Cards
[![Travis build status](https://img.shields.io/travis/prolificinteractive/houseofcards.svg?style=flat-square)](https://travis-ci.org/prolificinteractive/houseofcards)

_Tools for working with a house of (credit) cards._

![FRANK UNDERWOOD](http://img4.wikia.nocookie.net/__cb20140215085441/house-of-cards/images/9/9f/Season_2_Chapter_26.jpg)

## Installation

```gradle
compile 'com.prolificinteractive:houseofcards:1.0.1'
```

## Usage

Add a textWatcher to your EditText:
```
editText.addTextChangedListener(new CreditCardTextWatcher());
```

You can also pass in your own instance of `CreditCardUtil` to control what card types are used:

```
CreditCardUtil cardUtil = new CreditCardUtil(CreditCardUtil.VISA);
editText.addTextChangedListener(new CreditCardTextWatcher(cardUtil));
```

And you're not just limited to built in types! Just implement `CreditCard`!

## Contributing to House Of Cards

To report a bug or enhancement request, feel free to file an issue under the respective heading.

If you wish to contribute to the project, fork this repo and submit a pull request. Code contributions should follow the standards specified in the [Prolific Android Style Guide](https://github.com/prolificinteractive/android-code-styles).

## License

![prolific](https://s3.amazonaws.com/prolificsitestaging/logos/Prolific_Logo_Full_Color.png)

Copyright (c) 2017 Prolific Interactive

HouseOfCards is maintained and sponsored by Prolific Interactive. It may be redistributed under the terms specified in the [LICENSE] file.

[LICENSE]: ./LICENSE

## Name Disclaimer

This project is in no way affiliated with the Netflix Original Series _House of Cards_ or the UK television series of the same name.
This project is about credit card validation, not corrupt politicians.