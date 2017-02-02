package com.angolamais.kawakuticode.angola;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.angolamais.kawakuticode.models.RadioModel;
import com.angolamais.kawakuticode.services.Radio_Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Listen_Radio_activity extends AppCompatActivity implements View.OnClickListener {

    TextView radio_text_name;
    TextView radio_intro_text;
    TextView radio_date_text;
    ImageButton play, stop;
    RadioModel radio_intent;
    MediaPlayer player;
    private SeekBar playSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen__radio_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeMediaPlayer();
        initializeUIElements();

    }

    private void initializeUIElements() {

        Intent i = getIntent();
        radio_intent = i.getExtras().getParcelable("radio");

        radio_text_name = (TextView) findViewById(R.id.radio_name);
        radio_intro_text = (TextView) findViewById(R.id.text_intro_sint);
        radio_date_text = (TextView) findViewById(R.id.date_time);


        radio_text_name.setText(radio_intent.getRadio_name());
        radio_intro_text.setText(radio_intent.getIntro_message());
        radio_intro_text.setSelected(true);

        DateFormat date = SimpleDateFormat.getDateInstance();
        radio_date_text.setText(date.format(Calendar.getInstance().getTime()));

        play = (ImageButton) findViewById(R.id.play_button);
        stop = (ImageButton) findViewById(R.id.stop_button);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == play && isNetworkAvailable()) {
            startPlaying();
            Snackbar.make(v, "Buffering radio content ... ", Snackbar.LENGTH_LONG).show();
            stop.setVisibility(v.VISIBLE);
            play.setVisibility(v.INVISIBLE);

        } else {

            stopPlaying();
            stop.setVisibility(v.INVISIBLE);
            play.setVisibility(v.VISIBLE);

        }


    }


    private void startPlaying() {

        if (isMyServiceRunning(Radio_Service.class)) {
            Intent i = new Intent(this, Radio_Service.class);
            this.stopService(i);
            player.stop();
            player.release();
            stop.setEnabled(true);
            play.setEnabled(false);

            Intent newIntent = new Intent(this, Radio_Service.class);
            newIntent.putExtra("radio", radio_intent);
            startService(newIntent);

        } else {

            stop.setEnabled(true);
            play.setEnabled(false);
            // playSeekBar.setVisibility(View.VISIBLE);


            Intent newIntent = new Intent(this, Radio_Service.class);
            newIntent.putExtra("radio", radio_intent);
            startService(newIntent);

        }

    }

    private void stopPlaying() {
        Intent i = new Intent(this, Radio_Service.class);
        this.stopService(i);

        if (player.isPlaying()) {
            player.stop();
            player.release();
            //initializeMediaPlayer();
        }
        play.setEnabled(true);
        stop.setEnabled(false);
        // playSeekBar.setVisibility(View.INVISIBLE);
    }

    private void initializeMediaPlayer() {


        try {
            player = new MediaPlayer();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                //Log.i("Buffering", "" + percent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
