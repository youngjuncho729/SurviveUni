package com.example.surviveuni.gameCentre.CreateAccountSystem;

import com.example.surviveuni.data.User;
import com.example.surviveuni.gameCentre.UserManager;

class CreateAccountPresenter {
    /**
     * The view of createAccount activity
     */
    private CreateAccountView createAccountView;
    /**
     * The manager manages user
     */
    private UserManager userManager;


    CreateAccountPresenter(CreateAccountView createAccountView, UserManager userManager) {
        this.createAccountView = createAccountView;
        this.userManager = userManager;
    }

    /**
     * Create the user if the input is valid
     *
     * @param accInput user's account input
     * @param psInput  user's password input
     * @param nkInput  user's nickname input
     */
    void createUser(String accInput, String psInput, String nkInput) {
        if (checkReasonable(accInput, psInput)) {
            User user = new User(accInput, psInput);
            user.setNickName(nkInput);
            userManager.getUsers().put(accInput, user);
            userManager.SaveToFile();
            createAccountView.setToast("Your new account has been created!");
            createAccountView.navigateToMain();
        }
    }

    /**
     * Check the validity of user's input
     *
     * @param username user's account input
     * @param password user's password input
     * @return true if valid, else false
     */
    private boolean checkReasonable(String username, String password) {
        if (username.equals("")) {
            createAccountView.setToast("Username Cannot Be Empty!");
            return false;
        } else if (password.equals("")) {
            createAccountView.setToast("Password Cannot Be Empty!");
            return false;
        } else if (password.length() <= 6 || username.length() <= 6) {
            createAccountView.setToast("Length of Password And Username must be greater than 6");
            return false;
        } else {
            for (String key : userManager.getUsers().keySet()) {
                if (key.equals(username)) {
                    createAccountView.setToast("Username has already been taken");
                    return false;
                }
            }
        }
        return true;
    }
}
