package com.teixeira.mathis.project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

/**
 * Class responsible of drawing the game and manage the game mechanics
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private Context context;

    private GameThread thread;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Player player = new Player(screenWidth/2, screenHeight/2);
    private Target target = new Target();
    private int score = 0;
    private Paint scorePaint = new Paint();

    private Rect pauseButtonRect = new Rect(10, 10, 80, 80);
    private Paint pauseButtonPaint = new Paint(Color.LTGRAY);

    public GameView(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(50);

        setOnTouchListener(this);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    // Create the surface we can draw onto
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    // Stop the thread when the surface is destroyed
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    // Update every object of the game
    // Used for the physics (moving and checking collision with target)
    public void update(){
        player.update();
        if(target.checkCollision(player.x, player.y)){
            score++;
            target.randomPos();
        }
    }

    // Draw on the canvas with updated positions
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.CYAN);
            target.draw(canvas);
            player.draw(canvas);
            canvas.drawText("Score : " + score, (float)screenWidth/2, 60, scorePaint);
            canvas.drawRect(pauseButtonRect, pauseButtonPaint);
        }
    }

    // Move the player in every directions
    // Functions called by GameActivity after getting the data from the sensor
    public void moveRight(){
        player.x += player.speed;
    }

    public void moveLeft(){
        player.x -= player.speed;
    }

    public void moveUp(){
        player.y += player.speed;
    }

    public void moveDown(){
        player.y -= player.speed;
    }

    // If the user touches the screen on the top left corner (ie. the black square on screen, which is the pause button) then executes the save function
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(pauseButtonRect.contains((int) event.getX(), (int) event.getY())){
            save();
        }
        return true;
    }

    // Send the current score in a bundle to the save activity
    public void save(){
        Intent intent = new Intent(context, Save_Activity.class);
        Bundle b = new Bundle();
        b.putInt("score", score);
        intent.putExtras(b);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
