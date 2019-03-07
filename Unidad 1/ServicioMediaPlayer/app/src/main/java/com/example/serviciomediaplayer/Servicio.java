package com.example.serviciomediaplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

public class Servicio extends Service implements MediaPlayer.OnPreparedListener{
    //objeto MediaPlayer
    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //obtenemos tono de sistema
        //player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        //metodo que ejecuta sin parar el tono
        //player.setLooping(true);

        //iniciar el reproductor
        //player.start();

        //opcion para iniciar/detener explicitamente el tono
        //return START_STICKY;

        try {
            //obtenemos tono del telefono (en base a la carpeta RES/RAW)
            player = MediaPlayer.create(this, R.raw.ayuwoki);
            player.setOnPreparedListener(this);
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        //metodo que ejecuta sin parar el tono
        player.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //detiene el reproductor cuando la actividad es destruida
        player.stop();
    }
}