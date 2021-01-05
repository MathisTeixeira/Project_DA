package com.teixeira.mathis.project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Player class
 */

public class Player {
    public int x;
    public int y;
    private int size = 20;
    public float speed = 3;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // The player is drawn on the canvas as a gray circle
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle(x, y, size, paint);
    }

    // Prevent the player from going out of the device's screen
    public void update(){
        if(x>screenWidth-size){
            x = screenWidth-size;
        }else if(x<size){
            x = size;
        }
        if(y>screenHeight-size){
            y = screenHeight-size;
        }else if(y<size){
            y = size;
        }
    }
}
