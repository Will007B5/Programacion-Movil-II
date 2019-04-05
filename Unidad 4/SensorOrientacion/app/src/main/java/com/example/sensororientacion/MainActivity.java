package com.example.sensororientacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText;
    private SensorManager SM;
    private float x, y, z;
    private boolean bandera = false;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se crea el sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);

        try {
            //obtenemos tono del telefono (en base a la carpeta RES/RAW)
            player = MediaPlayer.create(this, R.raw.ayuwoki);
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        xText.setText("X: " + x);
        yText.setText("Y: " + y);
        zText.setText("Z: " + z);

        if((x>0) | (y<0) && bandera == false){
            Toast.makeText(this, "VOLTEADO",Toast.LENGTH_LONG).show();
            bandera = true;
            if (player.isPlaying()) {
                player.pause();
            }
            player.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SM.unregisterListener(this);
    }
}
