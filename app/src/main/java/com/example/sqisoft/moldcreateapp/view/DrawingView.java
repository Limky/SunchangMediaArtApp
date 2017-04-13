package com.example.sqisoft.moldcreateapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.sqisoft.moldcreateapp.R;
import com.example.sqisoft.moldcreateapp.manager.ColorManager;
import com.example.sqisoft.moldcreateapp.manager.DataManager;

/**
 * Created by SQISOFT on 2017-04-06.
 */

public class DrawingView extends View {
    //drawing path
    private Path drawPath;
    //drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;
    //circle bitmap
    private Bitmap circleBitmap;

    private Bitmap mBackgroundBitmap;

    private int width,height;


    public DrawingView(Context context) {
        super(context);
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupDrawing();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupDrawing();
    }

    @Override
    public void setBackgroundResource(int resid) {
        Log.w("setBackgroundResource ======","");
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), resid);
        super.setBackgroundResource(resid);
    }
    private int strokeRadius;
    private ShapeDrawable mBrush;
    private Paint mBitmapPaint;
    public void setupDrawing(){
        Log.w("setupDrawing ======","");
        drawPath = new Path();
        drawPaint = new Paint();



        String seletedColor =  ColorManager.getInstance().getmSeletedColor();
        if(seletedColor.equals("#311B92")) {
            drawPaint.setColor(Color.TRANSPARENT);
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(55);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
            System.out.println("setupDrawing.init color = "+seletedColor);
        }else {
            drawPaint.setColor(Color.parseColor(seletedColor));
            drawPaint.setAntiAlias(true);
            drawPaint.setStrokeWidth(55);
            drawPaint.setStyle(Paint.Style.STROKE);
            drawPaint.setStrokeJoin(Paint.Join.ROUND);
            drawPaint.setStrokeCap(Paint.Cap.ROUND);
            drawPaint.setAlpha(0x70);
            System.out.println("setupDrawing. color = "+seletedColor);
        }



    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //view given size
        Log.w("onSizeChanged ======","");
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888); //result

    }


    @Override
    protected void onDraw(Canvas canvas) {
    //draw view
        Log.w("onDraw ======","");
        canvas.drawColor(Color.TRANSPARENT);
        Bitmap mask = null;
        switch (DataManager.getInstance().getSeletedMoldIndex()){

            case  1 :
             //   mDrawingView.setBackgroundResource(R.drawable.mold_01b);
                 mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_01b_mask);
                break;

            case  2 :
             //   mDrawingView.setBackgroundResource(R.drawable.mold_02b);
                 mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_02b_mask);
                break;

            case  3 :
          //      mDrawingView.setBackgroundResource(R.drawable.mold_03b);
                 mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_03b_mask);
                break;

            case  4 :
              //  mDrawingView.setBackgroundResource(R.drawable.mold_04b);
                 mask = BitmapFactory.decodeResource(getResources(), R.drawable.mold_04b_mask);
                break;

        }


        drawCanvas = new Canvas(canvasBitmap);

        drawPaint.setFilterBitmap(false);
        drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT) ); // DST_OUT DST_IN

        drawCanvas.drawBitmap(mask,0,0,drawPaint);

        drawPaint.setXfermode(null);

        canvas.drawBitmap(canvasBitmap, 0, 0, null);
        canvas.drawPath(drawPath, drawPaint);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//detect user touch
        Log.w("onTouchEvent ======","");
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                drawPath.moveTo(touchX, touchY);//기준점을 x, y로 이동 시킵니다.
                break;
            case MotionEvent.ACTION_MOVE:

                drawPath.lineTo(touchX, touchY);//Path의 마지막에 경로를 추가 합니다.
                break;
            case MotionEvent.ACTION_UP:

                //canvas.drawPath()함수를 호출하면 정의된 path에 따라 그림을 그린다.
                drawCanvas.drawPath(drawPath, drawPaint);//설정한 drawPath 화면에 drawPaint
                drawPath.reset();//path초기화

                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }





}
