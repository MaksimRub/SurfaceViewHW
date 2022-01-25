package com.example.surfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprites {
    MySurface mySurface;
    Bitmap image;//изображение со спрайтами
    int x,y;//координаты персонажа
    float dx,dy;//скорость персонажа
    int height,width;//высота и ширина кадра
    Paint paint;
    final int IMAGE_ROWS=8;
    final  int IMAGE_COLUMNS=10;
    int currentFrame=0;
    int direction=5;
    public Sprites(Bitmap image,MySurface mySurface,int x,int y){
        this.mySurface=mySurface;
        this.image=image;
        width=image.getWidth()/IMAGE_COLUMNS;
        height=image.getHeight()/IMAGE_ROWS;
        this.x=x;
        this.y=y;
    }
    public void controlRote(){
        //if(x<=0||x>=mySurface.getWidth()-image.getWidth()){
          // dx=-dx;

       //}
       // if(y<=0||y>=mySurface.getHeight()-image.getHeight()){
        //   dy=-dy;
      // }
       //x+=dx;
      //y+=dy;
        currentFrame=++currentFrame%IMAGE_COLUMNS;
    }
    public  void  setSpeed(float dx,float dy){
        this.dx=dx;
        this.dy=dy;
    }
    public  void draw(Canvas canvas){
        controlRote();
        Rect src=new Rect(currentFrame*width,direction*height,currentFrame*width+width,direction*height+height);
        Rect dst=new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(image,src,dst,paint);
    }

    public void setX(int x) {
        this.x = x;
        this.y = y;
    }
    public void setY(int y) {

        this.y = y;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
