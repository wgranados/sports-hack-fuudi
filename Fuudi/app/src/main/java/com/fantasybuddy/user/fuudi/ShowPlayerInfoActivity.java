package com.fantasybuddy.user.fuudi;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowPlayerInfoActivity extends AppCompatActivity {

    String whatYouSent;
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_player_info);


        Intent startingIntent = getIntent();
        whatYouSent = startingIntent.getStringExtra("keyy");
        Toast.makeText(getApplicationContext(), whatYouSent, Toast.LENGTH_LONG).show();

        loadPlayerTrends();
    }

    public void loadPlayerTrends(){
        TextView playerInfo = (TextView) findViewById(R.id.twitter_stuff);
        EditText playerNameBox = (EditText) findViewById(R.id.playerNameBox);

        String playerName = whatYouSent;
        ArrayList<String> results = new ArrayList<String>();
        enableStrictMode();
        results = new TwitterUpdates().returnTweets(playerName);
        StrictMode.enableDefaults();
        for(int i = 0; i < results.size(); i++){
            String result = results.get(i);
            String handle = result.substring(0, result.indexOf(":")+1);
            String message =  result.substring(result.indexOf(":")+1, result.length()-1);
            tweets.add(new Tweet(handle, message));
        }
        for(int i = 0; i < tweets.size(); i++){
            Tweet tweetRN = tweets.get(i);
            playerInfo.setText(playerInfo.getText() + "\n" + tweetRN.handle + "\r" + tweetRN.message + "\n");
        }
    }

    public class Tweet{
        String handle;
        String message;
        public Tweet(String handle, String message){
            this.handle = handle;
            this.message = message;
        }
    }

    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


}
