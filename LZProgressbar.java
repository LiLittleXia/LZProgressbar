package com.lw.lw.marqueeviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by liwei on 2017/5/26.
 */

public class LZProgressbar extends View {


    private Paint mPaintO = new Paint();
    private Paint mPaintB = new Paint();
    private double progress = 0;
    private int pbColor;
    private int bgColor;
    private int circularPic;
    private Bitmap circularBm;
    private int heightPx;


    public LZProgressbar(Context context) {
        super(context);
    }

    public LZProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(attrs);
    }

    public LZProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(attrs);
    }


    // 2.初始化画笔
    private void initPaint(AttributeSet attrs) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.cProgressbarStyle);
        pbColor = a.getColor(R.styleable.cProgressbarStyle_cPbNowColor, 0xffff0000);
        bgColor = a.getColor(R.styleable.cProgressbarStyle_cPbBackgroundColor, 0xffff0000);
        circularPic = a.getResourceId(R.styleable.cProgressbarStyle_picSrc, R.drawable.yuandian);
        heightPx = a.getDimensionPixelOffset(R.styleable.cProgressbarStyle_heightPx,60);


        setBitmap();




        mPaintB.setColor(bgColor);       //设置画笔颜色
        mPaintB.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaintB.setAntiAlias(true);
        mPaintO.setColor(pbColor);       //设置画笔颜色
        mPaintO.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaintO.setAntiAlias(true);
    }

    private void setBitmap() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), circularPic);
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        int newWidth = heightPx;
        int newHeight = heightPx;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        circularBm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        //第一个圆
        canvas.drawCircle(h / 2, h / 2, (h - 14) / 2, mPaintO);
        //第一个矩形
        canvas.drawRect(h / 2, 7, (float) ((w - h) * progress + 7 + (h - 14) / 2), h - 7, mPaintO);
        //第二个圆
        canvas.drawCircle(w - (h / 2), h / 2, (h - 14) / 2, mPaintB);
        //第二个矩形
        canvas.drawRect((float) ((w - h) * progress + 7 + (h - 14) / 2), 7, w - (h / 2), h - 7, mPaintB);
        //图形圆点
        canvas.drawBitmap(circularBm, (float) (((getWidth() - getHeight()) * progress)), 0, new Paint());

    }






    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }


}
