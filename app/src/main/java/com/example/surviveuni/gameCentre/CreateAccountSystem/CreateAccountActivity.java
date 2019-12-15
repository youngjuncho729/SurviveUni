package com.example.surviveuni.gameCentre.CreateAccountSystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.surviveuni.R;
import com.example.surviveuni.gameCentre.MainActivity;
import com.example.surviveuni.gameCentre.UserManager;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountView {

    /**
     * The userManager manages users
     */
    private UserManager userManager;
    /**
     * The Create Account presenter
     */
    private CreateAccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        userManager = UserManager.getInstance(this);
        presenter = new CreateAccountPresenter(this, userManager);
    }

    /**
     * Navigate to MainActivity
     */
    public void setExitBtn(View view) {
        Intent exitCreate = new Intent(this, MainActivity.class);
        startActivity(exitCreate);
    }

    /**
     * create new account if the user's account and password inputs are valid
     */
    public void setCreateBtn(View view) {
        EditText accIn = findViewById(R.id.CreateAccountAccInput);
        EditText psIn = findViewById(R.id.CreateAccountPwInput);
        EditText nickname = findViewById(R.id.CreateAccountNickName);

        String accInput = accIn.getText().toString();
        String psInput = psIn.getText().toString();
        String nkInput = nickname.getText().toString();

        presenter.createUser(accInput, psInput, nkInput);
    }

    /**
     * Navigate to MainActivity
     */
    @Override
    public void navigateToMain() {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

    /**
     * Make a toast with text
     *
     * @param text text to be presented
     */
    @Override
    public void setToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
