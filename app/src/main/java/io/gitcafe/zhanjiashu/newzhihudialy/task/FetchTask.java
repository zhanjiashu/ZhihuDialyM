package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.gitcafe.zhanjiashu.newzhihudialy.app.App;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;
import io.gitcafe.zhanjiashu.newzhihudialy.util.ZHStorageUtils;
import io.gitcafe.zhanjiashu.newzhihudialy.util.VolleyUtils;

/**
 * Created by Jiashu on 2015/6/2.
 */
public abstract class FetchTask<T> {
    private final String TAG = "FetchTask";
    private String mUrl;
    private boolean mFetchFromNetwork;

    protected VolleyUtils mVolleyUtils;

    protected DiskLruCache mDiskLruCache;

    protected String mCacheKey;

    protected Request mRequest;

    public FetchTask(Context context, String url, boolean fetchFromNetwork) {
        mUrl = url;
        mFetchFromNetwork = fetchFromNetwork;

        mVolleyUtils = VolleyUtils.getInstance(App.getContext());

        mDiskLruCache = ZHStorageUtils.getFilesDiskCache(context);

        mCacheKey = ZHStorageUtils.hashKeyForDisk(mUrl);
    }

    public void execute(FetchCallback<T> callback) {
        String cacheKey = ZHStorageUtils.hashKeyForDisk(mUrl);

        boolean hasCacheFile = ZHStorageUtils.hasCacheInDisk(mDiskLruCache, cacheKey);

        if (mFetchFromNetwork && App.checkNetwork() || !hasCacheFile) {
            fetchFromNetwork(mUrl, callback);
        } else {
            fetchFromeDiskCache(mCacheKey, callback);
        }
    }

    private void fetchFromNetwork(String url, final FetchCallback<T> callback) {
        mRequest = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cacheToDisk(response);
                        fetchFromeDiskCache(mCacheKey, callback);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );

        mVolleyUtils.addRequest(mRequest);
    }

    private void cacheToDisk(String response) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(mCacheKey);
            editor.set(0, response);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void fetchFromeDiskCache(String cacheKey, FetchCallback<T> callback) {

        String cacheStr = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(cacheKey);
            if (snapshot != null) {
                LogUtil.d("DetailFragment", cacheKey);
                InputStream in = snapshot.getInputStream(0);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                cacheStr = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(cacheStr)) {
            parseResponse(cacheStr, callback);
        }
    }

    protected abstract void parseResponse(String response, FetchCallback<T> callback);

    public interface FetchCallback<T> {
        void onFetchResponse(T t);
    }

    public void cancel() {
        if (mRequest != null) {
            mRequest.cancel();
        }
    }
}
