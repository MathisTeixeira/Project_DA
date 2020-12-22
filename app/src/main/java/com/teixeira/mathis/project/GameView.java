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
            canvas.drawRect(pauseButtonRect, pauseButtonPaint);
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

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(pauseButtonRect.contains((int) event.getX(), (int) event.getY())){
            save();
        }
        return true;
    }

    public void save(){
        Intent intent = new Intent(context, Save_Activity.class);
        Bundle b = new Bundle();
        b.putInt("score", score);
        intent.putExtras(b);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
