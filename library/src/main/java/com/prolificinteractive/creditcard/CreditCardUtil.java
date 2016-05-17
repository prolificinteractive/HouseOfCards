package com.prolificinteractive.creditcard;

import android.widget.EditText;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A utility for validating and formatting credit card numbers
 */
public class CreditCardUtil {

  private static final int CARD_LENGTH_FOR_TYPE = 4;

  public static final Pattern CREDIT_CARD = Pattern.compile(
      "^(?:4[0-9]{12}(?:[0-9]{3})?"              // Visa
          + "|5[1-5][0-9]{14}"                   // MasterCard
          + "|3[47][0-9]{13}"                    // American Express
          + "|3(?:0[0-5]|[68][0-9])[0-9]{11}"    // Diners Club
          + "|6(?:011|5[0-9]{2})[0-9]{12}"       // Discover
          + "|(?:2131|1800|35\\d{3})\\d{11}"     // JCB
          + ")$"
  );

  private static final Pattern REGX_AMEX = Pattern.compile("^3[47][0-9]{13}$");
  private static final Pattern REGX_VISA = Pattern.compile("^4[0-9]{15}?");
  private static final Pattern REGX_MASTERCARD = Pattern.compile("^5[1-5][0-9]{14}$");
  private static final Pattern REGX_DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$");
  private static final Pattern REGX_DINERS_CLUB =
      Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$");
  private static final Pattern REGX_JCB_15 = Pattern.compile("^(?:2131|1800)\\d{11}$");
  private static final Pattern REGX_JCB_16 = Pattern.compile("^35[0-9]{14}$");

  public static final Pattern TYPE_AMEX = Pattern.compile("^3[47][0-9]{2}$");
  public static final Pattern TYPE_VISA = Pattern.compile("^4[0-9]{3}?");
  public static final Pattern TYPE_MASTERCARD = Pattern.compile("^5[1-5][0-9]{2}$");
  public static final Pattern TYPE_DISCOVER = Pattern.compile("^6(?:011|5[0-9]{2})$");
  public static final Pattern TYPE_DINERS_CLUB = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]$");
  public static final Pattern TYPE_JCB_15 = Pattern.compile("^(?:2131|1800)$");
  public static final Pattern TYPE_JCB_16 = Pattern.compile("^35[0-9]{2}$");

  public static final CreditCard AMERICAN_EXPRESS =
      new Card(REGX_AMEX, TYPE_AMEX, new int[] { 4, 6, 5 });
  public static final CreditCard VISA = new Card(REGX_VISA, TYPE_VISA, new int[] { 4, 4, 4, 4 });
  public static final CreditCard MASTERCARD =
      new Card(REGX_MASTERCARD, TYPE_MASTERCARD, new int[] { 4, 4, 4, 4 });
  public static final CreditCard DISCOVER =
      new Card(REGX_DISCOVER, TYPE_DISCOVER, new int[] { 4, 4, 4, 4 });
  public static final CreditCard DINERS_CLUB =
      new Card(REGX_DINERS_CLUB, TYPE_DINERS_CLUB, new int[] { 4, 4, 4, 2 });
  public static final CreditCard JCB_15 =
      new Card(REGX_JCB_15, TYPE_JCB_15, new int[] { 4, 4, 4, 3 });
  public static final CreditCard JCB_16 =
      new Card(REGX_JCB_16, TYPE_JCB_16, new int[] { 4, 4, 4, 4 });

  private final List<CreditCard> cards;

  /**
   * Create an instance that will only use the provided types to validate and format
   *
   * @param creditCards a list of types to use
   */
  public CreditCardUtil(CreditCard... creditCards) {
    cards = new ArrayList<>(Arrays.asList(creditCards));
  }

  /**
   * @param cardNumber The input to clean and format
   * @return if cardNumber matches a CreditCard, a formatted cardNumber, otherwise a cleaned version
   * of cardNumber
   */
  public String formatForViewing(CharSequence cardNumber) {
    return formatForViewing(cardNumber, findCardType(cardNumber));
  }

  /**
   * @param cardNumber the input to clean and format
   * @param card the CreditCard to format for, if possible
   * @return if cardNumber matches card, a formatted cardNumber, otherwise a cleaned version of
   * cardNumber
   */
  public static String formatForViewing(CharSequence cardNumber, CreditCard card) {
    // make sure the cc isn't null
    if (cardNumber == null) {
      throw new InvalidParameterException("cannot have null credit card number");
    }

    // clean up the string
    String cleanedCardNumber = clean(cardNumber);

    // return the cleaned string if the card is null
    if (card == null) {
      return cleanedCardNumber;
    }

    // make sure the format isn't null or empty
    int[] format = card.getFormat();
    if (format == null || format.length == 0) {
      throw new InvalidParameterException("cannot have null or empty credit card format");
    }

    // sum the children and make sure it only contains numbers greater than zero
    int sum = 0;
    for (int i : format) {
      if (i <= 0) {
        throw new InvalidParameterException("the pattern must contain numbers greater than zero");
      }
      sum += i;
    }

    // make sure the string is long enough
    int length = cleanedCardNumber.length();
    if (length <= 0) {
      return cleanedCardNumber;
    }

    if (length > sum) {
      length = sum;
    }

    return format(cleanedCardNumber, length, format);
  }

  /**
   * @param card the input to validate
   * @return true if card is a valid card for this instance's card types
   */
  public boolean validateCard(CharSequence card) {
    return validateCard(card, findCardType(card));
  }

  /**
   * @param card the input to validate
   * @param creditCard the card type to validate against
   * @return true if card is a valid card for creditCard
   */
  public static boolean validateCard(CharSequence card, CreditCard creditCard) {
    return creditCard.getVerifyPattern().matcher(card).matches();
  }

  /**
   * @param s the card number to find type of, can be partial
   * @return the instance of CreditCard matching s, or null if no match
   */
  public CreditCard findCardType(CharSequence s) {
    String cardNumber = clean(s);
    if (cardNumber.length() >= CARD_LENGTH_FOR_TYPE) {
      for (CreditCard card : cards) {
        if (card.getTypePattern()
            .matcher(cardNumber.subSequence(0, CARD_LENGTH_FOR_TYPE))
            .matches()) {
          return card;
        }
      }
    }

    return null;
  }

  /**
   * @param s the card number to find type of, can be partial
   * @return the instance of CreditCard matching s, or null if no match
   */
  public static boolean equalTypeCard(CharSequence s, CreditCard card) {
    String cardNumber = clean(s);
    if (cardNumber.length() >= CARD_LENGTH_FOR_TYPE) {
      if (card.getTypePattern()
          .matcher(cardNumber.subSequence(0, CARD_LENGTH_FOR_TYPE))
          .matches()) {
        return true;
      }
    }

    return false;
  }

  public static boolean isAmex(EditText editText) {
    return AMERICAN_EXPRESS.getTypePattern()
        .matcher(editText.getText().subSequence(0, CARD_LENGTH_FOR_TYPE))
        .matches();
  }

  public static boolean isDinersClub(EditText editText) {
    return DINERS_CLUB.getTypePattern()
        .matcher(editText.getText().subSequence(0, CARD_LENGTH_FOR_TYPE))
        .matches();
  }

  /**
   * @param cardNumber the input to clean
   * @return a string containing only digits
   */
  public static String clean(CharSequence cardNumber) {
    return cardNumber.toString().trim().replaceAll("[^\\d]", "");
  }

  /**
   * @param cleanedNumber a clean credit card number to format
   * @param length the length to limit
   * @param format an array of groups lengths
   * @return a formatted version of cleanedNumber
   */
  protected static String format(String cleanedNumber, int length, int[] format) {
    StringBuilder builder = new StringBuilder();

    int start = 0;
    int end = 0;
    for (int p : format) {
      end += p;
      final boolean isAtEnd = (end >= length);
      builder.append(cleanedNumber.substring(start, isAtEnd ? length : end));
      if (!isAtEnd) {
        builder.append(" ");
        start += p;
      } else {
        break;
      }
    }

    return builder.toString();
  }

  /**
   * Simple implementation for {@linkplain CreditCard}
   */
  protected static class Card implements CreditCard {

    final Pattern typePattern;
    final Pattern verifyPattern;
    final int[] format;

    public Card(Pattern verifyPattern, Pattern typePattern, int[] format) {
      this.verifyPattern = verifyPattern;
      this.typePattern = typePattern;
      this.format = format;
    }

    @Override public int[] getFormat() { return format; }

    @Override public Pattern getTypePattern() { return typePattern; }

    @Override public Pattern getVerifyPattern() { return verifyPattern; }
  }
}
