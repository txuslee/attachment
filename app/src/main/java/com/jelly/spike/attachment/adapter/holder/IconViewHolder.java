package com.jelly.spike.attachment.adapter.holder;

import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jelly.spike.attachment.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class IconViewHolder {

    private static int CurrentColorIndex = 0;

    private static int[] ColorId = {
            android.R.color.holo_blue_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_red_dark,
            android.R.color.holo_green_dark,
            android.R.color.holo_purple,
            android.R.color.darker_gray,
    };

    @InjectView(R.id.chat_imv_attach_action_image)
    public ImageView image;
    public View root;

    private IconViewHolder(final ViewGroup parent) {
        this.root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attach_action, parent, false);
        ButterKnife.inject(this, root);
        this.root.setTag(this);
    }

    public static IconViewHolder get(final View convertView, final ViewGroup parent) {
        if (convertView == null) {
            return new IconViewHolder(parent);
        }
        return (IconViewHolder) convertView.getTag();
    }

    public void setIconResourceDimension(@DrawableRes int iconResource, @DimenRes int iconSize) {
        final Resources resources = this.root.getResources();
        int size = resources.getDimensionPixelSize(iconSize);
        this.setIconResourcePixelSize(iconResource, size);
    }

    public void setIconResourcePixelSize(@DrawableRes int iconResource, int size) {
        this.root.setLayoutParams(new FrameLayout.LayoutParams(size, size));
        this.root.setBackgroundColor(this.getNextColor());
        this.image.setImageResource(iconResource);
    }

    @ColorRes
    public int getNextColor() {
        int color = ColorId[CurrentColorIndex];
        CurrentColorIndex = (CurrentColorIndex + 1) % ColorId.length;
        return this.root.getResources().getColor(color);
    }
}
