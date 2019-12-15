package com.example.surviveuni.gameCentre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.surviveuni.R;
import com.example.surviveuni.gameCentre.CreateAccountSystem.CreateAccountActivity;
import com.example.surviveuni.gameCentre.LoginSystem.LoginActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Navigate to sign up activity
     */
    public void LogIn(View view) {
        Intent logIn = new Intent(this, LoginActivity.class);
        startActivity(logIn);
    }

    /**
     * Navigate to create account activity
     */
    public void createAccount(View view) {
        Intent createAcc = new Intent(this, CreateAccountActivity.class);
        startActivity(createAcc);

    }
}
