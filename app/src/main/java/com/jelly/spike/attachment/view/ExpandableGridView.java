package com.jelly.spike.attachment.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.jelly.spike.attachment.adapter.impl.RowDependentIconAdapter;
import com.jelly.spike.attachment.listener.animator.SimpleAnimatorListener;

public class ExpandableGridView extends GridView {

    private static final int DEFAULT_SPACING = 0;

    private static int[] Attributes = {
            android.R.attr.horizontalSpacing,
            android.R.attr.verticalSpacing,
            android.R.attr.numColumns,
    };

    private boolean forcedLayout;
    private int horizontalSpacing;
    private int verticalSpacing;
    private int columnCount;

    public ExpandableGridView(final Context context) {
        super(context);
    }

    public ExpandableGridView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.initialize(attrs);
    }

    public ExpandableGridView(final Context context, final AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableGridView(final Context context, final AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initialize(attrs);
    }

    private void initialize(final AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, Attributes);
        try {
            // Ignore API 15 restrictions
            this.horizontalSpacing = typedArray.getDimensionPixelSize(0, DEFAULT_SPACING);
            this.verticalSpacing = typedArray.getDimensionPixelSize(1, DEFAULT_SPACING);
            this.columnCount = typedArray.getInteger(2, GridView.AUTO_FIT);
            this.forcedLayout = true;
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        final RowDependentIconAdapter adapter = (RowDependentIconAdapter) this.getAdapter();
        if (adapter != null) {
            // Changed (false) means original size in layout editor
            if (this.forcedLayout) {
                this.updateIconDimensionPixelSize(adapter);
                this.forcedLayout = false;
            }
        }
    }

    private void updateIconDimensionPixelSize(final RowDependentIconAdapter adapter) {
        final int iconWidth = (this.getWidth() / this.columnCount) - this.horizontalSpacing;
        final int iconHeight = (this.getHeight() / adapter.getRowCount()) - this.verticalSpacing;
        adapter.setIconDimensionPixelSize(Math.min(iconWidth, iconHeight));
    }

    @Override
    public int getHorizontalSpacing() {
        return this.horizontalSpacing;
    }

    @Override
    public int getVerticalSpacing() {
        return this.verticalSpacing;
    }

    public void removeOnGlobalLayoutListener(final ViewTreeObserver.OnGlobalLayoutListener layoutListener) {
        final ViewTreeObserver observer = this.getViewTreeObserver();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            observer.removeGlobalOnLayoutListener(layoutListener);
        } else {
            observer.removeOnGlobalLayoutListener(layoutListener);
        }
    }

    public void collapse() {
        final int height = this.getHeight();
        final ValueAnimator animator = this.layoutHeightAnimator(height, 0);
        animator.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator layoutHeightAnimator(int start, int end) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update height
                final int value = (Integer) valueAnimator.getAnimatedValue();
                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = value;
                setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(100);
        return animator;
    }

    public void expand() {
        this.setVisibility(VISIBLE);

        final int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        final int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        this.measure(widthSpec, heightSpec);

        final ValueAnimator animator = layoutHeightAnimator(0, getMeasuredHeight());
        animator.start();
    }
}
