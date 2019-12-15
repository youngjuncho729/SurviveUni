package com.example.surviveuni.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.surviveuni.R;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.FeedbackActivity;


public class SleepFeedbackActivity extends FeedbackActivity implements SleepFeedbackView {

    /**
     * the presenter decides what to present on screen
     */
    private SleepFeedbackPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Intent intent = getIntent();
        String feedback = intent.getStringExtra(SleepAnswerActivity.EXTRA_MESSAGE);

        User user = (User) intent.getSerializableExtra("User");
        super.setUser(user);
        int touchedWolfNum = intent.getIntExtra("TouchedWolfNum", 0);

        presenter = new SleepFeedbackPresenter(this, new SleepScoreManager(this));
        presenter.setTouchedWolfNum(touchedWolfNum);
        presenter.setMessage();
        presenter.setFeedback(feedback);
        presenter.changeGameState(feedback);
        presenter.setStatsFeedback(feedback);
    }

    /**
     * set the feedback on the screen
     *
     * @param feedback the feedback on user's answer
     */
    @Override
    public void setTextView(String feedback) {
        TextView textView = findViewById(R.id.feedbackText);
        textView.setText(feedback);
    }

    /**
     * set the feedback on the changed score on the screen
     *
     * @param statsFeedback feedback on the changed scores
     */
    @Override
    public void setTextView2(String statsFeedback) {
        TextView textView2 = findViewById(R.id.statsText);
        textView2.setText(statsFeedback);
    }

    /**
     * set the message of number of wolves found on the screen
     *
     * @param message message about the number of wolves found
     */
    @Override
    public void setTextView3(String message) {
        TextView textView3 = findViewById(R.id.secondFeeedback);
        textView3.setText(message);
    }
}
