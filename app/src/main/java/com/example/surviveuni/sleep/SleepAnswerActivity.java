package com.example.surviveuni.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.surviveuni.R;
import com.example.surviveuni.data.User;

public class SleepAnswerActivity extends AppCompatActivity {

    /**
     * the feedback message
     */
    public static final String EXTRA_MESSAGE = "com.example.surviveuni.MESSAGE";

    /**
     * the number of sheep
     */
    private int sheepNum;

    /**
     * the number of wolves user touched
     */
    private int touchedWolfNum;

    /**
     * the user playing the game
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_answer);
        Intent intent = getIntent();
        sheepNum = intent.getIntExtra("SheepNum", 0);
        user = (User) intent.getSerializableExtra("User");
        touchedWolfNum = intent.getIntExtra("TouchedWolf", 0);
    }

    /**
     * Get the user's input and check the answer. Send the feedback to next activity and start the *
     * feedback activity.
     *
     * @param view view of the current game
     */
    public void submitAnswer(View view) {
        Intent intent = new Intent(this, SleepFeedbackActivity.class);
        EditText editText = findViewById(R.id.answerText);
        String answer = editText.getText().toString();
        String feedBack = checkAnswer(answer);
        intent.putExtra(EXTRA_MESSAGE, feedBack);
        intent.putExtra("User", user);
        intent.putExtra("TouchedWolfNum", touchedWolfNum);
        startActivity(intent);
        finish();
    }

    /**
     * Returns the feedback based on whether the user's answer is right or wrong.
     *
     * @param answer answer that user wrote
     * @return feedback according to the answer's correctness
     */
    private String checkAnswer(String answer) {
        try {
            int number = Integer.parseInt(answer);
            if (number == sheepNum) {
                return "Correct!";
            } else {
                return "Sorry!";
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Sorry! Your answer is not even a number.", Toast.LENGTH_SHORT).show();
            return "Sorry!";
        }
    }
}
