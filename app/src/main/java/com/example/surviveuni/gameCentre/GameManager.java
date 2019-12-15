package com.example.surviveuni.gameCentre;

import android.content.Context;
import android.util.Log;

import com.example.surviveuni.data.GameState;
import com.example.surviveuni.data.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameManager {
    /**
     * variable representing the user's gameState
     */
    private static GameState gameState;

    /**
     * representing the current login user
     */
    private static User user;

    private UserManager userManager;

    private Context context;

    /**
     * file suffix for saving and reading gameState
     */
    private static final String SUFFIX = "-sav1.dat";

    /**
     * file name for saving and reading users
     */
    private static final String FILENAME = "users.txt";

    public GameManager(User user, Context context) {
        GameManager.user = user;
        this.context = context;
        this.userManager = UserManager.getInstance(context);
    }

    /**
     * @return the user
     */
    public static User getUser() {
        return user;
    }

    /**
     * initiate gameState
     */
    void newGame() {
        gameState = new GameState();
    }


    /**
     * load the gameState for user and load hashMap users
     */
    void loadGame() {

        String filename = user.getUsername() + SUFFIX;
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                gameState = (GameState) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("GameState", "FileNotFoundException");
            gameState = new GameState();

        } catch (IOException e) {
            Log.e("GameState", "IOException");
            gameState = new GameState();

        } catch (ClassNotFoundException e) {
            Log.e("GameState", "ClassNotFoundException");
            gameState = new GameState();
        }

    }

    /**
     * save gameState and user
     */
    void saveGame() {
        String filename = user.getUsername() + SUFFIX;
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStream.writeObject(gameState);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        // save user  so the score is saved
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStream.writeObject(userManager.getUsers());
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * @return the gameState of user
     */
    public static GameState getGameState() {
        return gameState;
    }


}
