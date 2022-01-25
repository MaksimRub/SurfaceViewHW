package com.example.surfaceview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.surfaceview.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class GameMap {
    int sizeTexture = 32;
    int mapArray[][];
    Bitmap textures[];
    String str;
    String btr = "";
    int l=0;

    public GameMap(float width, float height, Resources resources) {
        Random random = new Random();
        //mapArray=new int[(int) (height/sizeTexture)][(int) (width/sizeTexture)];
        try {
            InputStream is = resources.openRawResource(R.raw.map);
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            mapArray = new int [59][31];
            while ((str = br.readLine())!=null) {
                btr=btr+str;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


            for (int i = 0; i < mapArray.length; i++) {
                for (int j = 0; j < mapArray[i].length; j++) {
                    //if(j>mapArray[i].length/2-5&& j<mapArray[i].length/2+5){
                    // mapArray[i][j]=0;
                    //}else{
                    //  mapArray[i][j]=random.nextInt(3)+1;

                    // }
                    Character s=btr.charAt(l);
                    String b=s.toString();
                    mapArray[i][j]=Integer.parseInt(b);
                    l++;


                }

            }



    textures=new Bitmap[4];
    textures[0]=BitmapFactory.decodeResource(resources,R.drawable.grass);
    textures[1]=BitmapFactory.decodeResource(resources,R.drawable.water);
    textures[2]=BitmapFactory.decodeResource(resources,R.drawable.road);
    textures[3]=BitmapFactory.decodeResource(resources,R.drawable.bridge);


}
    public  void draw(Canvas canvas){
        float x=0,y=0;
        Paint paint=new Paint();
        for (int i = 0; i < mapArray.length; i++) {
            for (int j = 0; j < mapArray[i].length; j++) {
                canvas.drawBitmap(textures[mapArray[i][j]],x,y,paint);
                x+=sizeTexture;
            }
            y+=sizeTexture;
            x=0;

        }
        //changeMap();
    }
    private void changeMap(){
        for (int i =  mapArray.length-2; i >=0; i--) {
            for (int j = 0; j < mapArray[i].length; j++) {
                mapArray[i+1][j]=mapArray[i][j];

            }

        }
        Random random=new Random();
        for (int j = 0; j < mapArray[0].length; j++) {
            if(j>mapArray[0].length/2-5&& j<mapArray[0].length/2+5){
                mapArray[0][j]=0;
            }else{
                mapArray[0][j]=random.nextInt(3)+1;

            }

        }
    }

}
