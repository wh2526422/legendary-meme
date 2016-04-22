package com.wh.bear.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/18.
 */
public class TouchScrenTest extends Activity {

    private SharedPreferences mSp;
    private int mRectWidth;
    private int mRectHeight;
    private boolean test1;
    private boolean test2;
    private boolean test3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE);
        mSp = getSharedPreferences("FactoryMode", Context.MODE_PRIVATE);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        mRectWidth = dm.widthPixels;
        mRectHeight = dm.heightPixels;

        setContentView(new CanvasView(this));
    }

    private class CanvasView extends View {
        Paint mLinePaint;
        Paint moveLinePaint;
        Paint rectPaint;
        float padding;

        List<List<Point>> lines;
        List<Point> line;
        List<TestRect> rects;

        public CanvasView(Context context) {
            super(context);
            padding = 60;
            lines = new ArrayList<>();
            line = new ArrayList<>();

            mLinePaint = new Paint();
            mLinePaint.setAntiAlias(true);
            mLinePaint.setStrokeCap(Paint.Cap.ROUND);
            mLinePaint.setStrokeWidth(4);
            mLinePaint.setColor(Color.BLUE);

            moveLinePaint = new Paint();
            moveLinePaint.setAntiAlias(true);
            moveLinePaint.setStrokeCap(Paint.Cap.ROUND);
            moveLinePaint.setStrokeWidth(4);
            moveLinePaint.setColor(Color.RED);

            rectPaint = new Paint();
            rectPaint.setAntiAlias(true);
            rectPaint.setStrokeCap(Paint.Cap.ROUND);
            rectPaint.setStrokeWidth(4);
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.STROKE);

            rects = readRects();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!test1) {
                canvas.drawLine(0, padding, mRectWidth - padding, mRectHeight, mLinePaint);
                canvas.drawLine(padding, 0, mRectWidth, mRectHeight - padding, mLinePaint);

                for (int k = 0; k < line.size() - 2; k++) {
                    canvas.drawLine(line.get(k).x, line.get(k).y, line.get(k + 1).x, line.get(k + 1).y, moveLinePaint);
                }

                for (int i = 0; i < lines.size(); i++) {
                    List<Point> points = lines.get(i);
                    for (int j = 0; j < points.size() - 1; j++) {
                        canvas.drawLine(points.get(j).x, points.get(j).y, points.get(j + 1).x, points.get(j + 1).y, moveLinePaint);
                    }

                }
            } else if (!test2) {
                canvas.drawLine(0, mRectHeight - padding, mRectWidth - padding, 0, mLinePaint);
                canvas.drawLine(padding, mRectHeight, mRectWidth, padding, mLinePaint);

                for (int k = 0; k < line.size() - 2; k++) {
                    canvas.drawLine(line.get(k).x, line.get(k).y, line.get(k + 1).x, line.get(k + 1).y, moveLinePaint);
                }

                for (int i = 0; i < lines.size(); i++) {
                    List<Point> points = lines.get(i);
                    for (int j = 0; j < points.size() - 1; j++) {
                        canvas.drawLine(points.get(j).x, points.get(j).y, points.get(j + 1).x, points.get(j + 1).y, moveLinePaint);
                    }

                }
            } else if (!test3) {
                for (TestRect rect : rects) {
                    rectPaint.setColor(rect.color);
                    rectPaint.setStyle(rect.type);
                    canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom,rectPaint);
                }

            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    if (test2 && !test3) {
                        changeRectColor(x,y,rects);
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    line.add(new Point(x, y));
                    if (test2 && !test3) {
                        changeRectColor(x,y,rects);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    lines.add(line);

                    if (!test1) {
                        test1 = ifTest1Success(line);

                        if (!test1) {
                            Toast.makeText(TouchScrenTest.this, "测试失败", Toast.LENGTH_SHORT).show();
                        } else {
                            lines.clear();
                        }
                    } else if (!test2) {
                        test2 = ifTest2Success(line);
                        if (!test2) {
                            Toast.makeText(TouchScrenTest.this, "测试失败", Toast.LENGTH_SHORT).show();
                        } else {
                            lines.clear();
                        }
                    } else if (!test3) {
                        test3 = ifTest3Success(rects);
                        if (test3) {
                            Toast.makeText(TouchScrenTest.this, "测试成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                    line = new ArrayList<>();
                    break;
            }
            invalidate();
            return true;
        }

        /**
         * 第一项线性测试结果计算
         *
         * @param points
         * @return
         */

        private boolean ifTest1Success(List<Point> points) {
            int centerX;
            int centerY;
            Point firtsPo = null;
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                int x = point.x;
                int y = point.y;
                centerY = y;
                centerX = centerY * mRectWidth / mRectHeight;

                if (i == 0) {
                    firtsPo = point;
                }
                //  判断第一个点的位置是否在两侧对角
                if (i == 0 && (x > padding || y > padding)) {
                    if (!(x > mRectWidth - padding && y > mRectHeight - padding)) {
                        return false;
                    }
                }
                //  判断所画的线是否超出预定范围
                if (x < centerX - padding || x > centerX + padding) {
                    return false;
                }
                //  判断终点是否在两侧对角
                if (i == points.size() - 1 && (x < mRectWidth - padding || y < mRectHeight - padding)) {
                    if (!(x < padding && y < padding && firtsPo.x > mRectWidth - padding && firtsPo.y > mRectHeight - padding)) {
                        return false;
                    }
                }
                //  补漏判断起点终点是否在一个对角
                if (i == points.size() - 1 && x > mRectWidth - padding && y > mRectHeight - padding &&
                        (firtsPo.x > mRectWidth - padding || firtsPo.y > mRectHeight - padding)) {
                    return false;
                }
            }

            return true;
        }

        /**
         * 第二项线性测试结果计算
         *
         * @param points
         * @return
         */
        private boolean ifTest2Success(List<Point> points) {
            int centerX;
            int centerY;
            Point firtsPo = null;

            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                int x = point.x;
                int y = point.y;

                centerX = x;
                centerY = (mRectWidth - centerX) * mRectHeight / mRectWidth;

                if (i == 0) {
                    firtsPo = point;
                }

                if (i == 0 && (x > padding || y < mRectHeight - padding)) {
                    if (!(x > mRectWidth - padding && y < padding)) {
                        return false;
                    }
                }

                if (y < centerY - padding || y > centerY + padding) {
                    return false;
                }

                if (i == points.size() - 1 && (x < mRectWidth - padding || y > padding)) {
                    if (!(x < padding && y > mRectHeight - padding && firtsPo.x > mRectWidth - padding && firtsPo.y < padding)) {
                        return false;
                    }
                }

                if (i == points.size() - 1 && x > mRectWidth - padding && y < padding &&
                        (firtsPo.x > mRectWidth - padding || firtsPo.y < padding)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 读取方块坐标
         * @return
         */
        private List<TestRect> readRects(){
            List<TestRect> rects = new ArrayList<>();
            TestRect rect;
            int h = mRectHeight / 8;
            int w = mRectWidth / 6;
            int left = 0;
            int right = w;
            int top = 0;
            int bottom = h;
            for (int i = 0; i < 7 ;i ++) {
                top += h;
                bottom +=h;
                rect = new TestRect(left,top,right,bottom);
                rects.add(rect);
                if (i == 6) {
                    for (int j = 0; j < 5; j ++) {
                        left += w;
                        right += w;
                        rect = new TestRect(left,top,right,bottom);
                        rects.add(rect);
                        if (j == 4) {
                            for (int k = 0; k < 7;k ++){
                                top -= h;
                                bottom -=h;
                                rect = new TestRect(left,top,right,bottom);
                                rects.add(rect);
                                if (k == 6) {
                                    for (int l = 0; l < 5; l++){
                                        left -= w;
                                        right -= w;
                                        rect = new TestRect(left,top,right,bottom);
                                        rects.add(rect);
                                    }
                                }
                            }

                        }
                    }
                }
            }
            return rects;
        }

        /**
         * 改变方块的颜色
         * @param x
         * @param y
         * @param rects
         */
        private void changeRectColor(int x, int y,List<TestRect> rects) {
            for (int i = 0; i < rects.size(); i ++) {
                TestRect rect = rects.get(i);
                if (x > rect.left && x < rect.right && y > rect.top && y < rect.bottom) {
                    rect.color = Color.GREEN;
                    rect.type = Paint.Style.FILL;
                }
            }

        }

        /**
         * 判断测试三是否成功
         * @param rects
         * @return
         */
        private boolean ifTest3Success(List<TestRect> rects) {
            for (TestRect rect : rects) {
                if (rect.color == Color.RED) {
                    return false;
                }
            }
            return true;
        }
    }

    class TestRect {
        int left;
        int right;
        int top;
        int bottom;
        int color = Color.RED;
        Paint.Style type = Paint.Style.STROKE;

        public TestRect(int left,  int top, int right, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }

        return super.onKeyDown(keyCode, event);
    }
}
