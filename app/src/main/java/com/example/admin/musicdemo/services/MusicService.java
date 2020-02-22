package com.example.admin.musicdemo.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.admin.musicdemo.R;
import com.example.admin.musicdemo.activitys.WelcomeActivity;
import com.example.admin.musicdemo.helps.MediaPlayerHelp;
import com.example.admin.musicdemo.models.MusicModel;

/**
 * 1、通过Service 连接PlayMusicView 和 MediaPlayHelper
 * 2、PlayMusicView -- Service:
 *      1、播放音乐、暂停音乐
 *      2、启动Service、绑定Service、接触绑定Service
 * 3、MediaPlayHelper -- Service:
 *      1、播放音乐、暂停音乐
 *      2、监听音乐播放完成，停止Service
 */
public class MusicService extends Service {

//    不可为0
    public static final int NOTIFICATION_ID = 1;

    private MediaPlayerHelp mMediaPlayerHelp;
    private MusicModel mMusicModel;
    public MusicService() {
    }

    public class MusicBind extends Binder{
        /**
         * 设置音乐（MusicModel）
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void setMusic(MusicModel musicModel){
            mMusicModel = musicModel;
            startForeground();
        }

        /**
         * 播放音乐
         */
        public void playMusic(){
            /*
             * 1、判断当前音乐是否是已经在播放的音乐
             * 是：直接执行start方法
             * 否：调用setPath方法*/
            if (mMediaPlayerHelp.getPath() != null && mMediaPlayerHelp.getPath().equals(mMusicModel.getPath())) {
                mMediaPlayerHelp.start();
            }else {
                mMediaPlayerHelp.setPath(mMusicModel.getPath());
                mMediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelp.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        /**
         * 暂停播放
         */
        public void stopMusic(){
            mMediaPlayerHelp.pause();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
       return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayerHelp = MediaPlayerHelp.getInstance(this);
    }

    /**
     * 系统默认不允许不可见的后台服务播放音乐，
     * Notification ,
     */
    /**
     * 设置服务在前台可见
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startForeground (){

        /**
         * 通知栏点击跳转的intent
         */
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this,0,new Intent(this, WelcomeActivity.class),PendingIntent.FLAG_CANCEL_CURRENT);
        /**
         * 创建Notification
         */
        Notification notification = null;
        /**
         * android API26以上 NotificationChannel特性适配
         */
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = createNotificationChannel();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(this,channel.getId())
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
        }else {
            notification = new Notification.Builder(this)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
        }

        /**
         * 设置 notification 在前台展示
         */
        startForeground(NOTIFICATION_ID,notification);

    }

    /**
     * 请求悬浮窗权限
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel(){
        String channelld = "cloud";
        String channelName = "CloudMusicService";
        String Description = "CloudMusic";
        NotificationChannel channel = new NotificationChannel(channelld,channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(Description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
        channel.setShowBadge(false);

        return channel;
    }
}
