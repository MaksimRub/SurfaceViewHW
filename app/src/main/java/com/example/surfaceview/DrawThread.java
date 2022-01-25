package com.example.surfaceview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawThread extends Thread{
    MySurface mySurface;
    SurfaceHolder surfaceHolder;
    boolean isRun=false;
    long nowTime,prevTime,ellasedTime;

    @Override
    public void run() {
        Canvas canvas;
        while (isRun) {
            if (!surfaceHolder.getSurface().isValid())
                continue;

            canvas = null;
            nowTime=System.currentTimeMillis();
            ellasedTime=nowTime-prevTime;
            if(ellasedTime>30){
                prevTime=nowTime;
                canvas=surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder){
                    mySurface.draw(canvas);
                }
                if(canvas!=null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public boolean isRun() {
        return isRun;
    }

    public DrawThread(MySurface mySurface, SurfaceHolder surfaceHolder) {
        this.mySurface=mySurface;
        this.surfaceHolder=surfaceHolder;

        prevTime=System.currentTimeMillis();
    }
}
