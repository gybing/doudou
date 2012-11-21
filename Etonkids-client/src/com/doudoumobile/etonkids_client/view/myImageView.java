package com.doudoumobile.etonkids_client.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class myImageView extends ImageView {

    private int color = Color.parseColor("GRAY");
    public int id = 0;
    
    public myImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        
        super.onDraw(canvas);    
        //画边框
        Rect rec=canvas.getClipBounds();
        rec.bottom--;
        rec.right--;
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rec, paint);
    }
}