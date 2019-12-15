package com.example.surviveuni.social;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

class Social implements Observer {
    private SocialActivity sa;
    private boolean gameWon = false;
    private int correctAnswer = generate_expect();
    int remainingGuess;
    private boolean unexpectedInput = false;
    private String feedBack;
    private boolean triggered = false;

    /**
     * First check whether happiness lower or equal to 10(by Observable pattern) if so directly
     * correct. Then game reach the last time, if so return the final message. Also if unexpected
     * input occur the game directly over. Else condition, return the message to be prompt.
     *
     * @param answer the String got from SocialActivity by user input.
     */
    String checkAnswer(String answer) {
        String feedback;
        if (triggered) {
            feedback = feedBack;
            gameWon = true;
        } else {
            try {
                int number = Integer.parseInt(answer);

                if (number > 5 || number < 1) {
                    feedback = "You are not here to be friend with me!";
                    sa.setUnexpectedInput(true);
                    unexpectedInput = true;
                } else if (remainingGuess == 1) {
                    if (number == correctAnswer) {
                        feedback = "Correct! Let's be friend!";
                    } else {
                        feedback = "Sorry! Run out of playing times:( Maybe next time.";
                    }
                } else {
                    if (number == correctAnswer) {
                        gameWon = true;
                        feedback = "Correct! Let's be friend!";
                    } else if (number > correctAnswer) {
                        feedback = "It's too high, try another time.";
                    } else {
                        feedback = "It's too low, try another time.";
                    }
                }
            } catch (NumberFormatException e) {
                sa.setFailedMessage();
                sa.setUnexpectedInput(true);
                unexpectedInput = true;
                feedback = "You are not here to be friend with me!";
            }
        }
        checkGameOver(feedback, remainingGuess == 1, unexpectedInput);
        return feedback;
    }

    /**
     * Generate the answer for the game.
     *
     * @return the number generated.
     */
    private int generate_expect() {
        Random r = new Random();
        return r.nextInt(5) + 1;
    }

    void passSocialActivity(SocialActivity sa) {
        this.sa = sa;
    }

    /**
     * Check whether the user won the game or have unexpected input or reach last chance to input
     * answer, if any of these happens, game is over. Redirect to feedback page by using method in
     * SocialActivity.
     *
     * @param feedback      the string returned from checkAnswer.
     * @param limitStatus   the boolean showing if the game last chance is reached.
     * @param unExpectInput the boolean showing if the game got unexpected input.
     */
    private void checkGameOver(String feedback, boolean limitStatus, boolean unExpectInput) {
        if (gameWon || limitStatus || unExpectInput) {
            sa.checkGameOver(feedback);
        }
    }

    /**
     * Check which level did user choose at SocialMain, give corresponding chance to play the game.
     *
     * @param level the String get from SocialMain by user's choice.
     */
    void setRemainingGuess(String level) {
        switch (level) {
            case "HARD":
                remainingGuess = 1;
                break;
            case "NORMAL":
                remainingGuess = 2;
                break;
            case "EASY":
                remainingGuess = 3;
                break;
        }
    }

    int getRemainingGuess() {
        return remainingGuess;
    }

    @Override
    public void update(Observable observable, Object o) {
        feedBack = "Correct! Let's be friend!";
        triggered = true;
    }
}