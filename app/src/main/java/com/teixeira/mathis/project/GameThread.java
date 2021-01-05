package com.teixeira.mathis.project;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * A custom thread for the game
 */

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    // Main function of the thread
    @Override
    public void run() {
        while (running) {
            canvas = null;

            // Lock the canvas to avoid using it in multiple threads at the same time
            // Then draw and update the canvas
            // And finally unlock the canvas
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Run the thread or stop it
    public void setRunning(boolean isRunning){
        running = isRunning;
    }
}
