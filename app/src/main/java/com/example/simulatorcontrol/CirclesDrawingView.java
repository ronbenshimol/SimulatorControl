package com.example.simulatorcontrol;

import androidx.core.view.MotionEventCompat;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;


public class CirclesDrawingView extends View {

    private float x;
    private float y;
    private int outerCircleRadius = 500;
    private int innerCircleRadius = 100;
    private boolean isJoystickMoving = false;
    private CommandClient client;


    public CirclesDrawingView(Context context, CommandClient simulatorClient) {
        super(context);
        this.client = simulatorClient;
    }

    @Override
    protected void onSizeChanged(int width, int height, int prevWidth, int prevHeight) {
        super.onSizeChanged(width, height, prevWidth, prevHeight);

        int paddingX = getPaddingLeft() + getPaddingRight();
        int paddingY = getPaddingTop() + getPaddingBottom();
        float w = (float)(width - paddingX);
        float h = (float)(height - paddingY);
        x = w / 2;
        y = h / 2;

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        outerCircleRadius = isLandscape ? 400 : 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background
        canvas.drawRGB(51, 153, 255);

        int outerCircleX = getWidth() / 2;
        int outerCircleY = getHeight() / 2;

        // draw the  outer Circle
        Paint outerCircle;
        outerCircle = new Paint();
        outerCircle.setColor(Color.GRAY);
        outerCircle.setStyle(Paint.Style.FILL);
        outerCircle.setAntiAlias(true);
        canvas.drawCircle(outerCircleX, outerCircleY, outerCircleRadius, outerCircle);

        // draw thr inner Circle
        Paint innerCircle;
        innerCircle = new Paint();
        innerCircle.setColor(Color.BLACK);
        innerCircle.setStyle(Paint.Style.FILL);
        innerCircle.setAntiAlias(true);
        canvas.drawCircle(x, y, innerCircleRadius, innerCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {

                if ((event.getX() >= x - innerCircleRadius && event.getX() <= x + innerCircleRadius)
                        && (event.getY() >= y - innerCircleRadius && event.getY() <= y + innerCircleRadius)) {
                    isJoystickMoving = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!isJoystickMoving) {
                    return true;
                }

                float diameter, minX, maxX, minY, maxY;
                float width = getWidth();
                float height = getHeight();

                x = event.getX();
                y = event.getY();

                diameter = outerCircleRadius * 2;
                minX = (width - diameter) / 2;
                maxX = width - (width - diameter) / 2 ;
                minY = (height - diameter) / 2 ;
                maxY = height - (height - diameter) / 2;

                if ((x + innerCircleRadius > maxX) || (x - innerCircleRadius < minX) ||
                        (y + innerCircleRadius > maxY) || (y - innerCircleRadius < minY)) {
                    break;
                }

                float normalizedX, normalizedY;

                // normalized the x & y between -1 to 1
                if (x > (float)(getWidth() / 2)) {
                    normalizedX = (((x + innerCircleRadius - minX)*2) / (maxX - minX)) - 1;
                } else {
                    normalizedX = (((x - innerCircleRadius - minX)*2) / (maxX - minX)) - 1;
                }

                if (y > (float)(getHeight() / 2)) {
                    normalizedY = (((y + innerCircleRadius - minY)*2) / (maxY - minY)) - 1;
                } else {
                    normalizedY = (((y - innerCircleRadius - minY)*2) / (maxY - minY)) - 1;
                }

                // send to server
                client.setAileron(normalizedY);
                client.setElevator(normalizedX);


                // move the joystick
                invalidate();

                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                x =  (float)(getWidth() / 2);
                y = (float)(getHeight() / 2);

                // send default values to server
                client.setAileron(0);
                client.setElevator(0);

                // move the joystick
                invalidate();

                isJoystickMoving = false;
                break;
            }
        }
        return true;
    }
}