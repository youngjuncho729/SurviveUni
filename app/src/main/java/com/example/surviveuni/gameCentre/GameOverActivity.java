package com.example.surviveuni.gameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surviveuni.R;
import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;


public class GameOverActivity extends AppCompatActivity {
    private GameState gameState;
    private String reason;
    private String record;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameState = GameManager.getGameState();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        record = String.valueOf(gameState.getDayOfSurvival());
        reason = "Sorry\n you failed to survive university\nyour record is " + record + " days";
        TextView textView = findViewById(R.id.reasonText);
        textView.setText(reason);
        reset_data();

        //get user here
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
    }

    /**
     * reset static variables to 50
     */
    private void reset_data() {
        gameState.setSpirit(20);
        gameState.setGPA(20);
        gameState.setHappiness(20);
        gameState.setDayOfSurvival(0);
    }

    /**
     * Navigate to GameActivity
     */
    public void NewGame(View view) {
        Intent newgame = new Intent(this, GameActivity.class);
        newgame.putExtra("User", user);
        startActivity(newgame);
    }
}
