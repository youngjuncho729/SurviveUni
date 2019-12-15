package com.example.surviveuni.sleep;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class SheepManager implements Serializable {

    /**
     * The width of game screen.
     */
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    /**
     * The height of game screen.
     */
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    /**
     * Number of Sheep to be created
     */
    private int sheepNum;

    /**
     * Number of Wolves tapped
     */
    private int touchedWolfNumber;

    /**
     * The list of sleep items created*
     */
    private List<SleepGameItem> itemList;

    /**
     * A new sheep manager with empty items.
     */
    SheepManager() {
        this.itemList = new ArrayList<>();
        this.touchedWolfNumber = 0;
    }

    /**
     * getter function for the sheepNum
     *
     * @return the number of sheep
     */
    int getSheepNum() {
        return sheepNum;
    }

    /**
     * gtter function for the touchedWolfNumber
     *
     * @return the number of Wolves tapped
     */
    int getTouchedWolfNumber() {
        return touchedWolfNumber;
    }

    /**
     * draw the items on the canvas
     *
     * @param canvas where the items are drawn
     */
    void draw(Canvas canvas) {
        for (SleepGameItem item : itemList) {
            item.draw(canvas);
        }
    }

    /**
     * updates all items
     */
    void update() {
        List<SleepGameItem> itemToRemove = new ArrayList<>();
        this.touchedWolfNumber = 0;
        for (SleepGameItem item : itemList) {
            item.move(screenHeight, screenWidth);
            itemToRemove.addAll(item.eat(itemList));
            if (item instanceof Wolf) {
                if (((Wolf) item).getTouched()) {
                    touchedWolfNumber++;
                }
            }
        }
        itemList.removeAll(itemToRemove);
        sheepNum -= itemToRemove.size();
    }

    /**
     * Create SheepNum numbers of sheep, and other items
     *
     * @param levelSelected the game level
     * @param res           the resource of image
     */
    void createItems(String levelSelected, Resources res) {
        setSheepNum(levelSelected);
        for (int i = 0; i < sheepNum + 3; i++) {
            int x = (int) (Math.random() * (screenWidth - 15)) + 10;
            int y = (int) (Math.random() * (screenHeight - 40)) + 10;
            if (i < sheepNum) itemList.add(new Sheep(x, y, res));
            else itemList.add(new Wolf(x, y, res));
        }
    }

    /**
     * set the number Sheep to be created according to the user selected level
     *
     * @param levelSelected the game level
     */
    private void setSheepNum(String levelSelected) {
        switch (levelSelected) {
            case "HARD":
                sheepNum = (int) (Math.random() * 5) + 13;
                break;
            case "NORMAL":
                sheepNum = (int) (Math.random() * 5) + 8;
                break;
            case "EASY":
                sheepNum = (int) (Math.random() * 5) + 3;
                break;
        }
    }

    /**
     * Cause the items reacts to the user's touch
     *
     * @param touchX x coordinate of user's touch
     * @param touchY y coordinate of user's touch
     */
    void onTouchEvent(int touchX, int touchY) {
        for (SleepGameItem item : itemList) {
            item.onTouchEvent(touchX, touchY);
        }
    }
}
