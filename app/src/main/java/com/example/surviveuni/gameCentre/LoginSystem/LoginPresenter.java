package com.example.surviveuni.gameCentre.LoginSystem;

import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.UserManager;

import java.util.InputMismatchException;

class LoginPresenter {

    /**
     * the Log in View
     */
    private LoginView loginView;
    /**
     * the Manager manages users information
     */
    private UserManager userManager;

    LoginPresenter(LoginView loginView, UserManager userManager) {
        this.loginView = loginView;
        this.userManager = userManager;
    }

    /**
     * Check the authentication of user,
     * if valid, navigate to the CustomizeActivity
     */
    void checkAuthentication(String username, String password) {
        try {
            User user = userManager.authenticate(username, password);
            loginView.navigateToCustomize(user);

        } catch (InputMismatchException e) {
            loginView.setInputError();
        }
    }
}
