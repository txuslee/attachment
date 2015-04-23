package com.jelly.spike.attachment.adapter;

import android.widget.ListAdapter;

public interface ActionIconAdapter extends ListAdapter {
    int getIconDimensionPixelSize();

    boolean setIconDimensionPixelSize(int dimensionPixelSize);
}
