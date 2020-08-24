package com.learning.gametut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.learning.gametut.model.Droid;
import com.learning.gametut.model.Player;

import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    //Thread to hold the game loop
    private MainThread thread;

    //Entities
    private Droid droid;
    //private Player player;
    private ArrayList<Player> enemy;
    private Player player;
    private int i = 0;

    //Bitmaps Images
    private Bitmap btn;
    private Bitmap guy;
    private Bitmap plr;

    //Screen Density
    private Float density;

    private VButton button;

    float w = 60f;
    private boolean gv_action;

    private float x1, x2, y1, y2;
    static final int MIN_DISTANCE = 150;

    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        //Bitmap Images
        guy = BitmapFactory.decodeResource(getResources(), R.drawable.guy);
        plr = BitmapFactory.decodeResource(getResources(), R.drawable.charsheet);
        btn = BitmapFactory.decodeResource(getResources(), R.drawable.btn);

        //Get Screen Density
        density = getResources().getDisplayMetrics().density;

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;


        //Entities
        droid = new Droid(guy, 50, 50);
        //player = new Player(plr, density);
        //  enemy = new ArrayList<Player>();

        //Custom button detects touch in rectangle region
        button = new VButton(60, 60, btn);
        button.setPosition(500, 500);

        //Main thread to hold the Game Loop
        thread = new MainThread(getHolder(), this);
        System.out.println("***" + screenWidth + "hei" + screenHeight + "***" + "***" + getWidth() + "hei" + getHeight());
/*        for (int i = 0; i < 1; i++) {
            Player e1 = new Player(plr, density, screenWidth, screenHeight);
            enemy.add(e1);
        }*/
        player = new Player(plr, density, screenWidth, screenHeight);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "Surface Changed");
        if (thread.isAlive()) {
            Log.d(TAG, "Thread Alive");
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "Surface Created");
        Log.d(TAG, " Thread alive : " + Thread.currentThread().getState());

        if (!thread.isAlive()) {
            thread.setRunning(true);
            thread.start();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface destroyed");

        thread.setRunning(false);
        boolean retry = true;
        while (retry) {

            try {
                thread.join();
                retry = false;
                Log.d(TAG, "inside desroy loop : " + i++);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Log.d(TAG, "Threads closed safely");
        Log.d(TAG, " Thread alive : " + Thread.currentThread().getState());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        button.onTouchEvent(event);
        //  droid.handleActionDown((int) event.getX(), (int) event.getY());
/*        for (int i = 0; i < enemy.size(); i++)
            enemy.get(i).handleActionDown((int) event.getX(), (int) event.getY());*/

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                x1 = event.getX();
                System.out.println("Down" + x1);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaX = x2 - x1;
                float deltaY = y2 - y1;
                if (Math.abs(deltaX) > MIN_DISTANCE && deltaX < 0) {
                    System.out.println("Left");
                    player.setLeft(true);
                    player.setRight(false);
                    player.setIdle(false);
                    gv_action = true;
                } else if (Math.abs(deltaX) > MIN_DISTANCE && deltaX > 0) {
                    System.out.println("Right");
                    player.setLeft(false);
                    player.setRight(true);
                    player.setIdle(false);
                } else if (Math.abs(deltaY) > MIN_DISTANCE && deltaY < 0) {
                    {
                        //gv_action = false;
                        player.setIdle(false);
                        if (!player.isJumping())
                            player.setJumping(true);
                    }
                } else if (Math.abs(deltaY) > MIN_DISTANCE && deltaY > 0) {
                    if (!player.isJumping())
                        player.setIdle(true);
                }
                break;
        }
        /*        if (event.getAction() == MotionEvent.ACTION_DOWN) {


         *//*            if (event.getY() > getHeight() - 72) {
                thread.setRunning(false);
                ((Activity) getContext()).finish();

            } else {
                //Log.d(TAG,"Coords X: = " + event.getX() + ", Y = " + event.getY()+ " height " + getHeight() + "Width"+ getWidth());
            }*//*
         *//*            if (enemy.size() > 0) {
                for (int i = 0; i < enemy.size(); i++) {
                    Log.d("player", " pl : " + enemy.get(i).isTouched());
                    if (enemy.get(i).isTouched()) {
                        enemy.remove(i);
                        i--;
                    }
                }
            }*//*

        }*/

        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            if (droid.isTouched()) {
                droid.setX((int) event.getX());
                droid.setY((int) event.getY());
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            droid.setTouched(false);

        }
        return true;
    }

    public void update(double delta) {

        droid.update();
        player.update();

    }

    public void render(Canvas canvas) {

        droid.draw(canvas);
        button.draw(canvas);
        player.draw(canvas);

    }

}
