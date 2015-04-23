package com.jelly.spike.attachment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import com.jelly.spike.attachment.adapter.IconAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.lyt_attach_action)
    protected GridView attachmentActionsGridView;

    private boolean isAttachmentActionShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        final IconAdapter adapter = new IconAdapter(this, attachmentActionsGridView.getNumColumns(), 2);
        this.attachmentActionsGridView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.btn_attachFile)
    public void onAttachmentClick(final View view) {
        if (isAttachmentActionShown) {
            hideAttachmentActions();
        } else {
            revealAttachmentActions();
        }
        isAttachmentActionShown = !isAttachmentActionShown;
    }

    private void hideAttachmentActions() {
        int height = this.attachmentActionsGridView.getHeight();
        final ValueAnimator animator = slideAnimator(height, 0);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                hide(attachmentActionsGridView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    private void revealAttachmentActions() {
        this.reveal(this.attachmentActionsGridView);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        this.attachmentActionsGridView.measure(widthSpec, heightSpec);

        final ValueAnimator animator = slideAnimator(0, this.attachmentActionsGridView.getMeasuredHeight());
        animator.start();
    }

    private ValueAnimator slideAnimator(int start, int end) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update height
                final int value = (Integer) valueAnimator.getAnimatedValue();
                final ViewGroup.LayoutParams layoutParams = attachmentActionsGridView.getLayoutParams();
                layoutParams.height = value;
                attachmentActionsGridView.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void hide(View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.GONE);
    }

    private void reveal(View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
    }

}
