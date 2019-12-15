package com.example.surviveuni.data;


import java.io.Serializable;

public class User implements Serializable {
    /**
     * this user's username
     */
    private String username;
    /**
     * this user's password
     */
    private String password;
    /**
     * this user's score
     */
    private int score = -1;
    /**
     * this user's nickname
     */
    private String nickname;

    /**
     * if the user is game over
     */
    private boolean dead = false;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
        this.dead = false;
    }

    /**
     * getter method of username
     *
     * @return this user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * check whether the entered password matches this user's password
     *
     * @param pw the password user entered
     * @return true if password matches user's password
     */
    public boolean checkPassword(String pw) {
        return this.password.equals(pw);
    }

    /**
     * update the score with new score
     *
     * @param new_score new score to set
     */
    public void updateScore(int new_score) {
        this.score = new_score;
    }

    /**
     * setter method of this user's nickname
     *
     * @param nkName new nickname to set
     */
    public void setNickName(String nkName) {
        this.nickname = nkName;
    }

    /**
     * getter method of this user's score
     *
     * @return this user's score
     */
    public int getScore() {
        return score;
    }

    /**
     * getter method of this user's nickname
     *
     * @return this user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the living condition of the user
     */
    public boolean getIsDead() {return dead;}

    /**
     * set the living condition of the user
     * @param bool new living condition of the user
     */
    public void setIsDead(boolean bool) {this.dead = bool;}

}
