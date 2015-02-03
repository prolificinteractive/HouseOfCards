package com.example.creditcard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import com.prolific.creditcard.R;
import com.prolific.creditcard.android.CreditCardTextWatcher;

public class MyActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);

    EditText editText = (EditText) findViewById(R.id.edit_text);
    editText.addTextChangedListener(new CreditCardTextWatcher());
  }
}
