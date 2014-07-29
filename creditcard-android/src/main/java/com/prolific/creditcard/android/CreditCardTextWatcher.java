package com.prolific.creditcard.android;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.prolific.creditcard.CreditCardUtil;
import java.lang.ref.WeakReference;

public class CreditCardTextWatcher implements TextWatcher {

  final WeakReference<EditText> weakReference;
  final CreditCardUtil cardUtil;

  boolean changingText;
  int cursorPos;
  int editVelocity;

  public CreditCardTextWatcher(EditText editText, CreditCardUtil creditCardUtil) {
    weakReference = new WeakReference<EditText>(editText);
    cardUtil = creditCardUtil;
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
    EditText editText = weakReference.get();
    if (editText == null) {
      return;
    }

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
      editText.setSelection(cursorPos);
    }
  }
}
