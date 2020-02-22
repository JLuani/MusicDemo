package com.example.admin.musicdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;

import com.example.admin.musicdemo.R;

/**
 * Created by yjl on 2020/2/2.
 */

public class MainView extends FrameLayout {

    private int mainFill;
    private String mainText;

    private View mView,viewMainFill;
    private TextView tvMainText;
    public MainView(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public MainView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MainView( @NonNull Context context,  @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MainView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs)
    {
        if (attrs==null)
        {
            return;
        }

        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.mainView);
        mainFill=typedArray.getColor(R.styleable.mainView_main_fill,Color.RED);
        mainText=typedArray.getString(R.styleable.mainView_main_text);
        typedArray.recycle();

        mView= LayoutInflater.from(context).inflate(R.layout.main_view,this,false);
        viewMainFill=mView.findViewById(R.id.view_mainfill);
        tvMainText=mView.findViewById(R.id.tv_maintext);

        viewMainFill.setBackgroundColor(mainFill);
        tvMainText.setText(mainText);
        addView(mView);
    }
}
