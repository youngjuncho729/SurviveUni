package com.example.surviveuni.gameCentre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.surviveuni.R;
import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.ScoreBoardSystem.ScoreBoardActivity;
import com.example.surviveuni.sleep.SleepMainActivity;
import com.example.surviveuni.social.SocialMain;
import com.example.surviveuni.study.StudyMenu;


import java.io.IOException;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

public class GameActivity extends AppCompatActivity {
    /**
     * the user plays the game
     */
    private User user;

    /**
     * Music player
     */
    private MediaPlayer mMediaPlayer;

    /**
     * Background music
     */
    private BackgroundSound mBackgroundSound = new BackgroundSound();

    /**
     * System audio manager
     */
    private AudioManager manager;

    /**
     * Background music switch
     */
    private ImageButton soundBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");

        GameState gs = GameManager.getGameState();
        Toast.makeText(this, "Your Three Values, GPA, Spirit and Happiness are "
                + gs.getGPA() + ", " + gs.getSpirit() + ", " + gs.getHappiness(), Toast.LENGTH_SHORT).show();

        TextView day = findViewById(R.id.textTitle);
        day.setText(" Survive for " + gs.getDayOfSurvival() + " days ");

        TextView gpa = findViewById(R.id.textGPA);
        gpa.setText("GPA:" + gs.getGPA());

        TextView happiness = findViewById(R.id.textHappiness);
        happiness.setText("Happiness:" + gs.getHappiness());

        TextView spirit = findViewById(R.id.textSpirit);
        spirit.setText("Spirit:" + gs.getSpirit());

        //Only display the sound switch when it's the first time user reaches main page or
        // when the music is off
        manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        soundBtn = findViewById(R.id.soundButton);
        if (manager.isMusicActive())
            soundBtn.setVisibility(View.INVISIBLE);
    }

    /**
     * When the page is reached, play music
     */
    protected void onResume() {
        super.onResume();

        mBackgroundSound.execute();

    }


    /**
     * Navigate to SleepMain Activity
     */
    public void StartSleep(View view) {
        Intent startGame = new Intent(this, SleepMainActivity.class);
        startGame.putExtra("User", user);

        startActivity(startGame);
    }

    /**
     * Navigate to SocialMain Activity
     */
    public void StartSocial(View view) {
        Intent startGame = new Intent(this, SocialMain.class);
        startGame.putExtra("User", user);

        startActivity(startGame);
    }

    /**
     * Navigate to StudyMenu Activity
     */
    public void StartStudy(View view) {
        Intent startGame = new Intent(this, StudyMenu.class);
        startGame.putExtra("User", user);

        startActivity(startGame);
    }

    /**
     * Navigate to Customize Activity
     */
    public void goBack(View view) {
        Intent i = new Intent(this, CustomizeActivity.class);
        i.addFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    /**
     * Set up score board button
     */
    public void setScoreBrdBtn(View view) {
        Intent i = new Intent(this, ScoreBoardActivity.class);
        i.putExtra("User", user);

        startActivity(i);
    }

    /**
     * Set up background sound
     */
    class BackgroundSound extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {


            mMediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.happy_dreams);


        }

        @Override
        protected Void doInBackground(Void... params) {

            mMediaPlayer.setLooping(true); // Set looping
            mMediaPlayer.setVolume(100, 100);

            // If system is not already playing music
            if (!manager.isMusicActive()) {
                mMediaPlayer.start();

            }
            return null;
        }

        protected void onCancelled(Void v) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }


    }

    /**
     * Set up the background music switch
     */
    public void soundSwitch(View view) {


        soundBtn = findViewById(R.id.soundButton);
        manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);


        if (manager.isMusicActive()) {

            mMediaPlayer.stop();
            soundBtn.setImageResource(android.R.drawable.ic_lock_silent_mode);
        } else {
            mMediaPlayer.reset();
            Uri uri = Uri.parse("android.resource://com.example.surviveuni/" + R.raw.happy_dreams);
            try {
                mMediaPlayer.setDataSource(this, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.setLooping(true); // Set looping
            mMediaPlayer.setVolume(100, 100);
            mMediaPlayer.start();

            soundBtn.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
        }


    }

}


