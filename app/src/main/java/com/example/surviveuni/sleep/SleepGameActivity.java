package com.example.surviveuni.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.example.surviveuni.data.User;

public class SleepGameActivity extends AppCompatActivity {

    /**
     * the sheep Manger
     */
    private SheepManager sheepManager;

    /**
     * Where the sheep are drawn.
     */
    private SheepView sheepView;

    /**
     * the user playing the game
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent level = getIntent();
        String levelSelected = level.getStringExtra("level");
        user = (User) level.getSerializableExtra("User");
        sheepManager = new SheepManager();
        sheepView = new SheepView(this, sheepManager, levelSelected);
        setContentView(sheepView);

        final Handler handler = new Handler();
        handler.postDelayed(
                () -> {
                    Intent intent = new Intent(SleepGameActivity.this, SleepAnswerActivity.class);
                    intent.putExtra("SheepNum", sheepManager.getSheepNum());
                    intent.putExtra("User", user);
                    intent.putExtra("TouchedWolf", sheepManager.getTouchedWolfNumber());
                    SleepGameActivity.this.startActivity(intent);
                    SleepGameActivity.this.finish();
                },
                10000);
    }
}
