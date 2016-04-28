package com.parallax.list.adapter;

import android.content.Context;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parallax.list.CommonConstant;
import com.parallax.list.R;
import com.parallax.list.loader.ImageLoaderViewAware;
import com.parallax.list.widget.imageview.ParallaxImageView;

/**
 * Created by jiangyue on 16/4/28.
 */
public class ParallaxListViewAdapter extends BaseAdapter {

    public LayoutInflater inflater;

    /* variable */
    private Matrix matrix;

    public ParallaxListViewAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private int[] ress = {
            R.drawable.bg01,
            R.drawable.bg02,
            R.drawable.bg03,
            R.drawable.bg04,
            R.drawable.bg05,
            R.drawable.bg02,
            R.drawable.bg01,
            R.drawable.bg02,
            R.drawable.bg01,
            R.drawable.bg02,
            R.drawable.bg01};

    public void parallaxView(View view, float distance, int listViewHeight) {
        if (null != view.getTag()) {
            ParallaxImageView photo = ((ViewHolder) view.getTag()).imgPhoto;

            setParallax(photo, distance, listViewHeight);
        }
    }

    /* 图片偏移量计算 */
    public void setParallax(ParallaxImageView img, float distance, int listViewHeight) {
        if (null == matrix) {
            matrix = new Matrix();
        }
        float[] values = new float[9];
        matrix.set(img.getImageMatrix());
        matrix.mapPoints(values);
        // 父视图的高度
        int viewHeight = listViewHeight + CommonConstant.HOMEITEMLITECELLHEIGHT;
        // 照片的高度 - Item的高度
        float difference = img.imgDeltaY;
        // 图片初始偏移设置
        img.imgDeltaS = distance >= 0 ? 1.0f : 0.0f;
        // 计算偏移量
        float move = (distance / viewHeight) * difference;
        matrix.postTranslate(0, img.checkDyBound(values, move));
        img.setImageMatrix(matrix);
    }

    @Override
    public int getCount() {
        return ress.length;
    }

    @Override
    public Object getItem(int position) {
        return ress[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cell_parallax_list_view, null);
            holder = initViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setHolderValue(holder, position);

        return convertView;
    }

    private ViewHolder initViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.imgPhoto = (ParallaxImageView) view.findViewById(R.id.pv_img_photo);
        return holder;
    }

    private void setHolderValue(ViewHolder holder, int pos) {
        if (holder.imgPhoto.getTag() == null) {
            holder.imgPhoto.setTag(ress[pos] + "img");
            ImageLoader.getInstance().displayImage("drawable://" + ress[pos], new ImageLoaderViewAware(holder.imgPhoto));
        }
    }

    class ViewHolder {
        private ParallaxImageView imgPhoto;     //加载图片
    }
}
