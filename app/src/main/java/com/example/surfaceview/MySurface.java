package com.example.surfaceview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class MySurface extends SurfaceView implements SurfaceHolder.Callback {

    //переменные для рисования
    float x,y;//координаты изображения
    float tx,ty;// координаты касания
    float dx,dy; //смещение
    float koeff; //коэффициент скорости

    float wallx,wally;

    //перемены для картинки
    Bitmap image,wall,sprites;
    Resources res;
    Paint paint;

     boolean isFirst=true;
     float hs,ws; //размеры холста
    float hi,wi;//размеры изобр


    DrawThread drawThread;
    GameMap gameMap;

    boolean please=true;


    Rect wallRect,imageRect;
    ArrayList<Sprites> spritesArray=new ArrayList<Sprites>();

    public MySurface(Context context){
        super(context);
        getHolder().addCallback(this);
        x=100;
        y=100;
        koeff=20;
        res=getResources();
        image= BitmapFactory.decodeResource(res,R.drawable.jerry);
        //wall=BitmapFactory.decodeResource(res,R.drawable.wall);
        hi=image.getHeight();
        wi=image.getWidth();
        paint=new Paint();
        sprites=BitmapFactory.decodeResource(res,R.drawable.sprites);
        spritesArray.add(new Sprites(sprites,this,100,100));

    }
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            tx=event.getX();
            ty=event.getY();
            if(tx>x){
                spritesArray.get(0).setDirection(7);

            }
            if(tx<x){
                spritesArray.get(0).setDirection(5);

            }
            please=true;


            //calculate();
            //spritesArray.add(new Sprites(sprites,this,(int) tx,(int) ty));
            //Iterator<Sprites> interator=spritesArray.iterator();
            //while(interator.hasNext()){
               // Sprites sprites1;
               // sprites1=interator.next();
               // float speedX=(tx-sprites1.x)/(float)ws*40;
               // float speedY=(tx-sprites1.y)/(float)hs*40;
               //sprites1.setSpeed(speedX,speedY);
           //}
        }
        return true;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(isFirst){
            hs=canvas.getHeight();
            ws=canvas.getWidth();
            //Random random=new Random();
            //wallx=random.nextInt((int) ws-wall.getWidth()-100);
            //wallx=random.nextInt((int) hs-wall.getHeight()-100);
            //wallRect=new Rect((int) wallx-30,(int) wally-30,(int) wallx+wall.getWidth()+30,(int) wally+wall.getHeight()+30);
            gameMap=new GameMap(ws,hs,res);

            isFirst=false;
        }
        if(tx!=0&&please){
            calculate();
            x+=dx;
            spritesArray.get(0).setX((int) x);
        }
        if(x>tx-40&&x<tx+40&&please){
            if(ty>y){
                spritesArray.get(0).setDirection(4);
            }
            if(ty<y){
                spritesArray.get(0).setDirection(6);
            }
            y+=dy;
            spritesArray.get(0).setY((int) y);
        }
        if(x-30<tx&&x+30>tx&&y-30<ty&&y+30>ty){
            please=false;
        }
        if(!please){
            spritesArray.get(0).setDirection(0);
        }







        gameMap.draw(canvas);
        //canvas.drawBitmap(image,x,y,paint);
        //canvas.drawBitmap(wall,wallx,wally,paint);
        //if(tx!=0){
           // calculate();
       // }
        //imageRect=new Rect((int) x,(int) y,(int) (x+wi),(int) (y+hi));
        //x += dx;
        //y += dy;
        //if (imageRect.intersect(wallRect)){
           // dx=0;
            //dy=0;
        //}
        //
        //checkScreen();



        for (int i = 0; i < spritesArray.size(); i++) {
            spritesArray.get(i).draw(canvas);

        }
    }

    private void calculate(){
        double g=Math.sqrt((tx-x)*(tx-x)+(ty-y)*(ty-y));
        dx=(float)(koeff*2*(tx-x)/g);
        dy=(float)(koeff*(ty-y)/g);
    }
    private void checkScreen(){
        if(y+hi>=hs||y<=0){
            dy=-dy;
        }
        if(x+wi>=ws||x<=0){
            dx=-dx;
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread=new DrawThread(this,getHolder());
        drawThread.setRun(true);
        drawThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean stop=true;
        drawThread.setRun(false);
        while(stop){
            try {
                drawThread.join();
                stop=false;
                please=false;

            } catch (InterruptedException e) {
                continue;
            }
        }


    }
}
