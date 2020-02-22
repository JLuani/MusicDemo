package com.example.admin.musicdemo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.example.admin.musicdemo.R;

/**
 * Created by yjl on 2020/1/27.
 */

public class BaseActivity extends Activity {
    private ImageView mIvBack,mTvMe;
    private TextView mTvTitle;

    /**
     * findViewById
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id)
    {
        return findViewById(id);
    }

    /**
     * 初始化NavigationBar
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    protected void initNavBar(boolean isShowBack,String title,boolean isShowMe)
    {
        mIvBack=fd(R.id.iv_back);
        mTvTitle=fd(R.id.tv_title);
        mTvMe=fd(R.id.iv_me);

        mIvBack.setVisibility(isShowBack ? View.VISIBLE:View.GONE);
        mTvMe.setVisibility(isShowMe ? View.VISIBLE:View.GONE);
        mTvTitle.setText(title);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this,MeActivity.class));
            }
        });
    }
}
