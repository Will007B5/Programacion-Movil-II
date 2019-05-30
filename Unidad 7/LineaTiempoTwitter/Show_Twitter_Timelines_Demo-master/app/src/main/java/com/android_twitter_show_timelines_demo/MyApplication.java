package com.android_twitter_show_timelines_demo;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("h835hmmGNYsKqFxEts6keGvfC", "kefJbnzbspZksOxMgHFUBChHssSS3205SLGW2R2nWMKndJzLzW"))//Claves de API
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
