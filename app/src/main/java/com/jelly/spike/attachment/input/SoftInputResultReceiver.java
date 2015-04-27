package com.jelly.spike.attachment.input;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.inputmethod.InputMethodManager;

public class SoftInputResultReceiver extends ResultReceiver {

    private final SoftInputReceiverListener listener;
    private int resultCode;

    public SoftInputResultReceiver(final Handler handler) {
        this(handler, null);
    }

    public SoftInputResultReceiver(Handler handler, SoftInputReceiverListener listener) {
        super(handler);
        this.resultCode = InputMethodManager.RESULT_UNCHANGED_HIDDEN;
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {
        this.resultCode = resultCode;
        if (this.listener != null) {
            if (resultCode == InputMethodManager.RESULT_SHOWN) {
                this.listener.onSoftInputOpen();
            } else if (resultCode == InputMethodManager.RESULT_HIDDEN) {
                this.listener.onSoftInputClose();
            } else {
                //(resultCode == InputMethodManager.RESULT_UNCHANGED_HIDDEN)
                this.listener.onSoftInputUnchanged(resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        }
    }

    public boolean isSoftInputShown() {
        return (this.resultCode == InputMethodManager.RESULT_SHOWN) || (this.resultCode == InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public interface SoftInputReceiverListener {
        void onSoftInputUnchanged(boolean isOpen);

        void onSoftInputOpen();

        void onSoftInputClose();
    }
}
