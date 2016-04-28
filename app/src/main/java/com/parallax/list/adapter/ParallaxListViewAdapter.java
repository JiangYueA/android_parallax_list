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

    private String[] ress = {
            "http://yes-img.b0.upaiyun.com//user/155650/item/kr300jhkcincgl8cvxmfl1l7f92wcccs!640f",
            "http://yes-img.b0.upaiyun.com//user/155518/item/9lph3g29tlj5qwptg0xlzo9hccetnu5k!640f",
            "http://yes-img.b0.upaiyun.com//user/153721/item/881154cbd4kkihl5mi0ltasus8r01z7l!1280",
            "http://yes-img.b0.upaiyun.com//user/154378/item/cxrn43pm7brzu5b9z4a7i8ujhoy3nne5!1280",
            "http://yes-img.b0.upaiyun.com//user/155499/item/15a1184320054fabb95bb4e0c5f488f7!640f",
            "http://yes-img.b0.upaiyun.com//user/152733/item/tpb8l6baqjikv5yqob2pfwnjrbrxjjsv!640f",
            "http://yes-img.b0.upaiyun.com//user/151595/item/xmeehvzzyh3cx5307lz921rdvdj7gd51!1280",
            "http://yes-img.b0.upaiyun.com//user/154410/item/6w2h7ccq4gnr85jm6qdyxwevbo2ajgxt!640f",
            "http://yes-img.b0.upaiyun.com//user/152779/item/sob1rdlkla9kh9tnvj57y0bktrmf4e55!640f",
            "http://yes-img.b0.upaiyun.com//user/155105/item/xitjatdo3f8v8tdl55t6dexnjak9wjzb!640f"
    };

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
        if (holder.imgPhoto.getTag() == null || !holder.imgPhoto.getTag().equals(ress[pos])) {
            holder.imgPhoto.setTag(ress[pos]);
            ImageLoader.getInstance().displayImage(ress[pos], new ImageLoaderViewAware(holder.imgPhoto));
        }
    }

    class ViewHolder {
        private ParallaxImageView imgPhoto;     //加载图片
    }
}
