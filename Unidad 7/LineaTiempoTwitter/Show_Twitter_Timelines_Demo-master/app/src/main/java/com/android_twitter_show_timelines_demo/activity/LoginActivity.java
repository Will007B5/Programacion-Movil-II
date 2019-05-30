package com.android_twitter_show_timelines_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android_twitter_show_timelines_demo.helper.MyPreferenceManager;
import com.android_twitter_show_timelines_demo.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class LoginActivity extends AppCompatActivity {


    private MyPreferenceManager myPreferenceManager;

    private TwitterLoginButton loginButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myPreferenceManager = new MyPreferenceManager(this);

        //If para saber si el usuario esta logueado o no
        if (myPreferenceManager.getUserId() != 0) {
            //Si esta logueado, carga la actividad principal
            startMainActivity();
            return;
        }

        setContentView(R.layout.login_activity);

        loginButton = findViewById(R.id.login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession twitterSession = result.data;

                if (twitterSession != null) {

                    myPreferenceManager.saveUserId(twitterSession.getUserId());
                    myPreferenceManager.saveScreenName(twitterSession.getUserName());

                    startMainActivity();

                    Toast.makeText(LoginActivity.this, "Sesion Iniciada", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(LoginActivity.this, "Sesion no iniciada. Intente de nuevo", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this, "Sesion no iniciada. Intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * method to start Main Activity
     */
    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loginButton.onActivityResult(requestCode, resultCode, data);

    }
}
