package com.example.surviveuni.social;

import com.example.surviveuni.data.GameState;
import com.example.surviveuni.gameCentre.GameManager;

class SocialFeedbackPresenter implements SocialFeedbackView {
    private GameState gameState = GameManager.getGameState();

    private String setImage;

    /**
     * A getter for the setImage attribute.
     *
     * @return the string of setImage.
     */
    @Override
    public String getSetImage() {
        return setImage;
    }

    /**
     * Check the feedback gotten from SocialActivity and update the user's game state.
     *
     * @param feedback the string gotten from SocialActivity.
     */
    @Override
    public void checkFeedback(String feedback) {
        if (feedback.equals("Correct! Let's be friend!")) {

            gameState.changeGPA(-5);
            gameState.changeSpirit(-5);
            gameState.changeHappiness(10);
            setImage = "wow";

        } else if (feedback.equals("Sorry! Run out of playing times:( Maybe next time.")) {

            gameState.changeGPA(-5);
            gameState.changeSpirit(-5);
            gameState.changeHappiness(-5);
            setImage = "sorry";

        } else {

            gameState.changeGPA(-5);
            gameState.changeSpirit(-10);
            gameState.changeHappiness(-10);
            setImage = "angry";

        }
    }

}
