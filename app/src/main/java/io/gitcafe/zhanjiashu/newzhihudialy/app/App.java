package io.gitcafe.zhanjiashu.newzhihudialy.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class App extends Application {

    private static final String TAG = "AppApplication";
    private static Context mContext;
    private static ConnectivityManager mConnectivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        initImageLoader();
        LogUtil.d(TAG, "App reset");
    }

    public static Context getContext() {
        return mContext;
    }

    private void initImageLoader() {

        File cacheDir = StorageUtils.getIndividualCacheDirectory(this);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static boolean checkNetwork() {
        if (mConnectivityManager.getActiveNetworkInfo() != null
                && mConnectivityManager.getActiveNetworkInfo().isAvailable()) {
            return true;
        }
        return false;
    }

    public static String getVersionName() {
        PackageManager packageManager = getContext().getPackageManager();

        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0";
        }
    }
}
