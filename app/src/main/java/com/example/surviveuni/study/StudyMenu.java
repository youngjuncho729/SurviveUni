package com.example.surviveuni.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.GameActivity;
import com.example.surviveuni.R;


public class StudyMenu extends AppCompatActivity {
    /**
     * The user
     */
    private User user;

    /**
     * Levels
     */
    private final String[] LEVELS = {"EASY", "NORMAL", "HARD"};

    /**
     * Place to store the selected level
     */
    public static final String EXTRA_MESSAGE = "com.example.surviveuni.study.StudyMenu.MESSAGE";

    /**
     * Spinner to choose levels
     */
    private Spinner spinner;

    /**
     * The selected level
     */
    private String levelChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_menu);
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
        if (user == null) System.out.println("44444444");

        spinner = findViewById(R.id.studyLevel);

        setLevelSpinner();


    }

    /**
     * Switch to game page
     */
    public void StartStudyGame(View view) {
        levelChoice = spinner.getSelectedItem().toString();

        Intent startGame = new Intent(this, StudyGameActivity.class);
        startGame.putExtra(EXTRA_MESSAGE, levelChoice);
        startGame.putExtra("User", user);
        startActivity(startGame);


    }

    /**
     * Exit study game
     */
    public void ExitStudyGame(View view) {
        Intent exitGame = new Intent(this, GameActivity.class);
        exitGame.putExtra("User", user);
        startActivity(exitGame);

    }

    /**
     * Set up the level spinner
     */
    private void setLevelSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, LEVELS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}

