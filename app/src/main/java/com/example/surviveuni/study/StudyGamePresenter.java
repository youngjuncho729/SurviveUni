package com.example.surviveuni.study;

import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.UserManager;

public class StudyGamePresenter{
    /**
     * levels
     */
    private final String[] LEVELS = {"EASY", "NORMAL", "HARD"};

    /**
     * The state of the game
     */
    private GameState gameState;

    /**
     * User
     */
    private User user;

    /**
     * the Manager manages users information
     */
    private UserManager userManager;

    /**
     * Study game activity
     */
    private StudyGameActivity sga = new StudyGameActivity();

    /**
     * Study game view
     */
    private StudyGameView studyGameView;

    public static boolean changed = false;

    StudyGamePresenter(GameState gameState, StudyGameView studyGameView){
        this.gameState = gameState;
        this.userManager = UserManager.getInstance(sga);
        this.studyGameView = studyGameView;
    }

    /**
     * Display and check the used time based on the level selected. Start counting 1 second after
     * the event happens. Allow up to 3 seconds to react for easy level, 2 for normal, 1 for hard
     */
    int convertTime(long time, String level) {
        int sec = (int) ((time % 3600000 % 60000) / 1000);
        int deadline;

        if (level.equals(LEVELS[0]))
            deadline = 6;
        else if (level.equals(LEVELS[1]))
            deadline = 5;
        else
            deadline = 4;
        if (sec == deadline) {

            studyGameView.setUpResult(false);
        }

        return sec - 3;
    }

    /**
     * Check if the game is over
     */
    boolean checkExit() {
        if (gameState.checkGameover() == 1) {
            // re-order the scoreBoard
            changed = true;
            // set the user to be dead,since it is the user current login, so it cannot be null
            userManager.getUsers().get(user.getUsername()).setIsDead(true);
            return true;
        } else {
            gameState.updateDay();
            return false;
        }
    }

    /**
     * Save the user's current score
     */
    void saveScore() {
        // since variable user is the user current login, so it cannot be null
        userManager.getUsers().get(user.getUsername()).updateScore(gameState.getGPA() + gameState.getHappiness() + gameState.getSpirit());
        UserManager.getInstance(sga).SaveToFile(); // Save to file so no need to save again when sign out
        studyGameView.setScoreSaveMessage();
        changed = true;
        // if the user save score, he/she must be alive
        userManager.getUsers().get(user.getUsername()).setIsDead(false);
    }

    /**
     * Change user scores accordingly
     */
    public void setUpResult(boolean isSuccess) {
        gameState.changeHappiness(-5);
        gameState.changeSpirit(-5);
        if(isSuccess){gameState.changeGPA(5);}
        else{gameState.changeGPA(-5);}

    }


    /**
     * Pass and set study game activity
     */
    void passActivity(StudyGameActivity sga){this.sga = sga;}

    /**
     * Pass and set user
     */
    void passUser(User user){this.user = user;}

    /**
     * Pass and set user manager
     */
    void passUserManager(UserManager userManager){this.userManager = userManager;}
}
