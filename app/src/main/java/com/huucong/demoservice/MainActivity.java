package com.huucong.demoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mButtonPlay;
    private boolean mIsPlaying;
    private MusicService mMusicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mButtonPlay = findViewById(R.id.button_play);
        mButtonPlay.setOnClickListener(this);
    }

    private void playSong(){
        mIsPlaying = true;
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
    }

    private void stopSong(){
        mIsPlaying = false;
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_play) {
            if(!mIsPlaying){
                mButtonPlay.setImageResource(R.drawable.ic_pause_white_48dp);
                playSong();
            } else {
                mButtonPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                stopSong();
            }
        }
    }


    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MusicService.class));
        super.onDestroy();
    }

}
