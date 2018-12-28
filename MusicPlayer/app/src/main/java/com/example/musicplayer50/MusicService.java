package com.example.musicplayer50;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * musicservice 音乐服务
 *
 * 1、start函数，控制播放或者暂停。 startnew函数，得到一首音乐的地址，播放这一首音乐。     
 *
 * 2、开启另外一个线程handle更新主界面的歌曲进度条，发送一次进度信息后，线程休息一秒钟，然后继续发送下一秒的进度信息。
 *
 * 3、onStartCommand用来接收活动传递过来的数据，根据action做出相应操作
 *
 */
public class MusicService extends Service {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MyBinder myBinder;

    private static final int SET_SEEKBAR_MAX = 3;
    private static final int UPDATE_PROGRESS = 1;
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();

    }


    @Override

    //很多的Service就是通过Binder机制来和客户端通讯交互的
    public IBinder onBind(Intent intent) {
        // return the binder to the activity
        myBinder = new MyBinder();
        return myBinder;
    }


    public void start() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                Intent intent1 = new Intent("pauseimage");
                sendBroadcast(intent1);         //发送intent的方式
            } else {
                mediaPlayer.start();
                Intent intent2 = new Intent("playimage");
                sendBroadcast(intent2);
            }
        }
    }
    //播放下一首音乐
    public void startnew(String path) throws Exception {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();}

        mediaPlayer.release();
        mediaPlayer = null;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepare();
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//Media Player的播放完成事件绑定事件监听器
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("dai","结束监听器创建成功");
                Intent intent12 = new Intent("nextsong");
                sendBroadcast(intent12);

            }
        });
        Intent intent1 = new Intent("playimage");
        sendBroadcast(intent1);
        handler.sendEmptyMessage(SET_SEEKBAR_MAX);
        handler.sendEmptyMessage(UPDATE_PROGRESS);

    }

    private Handler handler = new Handler() {
    @SuppressLint("HandlerLeak")
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_PROGRESS:
                // 设置最大当前播放进度
                Intent intent = new Intent("seekbarprogress");
                intent.putExtra("seekbarprogress",
                        mediaPlayer.getCurrentPosition());
                sendBroadcast(intent);
                // 需要随着播放设置
                handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 1000);
                break;
            case SET_SEEKBAR_MAX:
                intent = new Intent("seekbarmaxprogress");
                intent.putExtra("seekbarmaxprogress", mediaPlayer.getDuration());
                sendBroadcast(intent);
                // 因为进度条只需要设置一次就够了,所以不需要反复发送Message;
                break;
            default:
                break;
              }
            };
           };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("startnew")) {
            try {
             //   mediaPlayer.release();
                Toast.makeText(getApplicationContext(), intent.getStringExtra("title"), Toast.LENGTH_SHORT).show();
                startnew(intent.getStringExtra("url"));
                Log.e("dai","接收到“启动活动，播放音乐");
                Intent titleintent = new Intent("gettitle");
                titleintent.putExtra("title",intent.getStringExtra("title"));
                titleintent.putExtra("url",intent.getStringExtra("url"));
                titleintent.putExtra("artist",intent.getStringExtra("artist"));
                Log.e("dai","发送一个新广播给两活动，主活动接收标题，更改Text内容。");
                sendBroadcast(titleintent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (intent.getAction().equals("changed")) {// 如果是拖动的Action,就跳至拖动的进度
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(intent.getIntExtra("seekbarprogress", 0));
            }
        }
        return super.onStartCommand(intent,flags,startId);
    }


    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }
}