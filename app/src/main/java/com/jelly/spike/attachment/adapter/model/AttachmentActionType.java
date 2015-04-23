package com.jelly.spike.attachment.adapter.model;

import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;

import com.jelly.spike.attachment.R;

public enum AttachmentActionType {
    PhotoAttachment(R.drawable.selector_attach_camera_image),
    VideoAttachment(R.drawable.selector_attach_camera_image),
    GalleryAttachment(R.drawable.selector_attach_gallery),
    LocationAttachment(R.drawable.selector_attach_location),
    FileAttachment(R.drawable.selector_attach_document);

    private final int resourceId;

    private AttachmentActionType(@DrawableRes int resourceId) {
        this.resourceId = resourceId;
    }

    @DrawableRes
    public int getResourceId() {
        return this.resourceId;
    }

    @DimenRes
    public int getDimensionId() {
        return R.dimen.button_attachment_action_icons_size;
    }
}
