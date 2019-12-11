package io.appmaven.ping;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private double averageFPS;

    private final SurfaceHolder surfaceHolder;
    private GameView gameView;

    public static Canvas canvas;

    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;

        long targetTime = 1000 / Constants.TARGET_FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();

                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
                Log.e("MainThread", e.getLocalizedMessage());
            }

            finally {
                if(canvas!=null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if(waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (Exception e) {
                Log.e("MainThread: ", e.getLocalizedMessage());
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == Constants.TARGET_FPS)        {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                // System.out.println(averageFPS);
            }
        }
    }
}
