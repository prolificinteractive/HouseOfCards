package com.prolificinteractive.creditcard.android;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import com.prolificinteractive.creditcard.CreditCard;
import com.prolificinteractive.creditcard.CreditCardUtil;

import static com.prolificinteractive.creditcard.CreditCardUtil.AMERICAN_EXPRESS;
import static com.prolificinteractive.creditcard.CreditCardUtil.DINERS_CLUB;
import static com.prolificinteractive.creditcard.CreditCardUtil.DISCOVER;
import static com.prolificinteractive.creditcard.CreditCardUtil.JCB_15;
import static com.prolificinteractive.creditcard.CreditCardUtil.JCB_16;
import static com.prolificinteractive.creditcard.CreditCardUtil.MASTERCARD;
import static com.prolificinteractive.creditcard.CreditCardUtil.VISA;

/**
 * A {@linkplain android.text.TextWatcher} that will format credit card numbers as the user types
 */
public class CreditCardTextWatcher implements TextWatcher {

  final CreditCardUtil cardUtil;

  boolean changingText = false;
  int cursorPos = 0;
  int editVelocity = 0;

  /**
   * Create an instance that will use the provided {@linkplain com.prolificinteractive.creditcard.CreditCardUtil}
   * for validation and formatting
   *
   * @param creditCardUtil an instance to use
   */
  public CreditCardTextWatcher(CreditCardUtil creditCardUtil) {
    cardUtil = creditCardUtil;
  }

  /**
   * Create an instance that will only format for the provided {@linkplain
   * com.prolificinteractive.creditcard.CreditCard}s
   *
   * @param cards cards to format for
   */
  public CreditCardTextWatcher(CreditCard... cards) {
    this(new CreditCardUtil(cards));
  }

  /**
   * Create an instance that will use all major CreditCard types
   *
   * @see com.prolificinteractive.creditcard.CreditCardUtil#VISA
   * @see com.prolificinteractive.creditcard.CreditCardUtil#MASTERCARD
   * @see com.prolificinteractive.creditcard.CreditCardUtil#AMERICAN_EXPRESS
   * @see com.prolificinteractive.creditcard.CreditCardUtil#DISCOVER
   * @see com.prolificinteractive.creditcard.CreditCardUtil#DINERS_CLUB
   */
  public CreditCardTextWatcher() {
    this(
        VISA,
        MASTERCARD,
        AMERICAN_EXPRESS,
        DISCOVER,
        DINERS_CLUB,
        JCB_15,
        JCB_16
    );
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    if (!changingText) {
      editVelocity = (count - before);
      cursorPos = (start + count);
    }
  }

  @Override public void afterTextChanged(Editable s) {
    if (!changingText) {
      changingText = true;
      setText(s);
      changingText = false;
    }
  }

  void setText(Editable s) {
    String formattedText = cardUtil.formatForViewing(s);
    s.replace(0, s.length(), formattedText);

    int i = cursorPos;
    int formattedTextLength = formattedText.length();
    if (cursorPos >= formattedTextLength) {
      cursorPos = formattedTextLength;
    }
    if ((editVelocity > 0)
        && (cursorPos > 0)
        && (formattedText.charAt(-1 + cursorPos) == ' ')) {
      this.cursorPos += 1;
    }
    if ((editVelocity < 0)
        && (cursorPos > 1)
        && (formattedText.charAt(-1 + cursorPos) == ' ')) {
      cursorPos -= 1;
    }
    if (cursorPos != i) {
      Selection.setSelection(s, cursorPos);
    }
  }
}
