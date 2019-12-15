package com.example.surviveuni.gameCentre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.surviveuni.R;
import com.example.surviveuni.data.User;

public class CustomizeActivity extends AppCompatActivity {
    /**
     * variable representing the current login user
     */
    private User user;

    /**
     * variable representing the gameManager
     */
    private GameManager gameManager;

    /**
     * start the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("USER");

        gameManager = new GameManager(user, this);
        setNewGameBtn();
        setLoadGameBtn();
        setSaveGameBtn();
        setSignOutBtn();
    }

    /**
     * Resume the activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (GameManager.getGameState() == null) {
            findViewById(R.id.CustomizeSaveBtn).setVisibility(View.GONE);
        } else {
            findViewById(R.id.CustomizeSaveBtn).setVisibility(View.VISIBLE);

        }
    }


    /**
     * set the NEW GAME button in customize.xml
     */
    private void setNewGameBtn() {
        findViewById(R.id.CustomizeNewGameBtn).setOnClickListener(v -> {
            gameManager.newGame();

            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("User", user);
            startActivity(i);

        });

    }

    /**
     * set LOAD GAME button in customize.xml
     */
    private void setLoadGameBtn() {
        findViewById(R.id.CustomizeLoadGameBtn).setOnClickListener(v -> {
            gameManager.loadGame();

            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("User", user);
            startActivity(i);

        });

    }

    /**
     * set SAVE GAME button in customize.xml
     */
    private void setSaveGameBtn() {
        findViewById(R.id.CustomizeSaveBtn).setOnClickListener(v -> {

            gameManager.saveGame();
            Toast.makeText(this, "Your game is successfully saved!", Toast.LENGTH_SHORT).show();

        });
    }

    /**
     * set SIGN OUT button in customize.xml for multi users to login
     */
    private void setSignOutBtn() {
        findViewById(R.id.CustomizeSignOutBtn).setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });
    }

}
