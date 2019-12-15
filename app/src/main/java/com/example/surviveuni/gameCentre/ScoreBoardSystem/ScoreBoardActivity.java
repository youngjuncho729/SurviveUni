package com.example.surviveuni.gameCentre.ScoreBoardSystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.surviveuni.R;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.GameActivity;

public class ScoreBoardActivity extends AppCompatActivity implements ScoreBoardView {
    /**
     * variable representing the current log in user
     */
    private User user;

    /**
     * an array collects all the output TextView
     */
    private TextView[] texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        // get current user here
        Intent i = getIntent();
        user = (User) i.getSerializableExtra("User");

        ScoreBoardPresenter sb = new ScoreBoardPresenter(this, this);
        // create texts and allocate with right value
        loadTextViews();

        //pass user to ScoreBoardPresenter
        sb.passUser(user);

        // show the ranking on Scoreboard
        sb.showRanking();
    }

    /**
     * a new intent  that go back to GameActivity.xml
     * @param view current xml view
     */
    public void setBoardBackBtn(View view) {
        Intent back = new Intent(this, GameActivity.class);
        back.putExtra("User", user);
        startActivity(back);
    }

    /**
     * show string
     * @param index the index in texts that show display something
     */
    @Override
    public void showAnonymous(int index) {
        texts[index].setText(getString(R.string.anonymous));
    }

    /**
     * show nickName on ScoreBoard xml
     * @param index the index in texts that show display something
     * @param us the user should appear
     */
    @Override
    public void showNickName(int index, User us) {
        texts[index].setText(String.valueOf(us.getNickname()));
    }

    /**
     * show the sum of three statics on scoreBoard xml
     * @param index the index in texts that show display something
     * @param us the user should appear
     */
    @Override
    public void showTotalScore(int index, User us) {
        texts[index].setText(String.valueOf(us.getScore()));
    }

    /**
     * helper function
     * create array texts and allocate with TextViews in ScoreBoard.xml
     */
    private void loadTextViews(){
        TextView name1 = findViewById(R.id.BoardName1);
        TextView name2 = findViewById(R.id.BoardName2);
        TextView name3 = findViewById(R.id.BoardName3);
        TextView name4 = findViewById(R.id.BoardName4);
        TextView name5 = findViewById(R.id.BoardName5);
        TextView score1 = findViewById(R.id.BoardScore1);
        TextView score2 = findViewById(R.id.BoardScore2);
        TextView score3 = findViewById(R.id.BoardScore3);
        TextView score4 = findViewById(R.id.BoardScore4);
        TextView score5 = findViewById(R.id.BoardScore5);
        texts = new TextView[]{name1, name2, name3, name4, name5, score1, score2, score3, score4, score5};
    }
}

