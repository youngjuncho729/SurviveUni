package com.example.surviveuni.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.GameActivity;
import com.example.surviveuni.R;

public class SleepMainActivity extends AppCompatActivity {
    /**
     * The list of choices for the spinner that allows the user to choose which Java feature to
     * demonstrate.
     */
    private final String[] levels = {"EASY", "NORMAL", "HARD"};
    /**
     * the spinner allows user to choice the game level
     */
    private Spinner spinner;
    /**
     * the level of the game
     */
    private String levelSelected;
    /**
     * the user playing the game
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_main);
        setLevelSpinner();

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
    }

    /**
     * Start the game when the user clicks the start button
     */
    public void StartSleepGame(View view) {
        levelSelected = spinner.getSelectedItem().toString();
        Intent startGame = new Intent(this, SleepGameActivity.class);
        startGame.putExtra("level", levelSelected);
        startGame.putExtra("User", user);
        startActivity(startGame);
    }

    /**
     * Return to the Game main activity when user clicks the exit button
     */
    public void ReturnGameMain(View view) {
        Intent ReturnGame = new Intent(this, GameActivity.class);
        ReturnGame.putExtra("User", user);
        startActivity(ReturnGame);
    }

    /**
     * Set up the gameLevel spinner
     */
    private void setLevelSpinner() {
        spinner = findViewById(R.id.gameLevel);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
