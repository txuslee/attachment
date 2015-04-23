package com.jelly.spike.attachment.adapter;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jelly.spike.attachment.R;
import com.jelly.spike.attachment.adapter.holder.IconViewHolder;
import com.jelly.spike.attachment.adapter.model.AttachmentActionType;


public class IconAdapter extends BaseAdapter {

    private final Context context;
    private final int columns;
    private final int rows;
    private int iconDimensionPixelSize;

    public IconAdapter(final Context context, int columns, int rows) {
        this.iconDimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.button_attachment_action_icons_size);
        this.context = context;
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return AttachmentActionType.values().length;
    }

    @Override
    public Object getItem(int position) {
        return AttachmentActionType.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return AttachmentActionType.values()[position].ordinal();
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        final IconViewHolder vh = IconViewHolder.get(convertView, parent);
        final AttachmentActionType type = (AttachmentActionType) this.getItem(position);
        vh.setIconResourcePixelSize(type.getResourceId(), this.iconDimensionPixelSize);
        return vh.root;
    }

    @DimenRes
    private int getIconDimensionId() {
        return R.dimen.button_attachment_action_icons_size;
    }

    public int getIconDimensionPixelSize() {
        return this.iconDimensionPixelSize;
    }

    public void setIconDimensionPixelSize(int dimensionPixelSize) {
        // Avoid refresh loop
        if (this.iconDimensionPixelSize != dimensionPixelSize) {
            this.iconDimensionPixelSize = dimensionPixelSize;
            this.notifyDataSetChanged();
        }
    }

    public int getColumnCount() {
        return this.columns;
    }

    public int getRowCount() {
        return this.rows;
    }
}