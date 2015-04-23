package com.jelly.spike.attachment.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

import com.jelly.spike.attachment.adapter.IconAdapter;

public class IconGridView extends GridView {

    private static final int DEFAULT_SPACING = 0;

    private static int[] Attributes = {
            android.R.attr.horizontalSpacing,
            android.R.attr.verticalSpacing,
            android.R.attr.padding,
    };

    private int horizontalSpacing;
    private int verticalSpacing;
    private int padding;

    public IconGridView(final Context context) {
        super(context);
    }

    public IconGridView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.initialize(attrs);
    }

    public IconGridView(final Context context, final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IconGridView(final Context context, final AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initialize(attrs);
    }

    private void initialize(final AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, Attributes);
        try {
            // Ignore API 15 restrictions
            this.horizontalSpacing = typedArray.getDimensionPixelSize(0, DEFAULT_SPACING);
            this.verticalSpacing = typedArray.getDimensionPixelSize(1, DEFAULT_SPACING);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = heightMeasureSpec;
        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    public void onGlobalLayout() {
        super.onGlobalLayout();

        final IconAdapter adapter = (IconAdapter) this.getAdapter();
        final int columnCount = adapter.getColumnCount();
        final int rowCount = adapter.getRowCount();

        if (columnCount != GridView.AUTO_FIT) {
            final int iconWidth = (this.getWidth() / columnCount) - this.horizontalSpacing;
            final int iconHeight = (this.getHeight() / rowCount) - this.verticalSpacing;
            adapter.setIconDimensionPixelSize(Math.min(iconWidth, iconHeight));
        }
    }
}
