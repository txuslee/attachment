package com.jelly.spike.attachment.input;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.inputmethod.InputMethodManager;

public class SoftInputResultReceiver extends ResultReceiver {

    private boolean isSoftInputShown;

    public SoftInputResultReceiver(final Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {
        if ((resultCode == InputMethodManager.RESULT_SHOWN) || (resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN)) {
            this.isSoftInputShown = true;
        } else if ((resultCode == InputMethodManager.RESULT_HIDDEN) || (resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN)) {
            this.isSoftInputShown = false;
        }
    }

    public boolean isSoftInputShown() {
        return this.isSoftInputShown;
    }
}
