package com.example.creditcard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import com.prolific.creditcard.CreditCardUtil;
import com.prolific.creditcard.R;
import com.prolific.creditcard.android.CreditCardTextWatcher;

import static com.prolific.creditcard.CreditCardUtil.AMERICAN_EXPRESS;
import static com.prolific.creditcard.CreditCardUtil.DINERS_CLUB;
import static com.prolific.creditcard.CreditCardUtil.DISCOVER;
import static com.prolific.creditcard.CreditCardUtil.MASTERCARD;
import static com.prolific.creditcard.CreditCardUtil.VISA;

public class MyActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);

    EditText editText = (EditText) findViewById(R.id.edit_text);
    editText.addTextChangedListener(
        new CreditCardTextWatcher(editText, new CreditCardUtil(AMERICAN_EXPRESS,
            VISA,
            MASTERCARD,
            DISCOVER,
            DINERS_CLUB)));
  }
}
