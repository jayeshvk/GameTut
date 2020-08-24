package com.learning.gametut.model;

import android.graphics.Canvas;

import com.learning.gametut.Animation;


public abstract class MapObject {

    protected double xmap;
    protected double ymap;

    // Vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    // Collison box
    protected int cwidth;
    protected int cheight;


    //Animation attributes
    protected boolean facingLeft;
    protected Animation animation;
    protected int currentAction;


    //dimensions
    protected int width;
    protected int height;

    // movement
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;
    protected boolean idle;


    // movement attributres
    protected double moveSpeed;
    protected double maxSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double stopSpeed;
    protected double jumpStart;
    protected double gravity;
    protected double stopJumpSpeed;

    // Constructor
    MapObject() {

    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getCwidth() {
        return cwidth;
    }

    public int getCheight() {
        return cheight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setIdle(boolean b) {
        idle = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void setJumping(boolean b) {
        jumping = b;
    }


    public void draw(Canvas g) {

    }

    protected abstract void loadSpriteSheet();

}
