package com.example.surviveuni.gameCentre.ScoreBoardSystem;

import android.content.Context;

import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.FeedbackActivity;
import com.example.surviveuni.gameCentre.UserManager;
import com.example.surviveuni.study.StudyGamePresenter;

class ScoreBoardPresenter {
    /**
     * representing the maxScore for each loop
     */
    private int maxScore;

    /**
     * array that representing the five userd that should be displayed on scoreBoard
     */
    private static User[] onBoard = new User[5];

    /**
     * next user to put on ScoreBoard
     */
    private User toBoard;

    /**
     * userManager to get users
     */
    private UserManager userManager;


    private ScoreBoardView scoreBoardView;

    /**
     * representing the current user
     */
    private User us;

    // Constructor
    ScoreBoardPresenter(Context context, ScoreBoardView scoreBoardView) {
        this.maxScore = -1;
        this.toBoard = null;
        this.userManager = UserManager.getInstance(context);
        this.scoreBoardView = scoreBoardView;
    }

    /**
     * show the ranking on ScoreBoard.xml
     */
    void showRanking(){
        // if any of the three game is played and saved or the user never save score,
        // then re-sort the whole scoreBoard
        if(StudyGamePresenter.changed || FeedbackActivity.changed || us.getScore() == -1){
            setRanking();
        }
        for(int i = 0 ; i < 5; i++){
            if(ScoreBoardPresenter.onBoard[i] != null){
                // if the user didn't make a nickname when register, then show "ANONYMOUS"
                if (String.valueOf(ScoreBoardPresenter.onBoard[i].getNickname()).equals("")) {
                    scoreBoardView.showAnonymous(i);
                } else {
                    scoreBoardView.showNickName(i, ScoreBoardPresenter.onBoard[i]);
                }
                scoreBoardView.showTotalScore(i + 5, ScoreBoardPresenter.onBoard[i]);
            }
        }
        // change them back to false
        StudyGamePresenter.changed = false;
        FeedbackActivity.changed = false;
    }

    /**
     * if someone save a new score or a new user just create his/her account, then re-sort the
     * scoreBoard
     */
    private void setRanking() {
        User user;
        int spaceUsed;
        // initialize the array for the five user should be on ScoreBoard
        initializeOnBoard();
        spaceUsed = 0;
        for (int i = 0; i < 5; i++) {
            toBoard = null;
            maxScore = -1;
            for (String key : userManager.getUsers().keySet()) {
                // if the user is not in the array and he/she is not dead
                if (!contains(ScoreBoardPresenter.onBoard, key) && !userManager.getUsers().get(key).getIsDead()) {
                    user = userManager.getUsers().get(key);
                    // since the user is got by the key in the keySet, since the key is in the keySet
                    // so the user cannot be null here
                    if (user.getScore() > maxScore) {
                        System.out.println(user.getScore());
                        maxScore = user.getScore();
                        toBoard = user;
                    }
                }
            }
            // if the next user exists
            if (maxScore != -1) {
                ScoreBoardPresenter.onBoard[spaceUsed] = toBoard;
            }
            spaceUsed++;

        }
    }

    /**
     * determine whether a user with username b is in the array a
     * @param a the User array
     * @param b the username for which to look for
     * @return if the user is found or not
     */
    private boolean contains(User[] a, String b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) {
                return false;
            } else if (a[i].getUsername().equals(b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * accept the current login user for activity
     * @param user current login user
     */
    void passUser(User user){this.us = user;}

    /**
     * initialize the array representing the five users should be on ScoreBoard next turn
     */
    private void initializeOnBoard(){
        for(int i = 0; i < 5 ; i++)
        {
            onBoard[i] = null;
        }
    }
}
