package com.prolificinteractive.creditcard.android;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import com.prolificinteractive.creditcard.CreditCard;
import com.prolificinteractive.creditcard.CreditCardUtil;

import static com.prolificinteractive.creditcard.CreditCardUtil.*;

public class CreditCardTextWatcher implements TextWatcher {

  final CreditCardUtil cardUtil;

  boolean changingText = false;
  int cursorPos = 0;
  int editVelocity = 0;

  public CreditCardTextWatcher(CreditCardUtil creditCardUtil) {
    cardUtil = creditCardUtil;
  }

  public CreditCardTextWatcher(CreditCard... cards) {
    this(new CreditCardUtil(cards));
  }

  public CreditCardTextWatcher() {
    this(
        VISA,
        MASTERCARD,
        AMERICAN_EXPRESS,
        DISCOVER,
        DINERS_CLUB
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
    String formattedText = cardUtil.formatForViewing(s, cardUtil.findCardType(s.toString()));
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
