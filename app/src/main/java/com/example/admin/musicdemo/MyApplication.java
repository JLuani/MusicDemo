package com.example.admin.musicdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.example.admin.musicdemo.helps.RealmHelp;

import io.realm.Realm;

/**
 * Created by yjl on 2020/1/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        Realm.init(this);

        RealmHelp.magration();
    }
}
