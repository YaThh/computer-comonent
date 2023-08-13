package com.example.appbanlinhkien30.util;

import android.widget.EditText;

public class FieldValidator {
    //Kiểm tra field rỗng
    public static Boolean valid = true;
    public static boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
}
