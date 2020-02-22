package com.example.admin.musicdemo.helps;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * 1、直接在Activity中创建播放音乐，音乐与Activity绑定，Activity运行时播放音乐，Activity退出时音乐就会停止播放
 * 2、通过全局单例类与Application绑定，Application运行时播放音乐，Application被杀死时音乐就会停止播放
 * 3、通过service 进行音乐播放，音乐播放只依赖后台服务，service运行时播放音乐，service被杀死时音乐就会停止播放
 */
public class MediaPlayerHelp {

    private static MediaPlayerHelp instance;

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;


    public static MediaPlayerHelp getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (MediaPlayerHelp.class)
            {
                if (instance == null)
                {
                    instance=new MediaPlayerHelp(context);
                }
            }
        }

        return instance;
    }
    private MediaPlayerHelp(Context context)
    {
        mContext=context;
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 1、setPath：当前需要播放的音乐
     * 2、start：播放音乐
     * 3、pause：暂停播放
     */

//    当前需要播放的音乐
    public void setPath(String path)
    {

        /**
         * 当音乐进行切换时，如果音乐处于播放状态，那么就重载音乐播放状态
         * 如果音乐没有处于播放状态（暂停），那么就不重置音乐播放状态
         */
//        1、音乐正在播放或者切换了音乐，重置音乐播放状态
        if (mMediaPlayer.isPlaying()|| !path.equals(mPath))
        {
            mMediaPlayer.reset();
        }
        mPath = path;

//        2、设置播放音乐路径
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        3、准备播放
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                if (onMediaPlayerHelperListener !=null)
                {
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });

//        监听音乐播放完成
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });
    }

//    返回正在播放的音乐路径
    public String getPath()
    {
        return mPath;
    }
//    播放音乐
    public void start()
    {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

//    暂停播放
    public void pause()
    {
        mMediaPlayer.pause();
    }

    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }

    public interface OnMediaPlayerHelperListener
    {
        void onPrepared(MediaPlayer mp);
        void onCompletion(MediaPlayer mp);
    }
}
