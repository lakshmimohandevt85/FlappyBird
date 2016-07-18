package com.sdsu.cs646.lakshmi.assignment3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;


public class AnimationView extends View implements View.OnTouchListener
{
    Bitmap  bitmap_bird_up;
    Bitmap  bitmap_bird_down;
    Bitmap  bitmap_game_end;
    Bitmap  bitmap_backGround;


    // gameState = 0: when start.
    // gameState = 1: when running.
    // gameState = 2: when stopped.
    int gameState;

    float bird_x_postion,bird_y_postion;
    int x1_dir;
    int x2_dir;

    boolean flip = false;

    int top_left_rect_x1;
    int top_left_rect_x2;

    int top_right_rect_x1;
    int top_right_rect_x2;

    int bottom_left_rect_x1;
    int bottom_left_rect_x2;

    int bottom_right_rect_x1;
    int bottom_right_rect_x2;
    int bird_width = 0;
    int score = 0;

    /**
     * arg Constructor
     * @param context
     */
    public AnimationView (Context context)
    {
        super(context);
        setOnTouchListener(this);

        // gameState = 1 when game starts/running and 1 when game stops..
        gameState = 0;

        bitmap_bird_up = BitmapFactory.decodeResource(getResources(),R.drawable.bird);
        bitmap_bird_down = BitmapFactory.decodeResource(getResources(),R.drawable.bird2);

        bitmap_game_end = BitmapFactory.decodeResource(getResources(),R.drawable.gameover);

        bird_x_postion =0;
        bird_y_postion =0;
        x1_dir = 1;
        x2_dir = 1;

    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        super.onDraw(canvas);

        // Initialize the horizontal co-ordinates for the rectangles..
        if (gameState == 0)
        {
            top_left_rect_x1= (int) (getWidth() *.75) ;
            top_left_rect_x2 = top_left_rect_x1+25;

            top_right_rect_x1 = top_left_rect_x1 + 350;
            top_right_rect_x2 = top_right_rect_x1 + 25;

            /** Assign value for the bottom rectangles... **/

            bottom_left_rect_x1 = top_left_rect_x1;
            bottom_left_rect_x2 = top_left_rect_x2;

            bottom_right_rect_x1 = top_right_rect_x1;
            bottom_right_rect_x2 = top_right_rect_x2;
            gameState = 1;
        }

        Paint topLeftPaint = new Paint();
        Paint topRightPaint = new Paint();
        Paint bottomLeftPaint = new Paint();
        Paint bottomRightPaint = new Paint();

        // Top left rectangle

        Rect topLeftRectangle = new Rect();
        top_left_rect_x1 = top_left_rect_x1-x1_dir;
        top_left_rect_x2 = top_left_rect_x2 - x2_dir;

        topLeftRectangle.set(top_left_rect_x1, 0, top_left_rect_x2, getHeight() / 5);
        topLeftPaint.setStyle(Paint.Style.FILL);
        topLeftPaint.setColor(Color.parseColor("#FFFF99"));

        // Top Right rectangle
        Rect topRightRectangle = new Rect();

        top_right_rect_x1 = top_right_rect_x1-x1_dir;
        top_right_rect_x2 = top_right_rect_x2 - x2_dir;

        topRightRectangle.set( (top_right_rect_x1), 0, top_right_rect_x2, getHeight() / 3);
        topRightPaint.setStyle(Paint.Style.FILL);
        topRightPaint.setColor(Color.parseColor("#FFFF99"));


        //bottom left rectangle
        Rect bottomLeftRectangle = new Rect();
        bottom_left_rect_x1 = bottom_left_rect_x1-x1_dir;
        bottom_left_rect_x2 = bottom_left_rect_x2 - x2_dir;

        bottomLeftRectangle.set(bottom_left_rect_x1, (3 * getHeight() / 5), bottom_left_rect_x2, getHeight());
        bottomLeftPaint.setStyle(Paint.Style.FILL);
        bottomLeftPaint.setColor(Color.parseColor("#FFFF99"));

        Rect bottomRightRectangle = new Rect();
        bottom_right_rect_x1 = bottom_right_rect_x1-x1_dir;
        bottom_right_rect_x2 = bottom_right_rect_x2 - x2_dir;

        bottomRightRectangle.set((bottom_right_rect_x1), (2 * getHeight() / 3), bottom_right_rect_x2, getHeight());
        bottomRightPaint.setStyle(Paint.Style.FILL);
        bottomRightPaint.setColor(Color.parseColor("#FFFF99"));

        // Get the width of the bird ...
        bird_width = bitmap_bird_up.getWidth();

        // Game ending scenarios.

        // Scenario:1.1 (collision detection), When bird hits any rectangles game will end OR gameState = 2

        if ( top_left_rect_x2 > 0 &&  topLeftRectangle.bottom >= (int) (bird_y_postion )  &&  bird_width >= top_left_rect_x1 )
        {
            gameState = 2;
        }

        // Scenario:1.2 (collision detection)
        if ( bottom_left_rect_x2 > 0 && bottomLeftRectangle.top <= (int) (bird_y_postion + bitmap_bird_up.getHeight()) &&  bird_width >= bottom_left_rect_x1 )
        {
            gameState = 2;
        }
        // Scenario:1.3 (collision detection)
        if ( topRightRectangle.bottom >= (int)bird_y_postion  && bird_width >= top_right_rect_x1)
        {
            gameState = 2;

        }
        // Scenario:1.4 (collision detection)
        if ( bottomRightRectangle.top <= (int) (bird_y_postion + bitmap_bird_up.getHeight()) && bird_width >= bottom_right_rect_x1)
        {
            gameState = 2;
        }

        // Scenario:2, Game end when bird hits ground
        if ((bird_y_postion + bitmap_bird_up.getHeight()) >= getHeight())
        {
            gameState = 2;
        }
        else
        {
        // else increment bird_y_postion by 1;

            bird_y_postion += 1;
        }

        // Scenario:3, Game ends when right most rectangles passes the screen..
        if (top_right_rect_x2 == bird_x_postion || bottom_right_rect_x2 == bird_x_postion)
        {
            gameState = 2;
        }

        // Scenario:4, Game ends when bird hits the top
        if (bird_y_postion <= 0){
            gameState = 2;
        }
        bitmap_backGround = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
        canvas.drawBitmap(bitmap_backGround, null, new Rect(0, 0, getWidth(), getHeight()), null);

        if(flip)
        {
            canvas.drawBitmap(bitmap_bird_up, bird_x_postion, bird_y_postion, new Paint());
            flip = false;
        }
        else
        {
            canvas.drawBitmap(bitmap_bird_down, bird_x_postion, bird_y_postion, new Paint());
            flip = true;
        }
        canvas.drawRect(topLeftRectangle, topLeftPaint);
        canvas.drawRect(topRightRectangle, topRightPaint);
        canvas.drawRect(bottomLeftRectangle, bottomLeftPaint);
        canvas.drawRect(bottomRightRectangle, bottomRightPaint);

        // call the invalidate() method only when Game is not stopped.

        if (gameState == 1)
        {
            invalidate();
        }
        else if (gameState == 2)
        {
            canvas.drawBitmap(bitmap_game_end, ( getWidth() - getWidth()/2 )/2, ( getHeight() - getHeight()/2 ) /2, new Paint());

            // Scoring Rates
            // No rectangle pass => Score:0
            // First pair of rectangles passes( 2 rectangles) => Score:2
            // Second pair of rectangles passes ( 2 (previous pair)+ 2 rectangles) => Score:4


            if (top_right_rect_x2 <=0)
            {
                score = 4;
            }else if (top_left_rect_x2 <=0)
            {
                score = 2;
            }
            AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
            adb.setTitle("Start App, Current Score: "+score);

            adb.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    gameState = 0;
                    bird_y_postion =0;
                    invalidate();
                    score =0;
                }
            });

            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                } });
            adb.show();

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (bird_y_postion > getHeight()/2)
        {
            bird_y_postion = (float) (bird_y_postion - (0.025* bird_y_postion));
        }
        else
        {
            bird_y_postion = (float) (bird_y_postion - (0.1* bird_y_postion));
        }

        return true;
    }
}
