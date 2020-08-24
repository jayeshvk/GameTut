package com.learning.gametut;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.MotionEvent;

    public class VButton
    {
        public Matrix btn_matrix = new Matrix();

        public RectF btn_rect;

        float width;
        float height;   
        Bitmap bg;

        public VButton(int width, int height, Bitmap bg)
        {
            this.width = width;
            this.height = height;
            this.bg = bg;

            btn_rect = new RectF(0, 0, width, height);
        }

        public void setPosition(float x, float y)
        {
        	
            btn_matrix.setTranslate(x, y);
            btn_matrix.mapRect(btn_rect);
        }
        

        public void draw(Canvas canvas)
        {
            canvas.drawBitmap(bg, btn_matrix, null);
        }
        
        public boolean onTouchEvent(MotionEvent e){
        	
        	return true;
        }
    }