package com.example.surviveuni.gameCentre.LoginSystem;

import android.view.View;

import com.example.surviveuni.data.User;

interface LoginView {

    void setLoginBtn(View view);

    void navigateToCustomize(User user);

    void setInputError();

}
