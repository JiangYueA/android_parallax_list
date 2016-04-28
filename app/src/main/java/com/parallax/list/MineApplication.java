package com.parallax.list;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by jiangyue on 16/4/28.
 */
public class MineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommonConstant.MOBSCREENWIDTH = getWidthMin(this);
        CommonConstant.MOBSCREENHEIGHT = getHeightMin(this);
        CommonConstant.HOMEITEMLITECELLHEIGHT = getResources().getDimensionPixelSize(R.dimen.cell_height) + getResources().getDimensionPixelSize(R.dimen.cell_block_height);

        initImageLoader();
    }

    /* 初始化图片缓存 */
    public void initImageLoader() {
        //加载Universal Image Loader图片缓存
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(R.color.colorGray)//设置图片在下载期间显示的图片
                .resetViewBeforeLoading(false)
                .cacheInMemory(true)//是否緩存都內存中
                .cacheOnDisk(true)//是否緩存到sd卡上
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        //缓存目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "mine");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .memoryCacheExtraOptions(CommonConstant.MOBSCREENWIDTH, CommonConstant.MOBSCREENHEIGHT)
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)//缓存的文件数量
                .memoryCache(new FIFOLimitedMemoryCache(30 * 1024 * 1024))
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().handleSlowNetwork(true);
    }

    /**
     * 获取屏宽
     *
     * @param context 上下文
     */
    public int getWidthMin(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return (dm.widthPixels <= dm.heightPixels) ? dm.widthPixels : dm.heightPixels;
    }

    /**
     * 获取屏高
     *
     * @param context 上下文
     */
    public int getHeightMin(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return (dm.widthPixels >= dm.heightPixels) ? dm.widthPixels : dm.heightPixels;
    }
}
