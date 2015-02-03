package com.prolificinteractive.creditcard;

import java.util.regex.Pattern;

public interface CreditCard {

  Pattern getTypePattern();

  Pattern getVerifyPattern();

  int[] getFormat();
}
