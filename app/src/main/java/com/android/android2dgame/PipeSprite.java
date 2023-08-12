package com.android.android2dgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PipeSprite {

    private Bitmap image;
    private Bitmap image2;
    public int xX, yY;
    private int xVelocity = 10;

    public PipeSprite (Bitmap bmp, Bitmap bmp2, int x, int y){
        image = bmp;
        image2 = bmp2;
        yY = y;
        xX = x;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xX, yY, null);
        canvas.drawBitmap(image2, xX, yY - GameView.gapHeight-image2.getHeight(), null);
    }

    public void update(){
//        xX -= GameView.velocity;
    }


}
