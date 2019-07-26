package com.huucong.demoservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;


public class MusicService extends Service {
    private MediaPlayer mMediaPlayer;
    private Notification mNotification;
    private int mCurrentPosition;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
        initNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopMusic();
        super.onDestroy();
    }

    public void pauseMusic(){
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mCurrentPosition = mMediaPlayer.getCurrentPosition();
        }
    }

    public void resumeMusic(){
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(mCurrentPosition);
            mMediaPlayer.start();
        }
    }

    public void stopMusic(){
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    private void initNotification(){
        Intent songPlayIntent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, songPlayIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotification = new Notification.Builder(getApplicationContext())
                .setTicker("Music Player")
                .setContentTitle("Media Player")
                .setContentText("The song was stopped!")
                .setSmallIcon(R.drawable.ic_pause_white_48dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }
    private void showNotification(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, mNotification);
    }

}
