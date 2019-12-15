package com.example.surviveuni.sleep;

import android.content.Context;
import android.util.Log;

import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.GameManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SleepScoreManager {

    /**
     * the game statistic
     */
    private static GameState gameState;

    /**
     * the user playing the game
     */
    private User user;

    /**
     * the environment
     */
    private Context context;

    /**
     * the name of data file
     */
    private static final String SUFFIX = "-sav2.dat";

    /**
     * the number of wolves got touched
     */
    private int touchedWolfNum;

    SleepScoreManager(Context context) {
        gameState = GameManager.getGameState();
        this.user = GameManager.getUser();
        this.context = context;
    }

    /**
     * getter method of touchedWolfNum
     *
     * @return number of wolves touched by user
     */
    int getTouchedWolfNum() {
        return touchedWolfNum;
    }

    /**
     * setter method of touchedWolfNum
     *
     * @param touchedWolfNum new touchedWolfNum to set
     */
    void setTouchedWolfNum(int touchedWolfNum) {
        this.touchedWolfNum = touchedWolfNum;
    }

    /**
     * Modify the static variable according to the game result
     *
     * @param feedback the feedback based on the user's answer
     */
    void changeGameState(String feedback) {
        if (feedback.equals("Correct!")) {
            gameState.changeSpirit(10);
            gameState.changeGPA(-5);
        } else {
            gameState.changeSpirit(-10);
            gameState.changeGPA(-5);
        }
    }

    /**
     * Return the corresponding message to show the user the changer of data
     *
     * @param feedback the feedback based on the user's answer
     */
    String checkFeedback(String feedback) {
        if (feedback.equals("Correct!")) {
            return ("Spirit: +10\nGPA:-5");
        } else {
            return ("Spirit: -10\nGPA:-5");
        }
    }

    /**
     * load the score data of this user
     */
    void loadGame() {

        String filename = user.getUsername() + SUFFIX;
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                touchedWolfNum = touchedWolfNum + (int) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("touchedWolfNum", "FileNotFoundException");
            touchedWolfNum = 0;

        } catch (IOException e) {
            Log.e("touchedWolfNum", "IOException");
            touchedWolfNum = 0;

        } catch (ClassNotFoundException e) {
            Log.e("touchedWolfNum", "ClassNotFoundException");
            touchedWolfNum = 0;
        }
    }

    /**
     * save the score data of this user
     */
    void saveGame() {
        String filename = user.getUsername() + SUFFIX;
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStream.writeObject(touchedWolfNum);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
