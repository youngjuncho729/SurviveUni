package com.example.surviveuni.gameCentre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;


public abstract class FeedbackActivity extends AppCompatActivity {
    /**
     * variable representing the user's gameState
     */
    private GameState gameState;
    /**
     * the user playing the game
     */
    protected User user;
    /**
     * the manager manages users
     */
    private UserManager userManager;
    /**
     * the alert when score is saved
     */
    private AlertDialog.Builder scoreSaved;
    /**
     * whether the score is changed
     */
    public static boolean changed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = UserManager.getInstance(this);
        gameState = GameManager.getGameState();
        scoreSaved = new AlertDialog.Builder(this)
                .setMessage("Your Score Has Been Saved To ScoreBoard")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    /**
     * setter method for the user
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Navigate to the GameOverActivity if one of user's score is under 0,
     * else, update the dayOfSurvival and navigate to GameActivity
     */
    public void nextRound(View view) {
        Intent NextRound;
        if (gameState.checkGameover() == 1) {
            //re-order the scoreBoard
            changed = true;
            // set the user to be dead,since it is the user current login, so it cannot be null
            userManager.getUsers().get(user.getUsername()).setIsDead(true);
            NextRound = new Intent(this, GameOverActivity.class);
        } else {
            gameState.updateDay();
            NextRound = new Intent(this, GameActivity.class);
        }
        NextRound.putExtra("User", user);
        startActivity(NextRound);
        finish();
    }

    /**
     * update the user's score on the scoreboard
     */
    public void setSaveBtn(View view) {
        // since it is the user current login, so it cannot be null
        userManager.getUsers().get(user.getUsername()).updateScore(gameState.getGPA() + gameState.getHappiness() + gameState.getSpirit());
        UserManager.getInstance(this).SaveToFile(); // Save to file so no need to save again when sign out
        scoreSaved.show();
        changed = true;
        // if the user save score, so he/she is not dead
        userManager.getUsers().get(user.getUsername()).setIsDead(false);
    }
}
