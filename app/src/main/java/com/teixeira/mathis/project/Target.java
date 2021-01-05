package com.teixeira.mathis.project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Target the player needs to collide with in order to score points
 */

public class Target {
    private int x;
    private int y;
    private int size = 80;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    // Initialize the target with a random position
    public Target(){
        randomPos();
    }

    // Draw the target as a big white circle
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, size, paint);
    }

    // Detects collision with the player by checking if the player is within bounds of the target
    public boolean checkCollision(int playerX, int playerY){
        return playerX >= x-size && playerX <= x+size && playerY >= y-size && playerY <= y+size;
    }

    // Pick a new position for the target
    // Called when the player collides with the target
    public void randomPos(){
        x = ThreadLocalRandom.current().nextInt(size, screenWidth-size+1);
        y = ThreadLocalRandom.current().nextInt(size, screenHeight-size+1);
    }
}
