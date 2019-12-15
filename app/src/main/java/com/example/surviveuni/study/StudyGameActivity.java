package com.example.surviveuni.study;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surviveuni.R;
import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.GameActivity;
import com.example.surviveuni.gameCentre.GameManager;
import com.example.surviveuni.gameCentre.GameOverActivity;
import com.example.surviveuni.gameCentre.UserManager;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;


public class StudyGameActivity extends AppCompatActivity implements StudyGameView {
    private User user;
    private StudyGamePresenter studyGamePresenter;

    /**
     * Text feedback
     */
    private static String TIME_PREFIX = "Time: ";
    private static String SUCCESS_MSSG = "Success! GPA goes up!";
    private static String FAILURE_MSSG = "Failure... :(";
    private static String SAVE_MSSG = "Your Score Has Been Saved To ScoreBoard";
    private AlertDialog.Builder scoreSaved;

    /**
     * Elements on the page
     */
    private TextView result;
    private ImageButton button;

    /**
     * Tools to display time used
     */
    private TimerTask task2;
    private LocalTime startingTime;
    private int usedTime = 0;

    /**
     * Set up the page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameState gameState;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_study_game);
        gameState = GameManager.getGameState();

        startingTime = LocalTime.now();

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");
        String levelSelected = i.getStringExtra(StudyMenu.EXTRA_MESSAGE);

        findViewById(R.id.StudySaveBtn).setVisibility(View.GONE);
        studyGamePresenter = new StudyGamePresenter(gameState, this);
        setUpTime(levelSelected);
        studyGamePresenter.passActivity(this);
        studyGamePresenter.passUser(user);
        studyGamePresenter.passUserManager(UserManager.getInstance(this));
        scoreSaved = new AlertDialog.Builder(this)
                .setMessage(SAVE_MSSG)
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    /**
     * Set up time counter based on the level selected
     * Trigger random event after 2 seconds (opens the door/ receives a text message)
     */
    @Override
    public void setUpTime(String level) {
        Timer timer;
        timer = new Timer();

        task2 =
                new TimerTask() {
                    @Override
                    public void run() {
                        long time = Duration.between(startingTime, LocalTime.now()).toMillis();

                        usedTime = studyGamePresenter.convertTime(time, level);

                        if (usedTime == -1) {
                            runOnUiThread(
                                    () -> {
                                        // Stuff that updates the UI

                                        double i = Math.random();
                                        if (i > 0.5) setUpMessageButton();
                                        else setUpPersonButton();
                                    });
                        } else if (usedTime >= 0) {
                            runOnUiThread(
                                    () -> {
                                        // Stuff that updates the UI
                                        setTimeDisplay(usedTime);
                                    });
                        }
                    }
                };
        timer.schedule(task2, 0, 1000);
    }

    /**
     * Set up the phone image button
     */
    @Override
    public void setUpMessageButton() {

        button = new ImageButton(this);
        button = findViewById(R.id.MessageButton);
        button.setImageResource(R.drawable.message1);

        button.setOnClickListener(view -> {
            task2.cancel();
            button.setImageDrawable(null);
            setUpResult(true);
        });
    }


    /**
     * Set up the door image button
     */
    @Override
    public void setUpPersonButton() {

        button = new ImageButton(this);
        button = findViewById(R.id.theButton);
        button.setImageResource(R.drawable.person1);


        button.setOnClickListener(view -> {
            task2.cancel();
            button.setImageDrawable(null);
            setUpResult(true);

        });
    }

    /**
     * Set up the time counter display
     */
    private void setTimeDisplay(int usedTime) {
        TextView timeDisplay;
        timeDisplay = findViewById(R.id.studyTimeText);
        timeDisplay.setText(String.format(TIME_PREFIX + "%s", usedTime));
    }


    /**
     * Set up the exit button after the game is over
     */
    public void setExitBtn(View view) {
        Intent i;
        if (studyGamePresenter.checkExit()) {
            i = new Intent(this, GameOverActivity.class);
        } else {
            i = new Intent(this, GameActivity.class);
        }
        i.putExtra("User", user);
        startActivity(i);
    }

    /**
     * Set up save score button
     */
    public void setSaveScoreBtn(View view) {
        studyGamePresenter.saveScore();
    }


    /**
     * Set up the game result display
     */
    @Override
    public void setUpResult(boolean isSuccess) {
        result = findViewById(R.id.studyResult);
        runOnUiThread(() -> {
                    // Stuff that updates the UI
                    if (isSuccess && usedTime < 3) {
                        studyGamePresenter.setUpResult(true);
                        result.setText(SUCCESS_MSSG);
                    } else {
                        studyGamePresenter.setUpResult(false);
                        task2.cancel();
                        result.setText(FAILURE_MSSG);
                    }
                    findViewById(R.id.StudySaveBtn).setVisibility(View.VISIBLE);
                }
        );
    }

    /**
     * Set up the pop up message when saved the score
     */
    @Override
    public void setScoreSaveMessage() {
        scoreSaved.show();

    }
}
