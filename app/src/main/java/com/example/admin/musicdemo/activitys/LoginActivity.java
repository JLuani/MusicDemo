package com.example.admin.musicdemo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.musicdemo.R;
import com.example.admin.musicdemo.utils.UserUtils;
import com.example.admin.musicdemo.views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    //初始化View
    private void initView()
    {
        initNavBar(false,"登录",false);

        mInputPhone=fd(R.id.inout_phone);
        mInputPassword=fd(R.id.input_password);
    }

    //跳转注册页面点击事件
    public void onRegisterClick(View v)
    {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    //登录
    public void onCommitClick(View v)
    {
        String phone=mInputPhone.getInputStr();
        String password=mInputPassword.getInputStr();

        //验证用户输入是否合法
        if (!UserUtils.validateLogin(this,phone,password))
        {
            return;
        }

        //跳转到应用主页
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
