package com.jelly.spike.attachment.adapter.impl;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jelly.spike.attachment.adapter.ActionIconAdapter;
import com.jelly.spike.attachment.adapter.RowActionIconAdapter;


public class RowDependentIconAdapter extends BaseAdapter implements RowActionIconAdapter {

    private final int rows;
    private final ActionIconAdapter adapter;

    public RowDependentIconAdapter(final ActionIconAdapter delegate, int rows) {
        this.adapter = delegate;
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return adapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return adapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        return adapter.getView(position, convertView, parent);
    }

    @Override
    public int getIconDimensionPixelSize() {
        return this.adapter.getIconDimensionPixelSize();
    }

    public boolean setIconDimensionPixelSize(int dimensionPixelSize) {
        // Avoid refresh loop
        if (this.adapter.setIconDimensionPixelSize(dimensionPixelSize)) {
            this.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public int getRowCount() {
        return this.rows;
    }
}