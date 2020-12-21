package com.teixeira.mathis.project;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.ThreadLocalRandom;

public class Target {
    private int x;
    private int y;
    private int size = 80;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Target(){
        randomPos();
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, size, paint);
    }

    public boolean checkCollision(int playerX, int playerY){
        return playerX >= x-size && playerX <= x+size && playerY >= y-size && playerY <= y+size;
    }

    public void randomPos(){
        x = ThreadLocalRandom.current().nextInt(size, screenWidth-size+1);
        y = ThreadLocalRandom.current().nextInt(size, screenHeight-size+1);
    }
}
