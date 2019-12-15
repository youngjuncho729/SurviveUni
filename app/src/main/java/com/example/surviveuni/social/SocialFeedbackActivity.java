package com.example.surviveuni.social;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surviveuni.R;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.FeedbackActivity;


public class SocialFeedbackActivity extends FeedbackActivity {
    private SocialFeedbackPresenter presenter;


    /**
     * Set up the page.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_feedback);
        presenter = new SocialFeedbackPresenter();
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("User");
        String feedback = intent.getStringExtra(SocialActivity.EXTRA_MESSAGE);

        presenter.checkFeedback(feedback);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.feedbackText);
        textView.setText(feedback);

        String statsFeedback = checkFeedback();
        TextView textView2 = findViewById(R.id.statsText);
        textView2.setText(statsFeedback);
    }

    /**
     * Check the feedback gotten from SocialActivity and give the user feedback accordingly.
     *
     * @return the feedback for the user.
     */
    private String checkFeedback() {
        ImageView iv = findViewById(R.id.imageView1);
        if (presenter.getSetImage().equals("wow")) {
            iv.setImageResource(R.drawable.wow);
            return ("Happiness:+10\nGPA:-5\nSpirit:-5");
        } else if (presenter.getSetImage().equals("sorry")) {
            iv.setImageResource(R.drawable.sorry);
            return ("Happiness:-5\nGPA:-5\nSpirit:-5");
        } else {
            iv.setImageResource(R.drawable.angry);
            return ("Happiness:-10\nGPA:-5\nSpirit:-10");
        }
    }
}
