package com.parallax.list.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.parallax.list.CommonConstant;


/**
 * Created by jiangyue on 15/7/16.
 */
public class ParallaxListView extends ListView {

    /* variable */
    private float touchY, deltaY, nowY;
    private ListTransformer transform;

    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTransformItem(ListTransformer transform) {
        this.transform = transform;
    }

    /* 页面滑动监听 */
    public interface ListTransformer {
        public void transformItem(View view, float deltaY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != getChildAt(0) && transform != null) {
            nowY = Math.abs(getChildAt(0).getTop()) + getFirstVisiblePosition() * CommonConstant.HOMEITEMLITECELLHEIGHT;
            deltaY = nowY - touchY;
            touchY = nowY;
            //设置偏移量
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                transform.transformItem(child, deltaY);
            }
        }
    }

}
