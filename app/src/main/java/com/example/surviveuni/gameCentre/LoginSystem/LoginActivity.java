package com.example.surviveuni.gameCentre.LoginSystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.surviveuni.R;
import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.CustomizeActivity;
import com.example.surviveuni.gameCentre.MainActivity;
import com.example.surviveuni.gameCentre.UserManager;

public class LoginActivity extends AppCompatActivity implements LoginView {
    /**
     * the name that user entered.
     */
    private EditText usernameInput;
    /**
     * the password that the user entered.
     */
    private EditText passwordInput;
    /**
     * the Login presenter
     */
    private LoginPresenter presenter;
    /**
     * the user input error Alert
     */
    private AlertDialog.Builder errorAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this, UserManager.getInstance(this));
        errorAlert = new AlertDialog.Builder(this)
                .setTitle("Wrong Input")
                .setMessage("username or password is wrong")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    /**
     * user sign in
     */
    @Override
    public void setLoginBtn(View view) {
        usernameInput = findViewById(R.id.LogInAccount);
        passwordInput = findViewById(R.id.LogInPw);
        checkAuthentication();
    }

    /**
     * Navigate to CustomizeActivity
     */
    @Override
    public void navigateToCustomize(User user) {
        Intent i = new Intent(this, CustomizeActivity.class);
        i.putExtra("USER", user);
        startActivity(i);
    }

    /**
     * Navigate to MainActivity
     */
    public void setExitBtn(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * Set the Alert Dialog when user input is wrong
     */
    @Override
    public void setInputError() {
        errorAlert.show();
    }

    /**
     * Check the authentication of user
     */
    private void checkAuthentication() {
        presenter.checkAuthentication(usernameInput.getText().toString(), passwordInput.getText().toString());
    }
}
