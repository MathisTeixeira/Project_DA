package com.teixeira.mathis.project;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    private Player player = new Player(screenWidth/2, screenHeight/2);
    private Target target = new Target();
    private int score = 0;
    private Paint scorePaint = new Paint();
    
    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);

        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(50);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

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

    public void update(){
        player.update();
        if(target.checkCollision(player.x, player.y)){
            score++;
            target.randomPos();
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.CYAN);
            target.draw(canvas);
            player.draw(canvas);
            canvas.drawText("Score : " + score, (float)screenWidth/2, 60, scorePaint);
        }
    }

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
}
