package com.example.surviveuni.sleep;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.surviveuni.R;

import java.util.ArrayList;
import java.util.List;

class Wolf extends SleepGameItem {
    /**
     * This Wolf's width.
     */
    private int wolfWidth;

    /**
     * This Wolf's height.
     */
    private int wolfHeight;

    /**
     * Bitmap of Wolf
     */
    private Bitmap wolfLeft, wolfRight, appearance;

    /**
     * Indicates whether this Wolf is moving right.
     */
    private boolean goingRight;

    /**
     * Indicates whether this Wolf is touched.
     */
    private boolean touched;

    /**
     * Use for random movement up or down, turn around.
     */
    private double d;

    /**
     * Constructs a new Wolf at the specified cursor location (x, y).
     *
     * @param x   the x coordinate of the Wolf.
     * @param y   the y coordinate of the Wolf.
     * @param res the resource of bitmap image.
     */
    Wolf(int x, int y, Resources res) {
        super(x, y, res);
        touched = false;
        goingRight = true;
        wolfLeft = BitmapFactory.decodeResource(res, R.drawable.sheep_left);
        wolfRight = BitmapFactory.decodeResource(res, R.drawable.sheep_right);
        appearance = wolfRight;
        wolfWidth = wolfLeft.getWidth();
        wolfHeight = wolfRight.getHeight();
    }

    /**
     * getter method of getTouched
     *
     * @return whether this wolf is touched by user
     */
    boolean getTouched() {
        return touched;
    }

    /**
     * draw the wolf on the canvas
     *
     * @param canvas the graphics context in which to draw this item.
     */
    @Override
    void draw(Canvas canvas) {
        canvas.drawBitmap(appearance, getX(), getY(), null);
    }

    /**
     * Causes this wolf to move, change its corresponding coordinates.
     *
     * @param ScreenHeight height of the game screen
     * @param ScreenWidth  width of the game screen
     */
    @Override
    void move(int ScreenHeight, int ScreenWidth) {
        turnAround();
        moveRightLeft(ScreenWidth);
        moveUpDown(ScreenHeight);
    }

    /**
     * Wolf eats nearby other game items
     *
     * @param itemList list of sleep game items
     * @return List of SleepGameItem got eaten, if none, return empty list
     */
    @Override
    List<SleepGameItem> eat(List<SleepGameItem> itemList) {
        List<SleepGameItem> eatenItem = new ArrayList<>();
        for (SleepGameItem item : itemList) {
            if (!(item instanceof Wolf)) {
                // check for the distance between wolf and other items
                double distance =
                        Math.hypot(Math.abs(item.getX() - this.getX()), Math.abs(item.getY() - this.getY()));
                if (distance <= 50) {
                    eatenItem.add(item);
                    this.setX(item.getX());
                    this.setY(item.getY());
                }
            }
        }
        return eatenItem;
    }

    /**
     * Wolf change the appearance to wolf image when touched by user
     *
     * @param touchX x coordinate of user's touch
     * @param touchY y coordinate of user's touch
     */
    @Override
    void onTouchEvent(int touchX, int touchY) {
        if (!touched) {
            if (getX() <= touchX && touchX <= (getX() + wolfWidth)) {
                if (getY() <= touchY && touchY <= (getY() + wolfHeight)) {
                    touched = true;
                    wolfLeft =
                            getResizedBitmap(
                                    BitmapFactory.decodeResource(getRes(), R.drawable.wolf_left), 200, 200);
                    wolfRight =
                            getResizedBitmap(
                                    BitmapFactory.decodeResource(getRes(), R.drawable.wolf_right), 200, 200);
                    changeAppearance();
                }
            }
        }
    }

    /**
     * Change the appearance when the wolf turns around
     */
    private void changeAppearance() {
        if (goingRight) appearance = wolfLeft;
        else appearance = wolfRight;
        goingRight = !goingRight;
    }

    /**
     * resize the bitmap
     *
     * @param image        bitmap image to be resized
     * @param bitmapWidth  width of image to be resized
     * @param bitmapHeight height of image to be resized
     * @return resized bitmap
     */
    private Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }

    /**
     * Turns this sheep around, causing it to reverse direction.
     */
    private void turnAround() {
        d = Math.random();
        if (d < 0.1) {
            changeAppearance();
        }
    }

    /**
     * Move Height / 40 steps to the right or left in the direction I'm going. turn around if bump
     * into a wall.
     *
     * @param Width width of the game screen
     */
    private void moveRightLeft(int Width) {
        if (goingRight) {
            if (getX() + wolfWidth >= Width) turnAround();
            else setX(getX() + Width / 40);
        } else {
            if (getX() <= 0) turnAround();
            else setX(getX() - Width / 40);
        }
    }

    /**
     * Figure out whether to move up or down Height / 40 steps, or neither.
     *
     * @param Height height of the game screen
     */
    private void moveUpDown(int Height) {
        d = Math.random();
        if (d < 0.1 && getY() + wolfHeight <= Height - Height / 40) setY(getY() + Height / 40);
        else if (d > 0.9 && getY() >= 15) setY(getY() - Height / 40);
    }
}
