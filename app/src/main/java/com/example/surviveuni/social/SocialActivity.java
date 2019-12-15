package com.example.surviveuni.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surviveuni.R;
import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.GameManager;

import java.util.Observer;

public class SocialActivity extends AppCompatActivity implements SocialGameView {
    private User user;
    public static final String EXTRA_MESSAGE = "com.example.surviveuni.social.SocialActivity.MESSAGE";
    private String feedBack = "";
    private boolean unexpectedInput = false;
    private Social social;

    /**
     * Set up the page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent level = getIntent();
        String levelSelected = level.getStringExtra(SocialMain.EXTRA_MESSAGE);
        user = (User) level.getSerializableExtra("User");
        social = new Social();
        GameState gs = GameManager.getGameState();
        Observer sc = social;
        gs.addObserver(sc);
        gs.socialNotify();
        social.setRemainingGuess(levelSelected);
        social.remainingGuess = social.getRemainingGuess();
        setContentView(R.layout.activity_social_answer);
        social.passSocialActivity(this);
    }

    /**
     * Set up the button for submit answer. Prompt message if there is unexpected input or there
     * are still remain chances.
     */
    public void submitAnswer(View view) {
        EditText editText = findViewById(R.id.answerText);
        String answer = editText.getText().toString();

        feedBack = social.checkAnswer(answer);

        if (social.remainingGuess != 1) {
            social.remainingGuess--;
            if (!unexpectedInput) {
                Toast.makeText(this, feedBack, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "unexpected input: out of range or not a number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Check whether the user won the game or have unexpected input or reach last chance to input
     * answer, if any of these happens, game is over. Redirect to feedback page.
     *
     * @param feedback the String got from Social, will be sent to SocialFeedback.
     */
    void checkGameOver(String feedback) {
        Intent intent = new Intent(this, SocialFeedbackActivity.class);
        intent.putExtra(EXTRA_MESSAGE, feedback);
        intent.putExtra("User", user);
        startActivity(intent);
        finish();
    }


    @Override
    public void setUnexpectedInput(boolean unexpectedInput) {
        this.unexpectedInput = unexpectedInput;
    }

    @Override
    public void setFailedMessage() {
        Toast.makeText(this, "Sorry! Your answer is not even a number.", Toast.LENGTH_SHORT).show();
    }

}
