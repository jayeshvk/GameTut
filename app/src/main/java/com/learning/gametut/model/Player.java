package com.learning.gametut.model;
//jhjh//
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.learning.gametut.Animation;

import java.util.ArrayList;
import java.util.Random;

public class Player extends MapObject {
    // Animation
    private ArrayList<Bitmap[]> sprites = new ArrayList<Bitmap[]>();

    private final int[] numActionFrames = {2, 6, 1, 1, 1}; // Idle has 1
    // frame,
    // Walking has 6
    // frames etc
    // Animation action numbers
    private static final int actions = 5;
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int FIRE = 4;
    private int gv_locX, gv_locY;

    private Animation animation;
    private Bitmap bitmap;

    private boolean touched;

    // Jump speed
    private double jumpSpeed = 4;
    private double currentJumpSpeed = jumpSpeed;
    // Fall speed
    private double maxFallSpeed = 50;
    private double currentFallSpeed = 0;

    public Player(Bitmap bitmap, float density, int locX, int locY) {

        this.bitmap = bitmap;

        width = (int) ((int) 21 * density);
        height = (int) ((int) 32 * density);
        cwidth = 21;
        cheight = 31;

/*        moveSpeed = 2.5;
        maxSpeed = 5;
        stopSpeed = 0.1;
        gravity = 0.25;*/
        moveSpeed = 0.3;
        maxSpeed = 1.2;
        maxFallSpeed = 6;
        stopSpeed = 0.1;
        jumpStart = -6.30;
        gravity = 0.25;

        gv_locX = locX;
        gv_locY = locY;

        loadSpriteSheet();
        facingLeft = true;
        left = false;
        right = false;
        idle = true;
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(500);

        //x = locX - (Math.round(Math.random() * 500));
        x = gv_locX;
        y = (int) locY * 20 / 100;

    }

    private void move() {
        /*if (left) {
            facingLeft = true;
            dx -= moveSpeed;
            if (dx < -maxSpeed)
                dx = -maxSpeed;
        }
        if (right) {
            facingLeft = false;
            dx += moveSpeed;
            if (dx > maxSpeed)
                dx = maxSpeed;
        }*/
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed)
                dx = -maxSpeed;
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed)
                dx = maxSpeed;
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0)
                    dx = 0;
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }
        /*if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
            jumping = false;
        }

        if (falling) {
            dy += gravity;
            if (dy > maxFallSpeed)
                dy = maxFallSpeed;
        } else {
            dy = 0;
        }*/
        if (jumping) {
            y = y - currentJumpSpeed;
            currentJumpSpeed -= 0.1;
            if (currentJumpSpeed <= 0) {
                currentJumpSpeed = jumpSpeed;
                jumping = false;
                falling = true;
            }
        }
        if (falling) {
            if (currentFallSpeed >= 4) {
                falling = false;
                if (!left && !right)
                    idle = true;
            }
            //System.out.printf("Current Fall Speed %.2f Y %.2f\n", currentFallSpeed, y);
            y = y + currentFallSpeed;

            if (currentFallSpeed < maxFallSpeed)
                currentFallSpeed += 0.1;
        }
/*        if (!falling) {
            currentFallSpeed = 0.1;
        }*/
        if (idle) {
            left = false;
            right = false;
        }
        if (left || right) {
            x = x + dx;
            if (x <= 0)
                x = gv_locX;
            if (x > gv_locX)
                x = 0;
        }
    }

    public void update() {
        move();
        setanimation();
    }

    private void setanimation() {

        if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(100);

            }
        }
        if (jumping) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                //animation.setDelay(1000);
            }
        }
        if (falling) {
            if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                //animation.setDelay(1000);
            }

        }
        if (idle) {
            if (currentAction != IDLE)
                currentAction = IDLE;
            animation.setFrames(sprites.get(IDLE));
            animation.setDelay(500);
        }

        System.out.println("* L " + left + " R " + right + " J " + jumping + " F " + falling + " I " + idle + " * " + currentAction);
        animation.update();

    }

    public void draw(Canvas canvas) {
        // draw player
        if (facingLeft) {
            canvas.drawBitmap(animation.getImage(), (int) x, (int) y, null);
        } else { // Reverse the picture
            Matrix rotateR = new Matrix();
            rotateR.setScale(-1, 1);
            Bitmap bmp = Bitmap.createBitmap(animation.getImage(), 0, 0, animation.getImage().getWidth(), animation.getImage().getHeight(), rotateR, true);
            canvas.drawBitmap(bmp, (int) (x), (int) y, null);
            //canvas.drawBitmap(bmp, (int) (x) - width / 2, (int) y - height / 2, null);

        }
    }

    protected void loadSpriteSheet() {
        System.out.println(bitmap.getWidth() + " " + bitmap.getHeight());
        // sprite sheets are loading by first storing the actions in an
        // array
        // then storing the whole array in another array of image called
        // sprites.
        // Storing array into an array
        // i<4 because we have 4 type of actions
        for (int i = 0; i < actions; i++) {
            Bitmap[] bi = new Bitmap[numActionFrames[i]];
            for (int j = 0; j < numActionFrames[i]; j++) {
                bi[j] = Bitmap.createBitmap(bitmap, j * width, i * height, width, height);
            }
            sprites.add(bi);
        }
        System.out.println("Player Sprite Sheet loaded");
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - width / 2) && eventX <= (x + width / 2)) {
            if (eventY >= (y - height / 2) && eventY <= (y + height / 2)) {
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }

    public boolean isTouched() {
        return touched;
    }

    public boolean isJumping() {
        return jumping || falling;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
