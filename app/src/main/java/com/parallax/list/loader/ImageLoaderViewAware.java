package com.parallax.list.loader;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created by jiangyue on 16/4/28.
 */
public class ImageLoaderViewAware extends ImageViewAware {
    public ImageLoaderViewAware(ImageView imageView) {
        super(imageView);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
