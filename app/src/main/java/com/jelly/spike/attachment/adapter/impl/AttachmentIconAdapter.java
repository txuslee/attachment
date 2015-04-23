package com.jelly.spike.attachment.adapter.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jelly.spike.attachment.R;
import com.jelly.spike.attachment.adapter.ActionIconAdapter;
import com.jelly.spike.attachment.adapter.holder.ActionIconViewHolder;
import com.jelly.spike.attachment.adapter.model.AttachmentActionType;

public class AttachmentIconAdapter extends BaseAdapter implements ActionIconAdapter {

    private final Context context;
    private int iconDimensionPixelSize;

    public AttachmentIconAdapter(final Context context) {
        this.iconDimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.button_attachment_action_icons_size);
        this.context = context;
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
        final ActionIconViewHolder vh = ActionIconViewHolder.get(convertView, parent);
        final AttachmentActionType type = (AttachmentActionType) this.getItem(position);
        vh.setIconResourcePixelSize(type.getResourceId(), this.getIconDimensionPixelSize());
        return vh.root;
    }

    @Override
    public int getIconDimensionPixelSize() {
        return this.iconDimensionPixelSize;
    }

    @Override
    public boolean setIconDimensionPixelSize(int dimensionPixelSize) {
        // Avoid refresh loop
        if (this.iconDimensionPixelSize != dimensionPixelSize) {
            this.iconDimensionPixelSize = dimensionPixelSize;
            return true;
        }
        return false;
    }
}