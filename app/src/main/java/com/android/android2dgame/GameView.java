package com.android.android2dgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private CharacterSprite characterSprite;
    public static int gapHeight = 500;
    public static int velocity = 10;
    PipeSprite pipe1;
    PipeSprite pipe2;
    PipeSprite pipe3;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        characterSprite = new CharacterSprite(getResizedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bird), 300, 240));
        //pipeSprites
        makeLevel();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        characterSprite.y = characterSprite.y - (characterSprite.yVelocity *10);
        return super.onTouchEvent(event);
    }

    public void update(){
        characterSprite.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();
//        System.out.println("make level " +  Resources.getSystem().getDisplayMetrics().heightPixels + " height "  + Resources.getSystem().getDisplayMetrics().widthPixels + "  width  ");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(canvas != null){
            canvas.drawRGB(0, 100, 205);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);
        }

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight){
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    void makeLevel(){
        Bitmap bmp;
        Bitmap bmp2;
        int y;
        int x;
        bmp = getResizedBitmap(BitmapFactory.decodeResource
                        (getResources(), R.drawable.pipe), 500,
                Resources.getSystem().getDisplayMetrics().heightPixels / 2);
        bmp2 = getResizedBitmap(BitmapFactory.decodeResource
                        (getResources(), R.drawable.pipe), 500,
                Resources.getSystem().getDisplayMetrics().heightPixels / 2);

        Matrix mat = new Matrix();
        mat.postRotate(180);
        bmp2 = Bitmap.createBitmap(bmp2,0,0,bmp2.getWidth(), bmp2.getHeight(),mat,true);
        pipe1 = new PipeSprite(bmp, bmp2, 500, 850);  //2000
        pipe2 = new PipeSprite(bmp, bmp2, 900, 700);  //3200
        pipe3 = new PipeSprite(bmp, bmp2, 1450, 800);   //4500
    }

}
