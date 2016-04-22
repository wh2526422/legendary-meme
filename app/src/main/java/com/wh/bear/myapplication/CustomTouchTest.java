package com.wh.bear.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
public class CustomTouchTest extends Activity {

    private SharedPreferences mSp;
    private int mRectWidth;
    private int mRectHeight;
    private boolean test1;
    private boolean test2;
    private boolean test3;
    private boolean test4;
    private boolean test5;
    private boolean test6;


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
        Paint circlePaint;
        float padding;

        List<List<Point>> lines;
        List<Point> line;
        List<Circle> circles;
        List<Point> points;
        List<Line> shortLines;
        int flag;
        float oldDist;


        public CanvasView(Context context) {
            super(context);
            padding = getResources().getDimension(R.dimen.x60);
            lines = new ArrayList<>();
            line = new ArrayList<>();

            mLinePaint = new Paint();
            mLinePaint.setAntiAlias(true);
            mLinePaint.setStrokeCap(Paint.Cap.ROUND);
            mLinePaint.setStrokeWidth(15);
            mLinePaint.setColor(Color.BLUE);

            moveLinePaint = new Paint();
            moveLinePaint.setAntiAlias(true);
            moveLinePaint.setStrokeCap(Paint.Cap.ROUND);
            moveLinePaint.setStrokeWidth(15);
            moveLinePaint.setColor(Color.RED);

            circlePaint = new Paint();
            circlePaint.setAntiAlias(true);
            circlePaint.setStrokeCap(Paint.Cap.ROUND);
            circlePaint.setStrokeWidth(6);
            circlePaint.setStyle(Paint.Style.STROKE);

            circles = readCircles();
            points = readPoints();
            shortLines = rendLines();
        }

        private List<Line> rendLines() {
            List<Line> lines = new ArrayList<>();

            Line l1 = new Line(0, mRectHeight / 2, padding, mRectHeight / 2);
            Line l2 = new Line(mRectWidth / 2, mRectHeight - padding, mRectWidth / 2, mRectHeight);
            Line l3 = new Line(mRectWidth - padding, mRectHeight / 2, mRectWidth, mRectHeight / 2);
            Line l4 = new Line(mRectWidth / 2, 0, mRectWidth / 2, padding);

            lines.add(l1);
            lines.add(l2);
            lines.add(l3);
            lines.add(l4);

            return lines;
        }

        private List<Circle> readCircles() {
            List<Circle> circles = new ArrayList<>();
            int hPadding = mRectHeight / 8;
            int wPadding = mRectWidth / 6;
            Circle c1 = new Circle(wPadding, hPadding, padding);
            Circle c2 = new Circle(wPadding * 3, hPadding, padding);
            Circle c3 = new Circle(wPadding * 5, hPadding, padding);

            Circle c4 = new Circle(wPadding * 2, hPadding * 5 / 2, padding * 2 / 3);
            Circle c5 = new Circle(wPadding * 4, hPadding * 5 / 2, padding * 2 / 3);

            Circle c6 = new Circle(wPadding, hPadding * 4, padding);
            Circle c7 = new Circle(wPadding * 3, hPadding * 4, padding);
            Circle c8 = new Circle(wPadding * 5, hPadding * 4, padding);

            Circle c9 = new Circle(wPadding * 2, hPadding * 11 / 2, padding * 2 / 3);
            Circle c10 = new Circle(wPadding * 4, hPadding * 11 / 2, padding * 2 / 3);

            Circle c11 = new Circle(wPadding, hPadding * 7, padding);
            Circle c12 = new Circle(wPadding * 3, hPadding * 7, padding);
            Circle c13 = new Circle(wPadding * 5, hPadding * 7, padding);

            circles.add(c1);
            circles.add(c2);
            circles.add(c3);
            circles.add(c4);
            circles.add(c5);
            circles.add(c6);
            circles.add(c7);
            circles.add(c8);
            circles.add(c9);
            circles.add(c10);
            circles.add(c11);
            circles.add(c12);
            circles.add(c13);
            return circles;
        }

        private List<Point> readPoints() {
            List<Point> points = new ArrayList<>();
            Point p1 = new Point((int) padding, mRectHeight);
            Point p2 = new Point((int) padding, (int) padding);
            Point p3 = new Point((int) (mRectWidth - padding), (int) padding);
            Point p4 = new Point((int) (mRectWidth - padding), (int) (mRectHeight - padding));
            Point p5 = new Point((int) padding * 2, (int) (mRectHeight - padding));
            Point p6 = new Point((int) padding * 2, (int) padding * 2);
            Point p7 = new Point((int) (mRectWidth - padding * 2), (int) padding * 2);
            Point p8 = new Point((int) (mRectWidth - padding * 2), (int) (mRectHeight - padding * 2));
            Point p9 = new Point((int) padding * 3, (int) (mRectHeight - padding * 2));
            Point p10 = new Point((int) padding * 3, (int) padding * 3);

            points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(p4);
            points.add(p5);
            points.add(p6);
            points.add(p7);
            points.add(p8);
            points.add(p9);
            points.add(p10);
            return points;
        }

        private List<Circle> readTest6Cicles() {
            List<Circle> circles = new ArrayList<>();
            Circle c1 = new Circle(mRectWidth / 2, mRectHeight / 2, Math.min(mRectWidth / 2, mRectHeight / 2));
            Circle c2 = new Circle(mRectWidth / 2, mRectHeight / 2, Math.min(mRectWidth / 8, mRectHeight / 8));
            Circle c3 = new Circle(mRectWidth / 2, mRectHeight / 2, Math.min(mRectWidth / 4, mRectHeight / 3));
            c3.color = Color.GREEN;
            c3.flag = 5;
            circles.add(c1);
            circles.add(c2);
            circles.add(c3);
            return circles;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!test1) {
                canvas.drawLine(0, padding, mRectWidth - padding, mRectHeight, mLinePaint);
                canvas.drawLine(padding, 0, mRectWidth, mRectHeight - padding, mLinePaint);

                drawLinesFree(canvas);
            } else if (!test2) {
                canvas.drawLine(0, mRectHeight - padding, mRectWidth - padding, 0, mLinePaint);
                canvas.drawLine(padding, mRectHeight, mRectWidth, padding, mLinePaint);

                drawLinesFree(canvas);
            } else if (!test3) {

                for (int i = 0; i < circles.size(); i++) {
                    Circle circle = circles.get(i);
                    circlePaint.setColor(circle.color);
                    canvas.drawCircle(circle.cx, circle.cy, circle.radios, circlePaint);
                }
            } else if (!test4) {
                for (int i = 0; i < points.size() - 1; i++) {
                    canvas.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y, mLinePaint);
                }

                drawLinesFree(canvas);
            } else if (!test5) {
                for (int i = 0; i < shortLines.size(); i++) {
                    Line line = shortLines.get(i);
                    mLinePaint.setColor(line.color);
                    canvas.drawLine(line.startx, line.starty, line.stopx, line.stopy, mLinePaint);
                }

                drawLinesFree(canvas);
            } else if (!test6){
                for (int i = 0; i < circles.size(); i++) {
                    Circle circle = circles.get(i);
                    circlePaint.setColor(circle.color);
                    canvas.drawCircle(circle.cx, circle.cy, circle.radios, circlePaint);
                }

            } else {
                drawLinesFree(canvas);
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int downX = 0;
            int downY = 0;
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    if (test2 && !test3) {
                        changeCircleColor(downX, downY, circles);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = distance(event);
                    if (oldDist > 10f) {
                        flag = 1;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    line.add(new Point(x, y));
                    if (test4 && !test5) {
                        changeLineColor(x, y, shortLines);
                    } else if (test5 & !test6) {
                        if (flag == 1) {
                            float newDist = distance(event);
                            changeCircleColorAndRadios(oldDist, newDist, circles);
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    lines.add(line);
                    if (!test1) {
                        test1 = ifTest1Success(line);
                        if (!test1) {
                            Toast.makeText(CustomTouchTest.this, "测试失败", Toast.LENGTH_SHORT).show();
                        } else {
                            lines.clear();
                        }
                    } else if (!test2) {
                        test2 = ifTest2Success(line);
                        if (!test2) {
                            Toast.makeText(CustomTouchTest.this, "测试失败", Toast.LENGTH_SHORT).show();
                        } else {
                            lines.clear();
                        }
                    } else if (!test3) {
                        test3 = ifTest3Success(circles);
                        if (test3) {
                            Toast.makeText(CustomTouchTest.this, "测试成功", Toast.LENGTH_SHORT).show();
                            circles.clear();
                            lines.clear();
                        }
                    } else if (!test4) {
                        test4 = ifTest4Success(line);
                        if (test4) {
                            Toast.makeText(CustomTouchTest.this, "测试成功", Toast.LENGTH_SHORT).show();
                            lines.clear();
                        } else {
                            Toast.makeText(CustomTouchTest.this, "测试失败", Toast.LENGTH_SHORT).show();
                        }
                    } else if (!test5) {
                        test5 = ifTest5Success(shortLines);
                        if (test5) {
                            Toast.makeText(CustomTouchTest.this, "测试成功", Toast.LENGTH_SHORT).show();
                            lines.clear();
                            circles = readTest6Cicles();
                        }
                    } else if (!test6) {
                        flag = 0;
                        test6 = ifTest6Success(circles);
                        if (test6) {
                            showDialog(test6);
                            lines.clear();
                        }
                    }
                    line = new ArrayList<>();
                    break;
            }
            invalidate();
            return true;
        }

        /**
         * 自由划线
         *
         * @param canvas
         */
        private void drawLinesFree(Canvas canvas) {
            for (int k = 0; k < line.size() - 2; k++) {
                canvas.drawLine(line.get(k).x, line.get(k).y, line.get(k + 1).x, line.get(k + 1).y, moveLinePaint);
            }

            for (int i = 0; i < lines.size(); i++) {
                List<Point> points = lines.get(i);
                for (int j = 0; j < points.size() - 1; j++) {
                    canvas.drawLine(points.get(j).x, points.get(j).y, points.get(j + 1).x, points.get(j + 1).y, moveLinePaint);
                }

            }
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
            Point firstPo = null;
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                int x = point.x;
                int y = point.y;
                centerY = y;
                centerX = centerY * mRectWidth / mRectHeight;

                if (i == 0) {
                    firstPo = point;
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
                    if (!(x < padding && y < padding && firstPo.x > mRectWidth - padding && firstPo.y > mRectHeight - padding)) {
                        return false;
                    }
                }
                //  补漏判断起点终点是否在一个对角
                if (i == points.size() - 1 && x > mRectWidth - padding && y > mRectHeight - padding &&
                        (firstPo.x > mRectWidth - padding || firstPo.y > mRectHeight - padding)) {
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
         * 计算点测验是否成功
         *
         * @param circles
         * @return
         */
        private boolean ifTest3Success(List<Circle> circles) {
            for (Circle circle : circles) {
                if (circle.color == Color.RED) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 测试4 是否成功
         *
         * @param points
         * @return
         */
        private boolean ifTest4Success(List<Point> points) {
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                if (i == 0 && (point.x >= padding || point.y <= mRectHeight - padding)) {
                    return false;
                }

                if (point.x > padding - 10 && point.x < padding + 10 && point.y >= padding) {
                    return false;
                }

                if (point.y > padding - 10 && point.y < padding - 10 && point.x >= padding && point.x <= mRectWidth - padding) {
                    return false;
                }

                if (point.x > mRectWidth - padding - 10 && point.x < mRectWidth - padding + 10 && point.y >= padding && point.y <= mRectHeight - padding) {
                    return false;
                }

                if (point.y > mRectHeight - padding - 10 && point.y < mRectHeight - padding + 10 && point.x >= padding * 2 && point.x <= mRectWidth - padding) {
                    return false;
                }

                if (point.x > padding * 2 - 10 && point.x < padding * 2 + 10 && point.y >= padding * 2 && point.y <= mRectHeight - padding) {
                    return false;
                }

                if (point.y > padding * 2 - 10 && point.y < padding * 2 + 10 && point.x >= padding * 2 && point.x <= mRectWidth - padding * 2) {
                    return false;
                }

                if (point.x > mRectWidth - padding * 2 - 10 && point.x < mRectWidth - padding * 2 + 10 && point.y >= padding * 2 && point.y <= mRectHeight - padding * 2) {
                    return false;
                }

                if (point.y > mRectHeight - padding * 2 - 10 && point.y < mRectHeight - padding * 2 + 10 && point.x >= padding * 3 && point.x <= mRectWidth - padding * 2) {
                    return false;
                }

                if (point.x > padding * 3 - 10 && point.x < padding * 3 + 10 && point.y >= padding * 3 && point.y <= mRectHeight - padding * 2) {
                    return false;
                }

                if (i == points.size() - 1 && (point.x < padding * 3 || point.x > padding * 4 || point.y > mRectHeight - padding * 2 || point.y < mRectHeight - padding * 3)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 判断测试5是否成功
         *
         * @param lines
         * @return
         */
        private boolean ifTest5Success(List<Line> lines) {
            for (int i = 0; i < lines.size(); i++) {
                Line line = lines.get(i);
                if (line.color == Color.BLUE) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 判断测试6是否成功
         * @param circles
         * @return
         */
        private boolean ifTest6Success(List<Circle> circles) {
            for (int i = 0; i < circles.size() ; i ++) {
                Circle circle = circles.get(i);
                if (circle.flag < 5) {
                    return false;
                }
            }

            return true;
        }

        /**
         * 当圆圈被点击时改变其颜色
         *
         * @param downX
         * @param downY
         * @param circles
         */
        private void changeCircleColor(int downX, int downY, List<Circle> circles) {
            for (int i = 0; i < circles.size(); i++) {
                Circle circle = circles.get(i);
                if ((circle.cx - downX) * (circle.cx - downX) + (circle.cy - downY) * (circle.cy - downY) < circle.radios * circle.radios) {
                    circle.color = Color.GREEN;
                }
            }
        }

        private void changeLineColor(int x, int y, List<Line> lines) {
            for (int i = 0; i < lines.size(); i++) {
                Line line = lines.get(i);
                if (x > line.startx - 10 && x < line.stopx + 10 && y > line.starty - 10 && y < line.stopy + 10) {
                    line.color = Color.GREEN;
                }
            }
        }

        private void changeCircleColorAndRadios(float oldDist, float newDist, List<Circle> circles) {
            Circle circle = circles.get(2);
            circle.radios = newDist / oldDist * circle.radios;

            if (circle.radios >= circles.get(0).radios) {
                circle.radios = circles.get(0).radios;
                circles.get(0).flag += 1;
            }

            if (circle.radios <= circles.get(1).radios) {
                circle.radios = circles.get(1).radios;
                circles.get(1).flag += 1;
            }

        }

        private float distance(MotionEvent event) {
            float x = 0;
            float y = 0;
            if (event.getPointerCount() > 1) {
                x = event.getX(0) - event.getX(1);
                y = event.getY(0) - event.getY(1);
            }

            return (float) Math.sqrt(x * x + y * y);
        }

    }

    class Circle {
        int cx;
        int cy;
        float radios;
        int color = Color.RED;
        int flag = 0;

        public Circle(int cx, int cy, float radios) {
            this.cx = cx;
            this.cy = cy;
            this.radios = radios;
        }
    }

    class Line {
        float startx;
        float starty;
        float stopx;
        float stopy;
        int color = Color.BLUE;

        public Line(float startx, float starty, float stopx, float stopy) {
            this.startx = startx;
            this.starty = starty;
            this.stopx = stopx;
            this.stopy = stopy;
        }
    }
    private void showDialog(boolean success){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("信息提示");
        builder.setMessage(success ? "测试成功" : "测试失败");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.create().show();
    }
}
