package com.example.admin.musicdemo.helps;

//完成用户自动登录

/**
 * 1、用户登录
 *      当用户登录时，利用SharePreference 保存登录用户的用户标记（手机号）
 *      利用全局单例类UserHelper保存登录用户信息
 *          用户登录之后
 *          用户重新打开应用程序，检测SharePreference中是否存在登录用户标记
 *          ，若存在则为UserHelp进行赋值，并且进入主页。若不存在，则进入登录界面
 * 2、用户退出
 *      删除SharePreference保存的用户标记，退出到登录界面。
 */
public class UserHelper {

    private static UserHelper instance;
    private UserHelper(){};
    public static UserHelper getInstance(){
        if (instance == null)
        {
            synchronized (UserHelper.class){
                if (instance == null)
                {
                    instance = new UserHelper();
                }
            }
        }
        return instance;
    }

    private String phone;
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
