package com.teixeira.mathis.project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {
    private int x;
    private int y;
    private int size = 20;
    private float velocity = 0;
    private float speed = 10;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle(x, y, size, paint);
    }

    public void update(){
        x += speed;
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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
