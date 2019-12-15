package com.example.surviveuni.sleep;


class SleepFeedbackPresenter {

    /**
     * the View of sleepFeedback
     */
    private SleepFeedbackView feedbackView;
    /**
     * the score manager manages score of sleep game
     */
    private SleepScoreManager scoreManager;

    SleepFeedbackPresenter(SleepFeedbackView feedbackView, SleepScoreManager scoreManager) {
        this.feedbackView = feedbackView;
        this.scoreManager = scoreManager;
    }

    /**
     * Add the number of wolves touched in this game into data
     *
     * @param touchedWolfNum number of wolves touched in this game
     */
    void setTouchedWolfNum(int touchedWolfNum) {
        scoreManager.setTouchedWolfNum(touchedWolfNum);
        scoreManager.loadGame();
        scoreManager.saveGame();
    }

    /**
     * set the feedback on the screen
     *
     * @param feedback the feedback on user's answer
     */
    void setFeedback(String feedback) {
        feedbackView.setTextView(feedback);
    }

    /**
     * Create the message and show on the screen
     */
    void setMessage() {
        String message = "You have already discovered "
                + scoreManager.getTouchedWolfNum()
                + " wolves!";
        feedbackView.setTextView3(message);
    }

    /**
     * update the score based on the correctness of user's answer
     *
     * @param feedback feedback on the correctness of user's answer
     */
    void changeGameState(String feedback) {
        if (feedback == null) {
            feedback = "Sorry!";
        }
        scoreManager.changeGameState(feedback);
    }

    /**
     * set the feedback on the changed score on the screen
     *
     * @param feedback feedback on the correctness of user's answer
     */
    void setStatsFeedback(String feedback) {
        if (feedback == null) {
            feedback = "Sorry!";
        }
        feedbackView.setTextView2(scoreManager.checkFeedback(feedback));
    }
}

