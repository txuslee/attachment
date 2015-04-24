package com.jelly.spike.attachment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;

import com.jelly.spike.attachment.adapter.impl.AttachmentIconAdapter;
import com.jelly.spike.attachment.adapter.impl.RowDependentIconAdapter;
import com.jelly.spike.attachment.adapter.model.AttachmentActionType;
import com.jelly.spike.attachment.view.ActionIconGridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.lyt_attach_action)
    protected ActionIconGridView attachmentActionsGridView;

    private boolean isAttachmentActionShown = false;

    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        final Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        final Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Number of columns has not been set yet until onStart
        final RowDependentIconAdapter adapter = new RowDependentIconAdapter(new AttachmentIconAdapter(this), 2);
        this.attachmentActionsGridView.setAdapter(adapter);
        this.attachmentActionsGridView.setOnItemClickListener(this);
        this.attachmentActionsGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove layout listener before any other layout action is taken
                attachmentActionsGridView.removeOnGlobalLayoutListener(this);
                // Hide it here instead of doing it in layout file to force layout calculation on load
                hide(attachmentActionsGridView);
            }
        });
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
        if (this.isAttachmentActionShown) {
            this.attachmentActionsGridView.collapse();
        } else {
            this.attachmentActionsGridView.expand();
        }
        this.isAttachmentActionShown = !this.isAttachmentActionShown;
    }

    private void hide(final View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.GONE);
    }

    private void reveal(final View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, int position, long id) {
        final AttachmentActionType type = (AttachmentActionType) parent.getItemAtPosition(position);
        switch (type) {
            case PhotoAttachment:
                Log.d(TAG, "Camera image click");
                break;
            case VideoAttachment:
                Log.d(TAG, "Camera video click");
                break;
            case GalleryAttachment:
                Log.d(TAG, "Gallery click");
                break;
            case LocationAttachment:
                Log.d(TAG, "Location click");
                break;
            case DocumentAttachment:
                Log.d(TAG, "Document click");
                break;
            default:
                break;

        }
    }

}
