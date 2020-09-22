/*
 *
 *  * sufly0001@gmail.com Modify the code to enhance the ease of use.
 *  *
 *  * Copyright (C) 2015 Ted xiong-wei@hotmail.com
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.lottery.biying.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.ScrollView;

import com.lottery.biying.R;
import com.lottery.biying.util.LogTools;

public class ContentScrollView extends ScrollView {


    Context context;
    Bitmap big,small;

    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener listener;

    public ContentScrollView(Context context) {
        super(context);
        Init(context);
    }

    public ContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init(context);
    }

    public ContentScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init(context);
    }

    private void Init(Context context)
    {
        this.context=context;

    }

    public void setOnScrollChangeListener(OnScrollChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        listener.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = this.getParent();
        while (parent != null) {
            if (parent instanceof ScrollLayout) {
                ((ScrollLayout) parent).setAssociatedScrollView(this);
                break;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ViewParent parent = this.getParent();
        if (parent instanceof ScrollLayout) {
            if (((ScrollLayout) parent).getCurrentStatus() == ScrollLayout.Status.OPENED)
                return false;
        }
        return super.onTouchEvent(ev);
    }


//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        if(getWidth()>0)
//        {
//            Paint paint=new Paint();
//            paint.setAntiAlias(true);
//            canvas.drawBitmap(big,0,0,paint);
//            int middlew=(big.getWidth()-small.getWidth())/2;
//            int middleh=(big.getHeight()-small.getHeight())/2;
//            canvas.drawBitmap(small,middlew,middleh,paint);
//        }
//    }
}
