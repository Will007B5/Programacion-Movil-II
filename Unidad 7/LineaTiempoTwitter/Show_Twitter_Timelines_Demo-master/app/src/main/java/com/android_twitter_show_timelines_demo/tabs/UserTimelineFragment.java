package com.android_twitter_show_timelines_demo.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android_twitter_show_timelines_demo.helper.MyPreferenceManager;
import com.android_twitter_show_timelines_demo.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class UserTimelineFragment extends Fragment {

    private Context context;
    private RecyclerView userTimelineRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TweetTimelineRecyclerViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public UserTimelineFragment() {
    }

    public static UserTimelineFragment newInstance() {

        Bundle args = new Bundle();

        UserTimelineFragment fragment = new UserTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_timeline_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpSwipeRefreshLayout(view);
        setUpRecyclerView(view);
        loadUserTimeline();
    }

    private void setUpRecyclerView(@NonNull View view) {
        userTimelineRecyclerView = view.findViewById(R.id.user_timeline_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        userTimelineRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private void loadUserTimeline() {
        MyPreferenceManager myPreferenceManager = new MyPreferenceManager(context);

        UserTimeline userTimeline = new UserTimeline.Builder()
                .userId(myPreferenceManager.getUserId())
                .screenName(myPreferenceManager.getScreenName())
                .includeReplies(true)
                .includeRetweets(true)
                .maxItemsPerRequest(50)
                .build();

        adapter = new TweetTimelineRecyclerViewAdapter.Builder(context)
                .setTimeline(userTimeline)
                .setOnActionCallback(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                    }

                    @Override
                    public void failure(TwitterException exception) {
                    }
                })
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();


        userTimelineRecyclerView.setAdapter(adapter);
    }


    private void setUpSwipeRefreshLayout(View view) {


        swipeRefreshLayout = view.findViewById(R.id.user_swipe_refresh_layout);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (adapter == null)
                    return;


                swipeRefreshLayout.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(context, "Refresco exitoso", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(context, "Ha fallado algo al refrescar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
