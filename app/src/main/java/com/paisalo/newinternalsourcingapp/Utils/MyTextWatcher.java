package com.paisalo.newinternalsourcingapp.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
public abstract class MyTextWatcher implements TextWatcher {
    private final EditText editText;

    public MyTextWatcher(EditText textView) {
        this.editText = textView;
    }

    public abstract void validate(EditText editText, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = editText.getText().toString();
        validate(editText, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
