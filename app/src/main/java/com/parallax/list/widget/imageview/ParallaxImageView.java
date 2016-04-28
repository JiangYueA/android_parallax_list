package com.parallax.list.widget.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jiangyue on 15/7/10.
 */
public class ParallaxImageView extends ImageView {
    private static final String TAG = ParallaxImageView.class.getName();

    /* variable */
    public float imgDeltaS = 1.0f;
    public float imgDeltaY = 0.0f;

    public ParallaxImageView(Context context) {
        super(context);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setUiMatrix(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setUiMatrix(false);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setUiMatrix(true);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setUiMatrix(true);
    }

    /**
     * 设定Ui的位置和缩放
     */
    private void setUiMatrix(boolean falg) {
        if (null != getDrawable()) {
            float[] values = new float[9];
            Matrix matrix = new Matrix();
            matrix.set(getImageMatrix());
            matrix.mapPoints(values);

            float dwidth = getDrawable().getIntrinsicWidth();
            float dheight = getDrawable().getIntrinsicHeight();

            float vwidth = getWidth();
            float vheight = getHeight();

            float scale = 0f;
            float dy = 0f;

            if (dwidth * vheight > vwidth * dheight) {
                scale = vheight / dheight;
            } else {
                scale = vwidth / dwidth;
            }

            //计算图片的最大偏移量
            if (scale != 0) {
                imgDeltaY = dheight * scale - vheight;
            }

            //是否需要重置偏移量
            if (falg) {
                dy = (vheight - dheight * scale) * imgDeltaS;
            } else if (values[Matrix.MTRANS_Y] >= -imgDeltaY) {
                dy = values[Matrix.MTRANS_Y];
            }

            matrix.setScale(scale, scale);
            matrix.postTranslate(0, dy);

            setImageMatrix(matrix);
        }
    }

    /**
     * 计算边界
     *
     * @param values matrix数组
     * @param dy     偏移大小
     * @return
     */
    public float checkDyBound(float[] values, float dy) {
        if (imgDeltaY > 0) {
            if (values[Matrix.MTRANS_Y] + dy <= 0 && values[Matrix.MTRANS_Y] + dy >= -imgDeltaY) {
                return dy;
            }
        }
        return 0;
    }
}
