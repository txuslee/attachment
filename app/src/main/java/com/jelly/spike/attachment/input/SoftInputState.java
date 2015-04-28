package com.jelly.spike.attachment.input;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.LinkedList;
import java.util.List;


public class SoftInputState implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final int MIN_SOFT_INPUT_HEIGHT = 100;

    private final List<SoftKeyboardStateListener> listeners;
    private final View root;
    private int lastSoftKeyboardHeightInPx;
    private boolean isSoftKeyboardOpened;


    public SoftInputState(final View root) {
        this(root, false);
    }

    public SoftInputState(final View root, boolean isSoftKeyboardOpened) {
        this.listeners = new LinkedList<SoftKeyboardStateListener>();
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
        this.root = root;
        root.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        final int heightDiff = root.getRootView().getHeight() - (root.getHeight());
        if (!isSoftKeyboardOpened && heightDiff > MIN_SOFT_INPUT_HEIGHT) { // if more than 100 pixels, its probably a keyboard...
            isSoftKeyboardOpened = true;
            notifyOnSoftKeyboardOpened(heightDiff);
        } else if (isSoftKeyboardOpened && heightDiff < MIN_SOFT_INPUT_HEIGHT) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }

    public void setIsSoftKeyboardOpened(boolean isSoftKeyboardOpened) {
        this.isSoftKeyboardOpened = isSoftKeyboardOpened;
    }

    public boolean isSoftKeyboardOpened() {
        return isSoftKeyboardOpened;
    }

    /**
     * Default value is zero (0)
     *
     * @return last saved keyboard height in px
     */
    public int getLastSoftKeyboardHeightInPx() {
        return lastSoftKeyboardHeightInPx;
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        this.lastSoftKeyboardHeightInPx = keyboardHeightInPx;

        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }

    public interface SoftKeyboardStateListener {
        void onSoftKeyboardOpened(int keyboardHeightInPx);

        void onSoftKeyboardClosed();
    }
}