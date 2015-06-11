package io.gitcafe.zhanjiashu.newzhihudialy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import io.gitcafe.zhanjiashu.newzhihudialy.app.App;
import io.gitcafe.zhanjiashu.newzhihudialy.cache.VolleyLruImageCache;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class VolleyUtils {

    private static VolleyUtils mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private VolleyUtils(Context context) {
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new VolleyLruImageCache(context));
    }


    public static VolleyUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleyUtils.class) {
                if (mInstance == null) {
                    mInstance = new VolleyUtils(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(App.getContext());
        }
        return mRequestQueue;
    }

    public <T> void addRequest(Request<T> request) {
        mRequestQueue.add(request);
    }
}
