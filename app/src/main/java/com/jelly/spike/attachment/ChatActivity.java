package com.jelly.spike.attachment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.jelly.spike.attachment.input.SoftInputResultReceiver;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ChatActivity extends ActionBarActivity {

    @InjectView(R.id.lyt_chat_root)
    protected ViewGroup root;

    @InjectView(R.id.edt_chat_text)
    protected EditText text;

    //@InjectView(R.id.lyt_chat_action)
    //protected FrameLayout action;

    private SoftInputResultReceiver softInputResultReceiver;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_chat);
        ButterKnife.inject(this);

        this.softInputResultReceiver = new SoftInputResultReceiver(this.getWindow().getDecorView().getHandler());

        //final View content = LayoutInflater.from(this).inflate(R.layout.layout_attachment_action, null, false);
        //final int popupHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, this.getResources().getDisplayMetrics());
        //this.popupWindow = new PopupWindow(content, WindowManager.LayoutParams.MATCH_PARENT, popupHeight, false);
        //popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);

        this.text.clearFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View view, boolean hasFocus) {
                if ((view == text) && (!hasFocus)) {
                    //final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.root.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, this.softInputResultReceiver);

        //this.action.setVisibility(View.GONE);
        //this.popupWindow.dismiss();
    }

    @OnClick(R.id.edt_chat_text)
    public void onEditTextClick(final View view) {
        //this.popupWindow.showAtLocation(this.root, Gravity.BOTTOM, 0, 0);
        //this.popupWindow.showAsDropDown(this.text);

        //this.action.setVisibility(View.VISIBLE);
        //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.action.getLayoutParams();
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //this.action.setLayoutParams(params);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, this.softInputResultReceiver);
    }
}
