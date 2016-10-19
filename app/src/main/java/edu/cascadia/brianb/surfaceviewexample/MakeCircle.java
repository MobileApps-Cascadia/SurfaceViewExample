package edu.cascadia.brianb.surfaceviewexample;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by brianb on 10/16/16.
 */

public class MakeCircle extends Thread {
    private SurfaceHolder surfaceHolder; // for manipulating canvas
    private boolean threadIsRunning = true; // running by default
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Random random = new Random();
    Canvas canvas = null;

    // initializes the surface holder
    public MakeCircle(SurfaceHolder holder) {
        surfaceHolder = holder;
        setName("CircleThread");
    }

    // changes running state
    public void setRunning(boolean running) {
        threadIsRunning = running;
    }

    @Override
    public void run() {
        while(threadIsRunning){
            try {
                canvas = surfaceHolder.lockCanvas();
                //... actual drawing on canvas
                synchronized (surfaceHolder) {
                    if(canvas!=null) {
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(5);

                        int w = canvas.getWidth();
                        int h = canvas.getHeight();
                        float x = (float)random.nextInt(w - 1);
                        float y = (float)random.nextInt(h - 1);
                        float radius = (float)random.nextInt(200);
                        int r = random.nextInt(255);
                        int g = random.nextInt(255);
                        int b = random.nextInt(255);
                        paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);

                        // Draw using the Canvas
                        canvas.drawCircle(x, y, radius, paint);
                        Thread.sleep(200);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(canvas!=null)
                {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
