package com.angolamais.kawakuticode.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.angolamais.kawakuticode.angola.Listen_Radio_activity;
import com.angolamais.kawakuticode.models.RadioModel;

import java.io.IOException;

/**
 * Created by russeliusernestius on 31/01/17.
 */

public class Radio_Service extends Service implements MediaPlayer.OnErrorListener
        , MediaPlayer.OnPreparedListener {

    private MediaPlayer md;
    private RadioModel mRadio;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // The MediaPlayer has moved to the Error state, must be reset!
        mp.reset();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();
        //Toast.makeText(this, "you are Now listening  Radio " + Radios.get(radioServiceIntent.getStringExtra("radioUrlnew")), Toast.LENGTH_LONG).show();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mRadio = intent.getExtras().getParcelable("radio");
        //set to foreground
        md = new MediaPlayer();

        try {
            md.setAudioStreamType(AudioManager.STREAM_MUSIC);
            md.setDataSource(mRadio.getRadio_url());
            md.setOnPreparedListener(this);
            md.setOnErrorListener(this);
            md.prepareAsync();

        } catch (IOException media) {
            media.printStackTrace();

        }
        ShowNotification(intent);
        //Note: You can start a new thread and use it for long background processing from here.
        return START_STICKY;

    }

    public void ShowNotification(Intent intent) {

    /*    Notification notification = new Notification(android.R.drawable.ic_media_play, "ANGOLA+ ",
                );*/


        Notification notification = new Notification.Builder(this.getBaseContext())
                .setContentText(mRadio.getRadio_name())
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setWhen(System.currentTimeMillis())
                .build();


        Intent notificationIntent = new Intent(this, Listen_Radio_activity.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        //intent.getStringExtra("radioUrlnew");

        CharSequence contentTitle = "ANGOLA+";
        CharSequence contentText = "Your are Listening Radio " + mRadio.getRadio_name();
        /*notification.setLatestEventInfo(this, contentTitle,
                contentText, pendingIntent);*/
        startForeground(1, notification);


    }

    @Override
    public void onDestroy() {
        if (md.isPlaying()) {
            stopForeground(true);
            md.stop();
            md.release();

        }
    }

}

