package com.prolificinteractive.creditcard;

import java.util.regex.Pattern;

/**
 * Model for identifying, validating, and formatting credit cards
 */
public interface CreditCard {

  /**
   * @return a pattern which will be used to identify as this type using the first 4 digits
   */
  Pattern getTypePattern();

  /**
   * @return a pattern that can match non-formatted card numbers for this type
   */
  Pattern getVerifyPattern();

  /**
   * @return an array of grouping lengths. I.E. for VISA its { 4, 4, 4, 4 }
   */
  int[] getFormat();
}
