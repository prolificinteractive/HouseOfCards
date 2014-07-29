package com.prolific.creditcard;

import java.util.regex.Pattern;

public interface CreditCard {

  Pattern getTypePattern();

  Pattern getVerifyPattern();

  int[] getFormat();
}
