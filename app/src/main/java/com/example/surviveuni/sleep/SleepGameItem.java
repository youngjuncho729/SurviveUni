package com.example.surviveuni.sleep;

import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.List;

abstract class SleepGameItem {

    /**
     * This SleepGameItem's first coordinate.
     */
    private int x;

    /**
     * This SleepGameItem's second coordinate.
     */
    private int y;

    /**
     * the Resource
     */
    private Resources res;

    /**
     * Constructs a SleepGameItem at the specified cursor location (x, y).
     *
     * @param x_cor the x coordinate of the item.
     * @param y_cor the y coordinate of the item.
     */
    SleepGameItem(int x_cor, int y_cor, Resources res) {
        x = x_cor;
        y = y_cor;
        this.res = res;
    }

    /**
     * getter method of Resource
     *
     * @return the resource for image
     */
    Resources getRes() {
        return res;
    }

    /**
     * getter method of x coordinate of this item
     *
     * @return the x coordinate of this item
     */
    int getX() {
        return x;
    }

    /**
     * getter method of y coordinate of this item
     *
     * @return the y coordinate of this item
     */
    int getY() {
        return y;
    }

    /**
     * setter method of x coordinate of this item
     *
     * @param new_x the new x coordinate to set
     */
    void setX(int new_x) {
        x = new_x;
    }

    /**
     * setter method of y coordinate of this item
     *
     * @param new_y the new y coordinate to set
     */
    void setY(int new_y) {
        y = new_y;
    }

    /**
     * Causes this item to move, change its corresponding coordinates.
     *
     * @param ScreenHeight height of the game screen
     * @param ScreenWidth  width of the game screen
     */
    abstract void move(int ScreenHeight, int ScreenWidth);

    /**
     * Draws this item.
     *
     * @param canvas the graphics context in which to draw this item.
     */
    abstract void draw(Canvas canvas);

    /**
     * Item eats nearby other game items
     *
     * @param itemList list of game items
     * @return List of SleepGameItem got eaten, if none, return empty list
     */
    abstract List<SleepGameItem> eat(List<SleepGameItem> itemList);

    /**
     * item's reaction to the user's touch
     *
     * @param touchX x coordinate of user's touch
     * @param touchY y coordinate of user's touch
     */
    abstract void onTouchEvent(int touchX, int touchY);
}
