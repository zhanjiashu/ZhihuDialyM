package io.gitcafe.zhanjiashu.newzhihudialy.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Jiashu on 2015/6/24.
 */
public class NetworkHelper {

    private static ConnectivityManager sConnectivityManager;

    private static NetworkHelper sNetworkHelper;

    private NetworkHelper(Context context) {
        sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkHelper getInstance(Context context) {
        if (sNetworkHelper == null) {
            synchronized (NetworkHelper.class) {
                if (sNetworkHelper == null) {
                    sNetworkHelper = new NetworkHelper(context);
                }
            }
        }
        return sNetworkHelper;
    }

    public boolean isNetworkAvailable() {
        if (sConnectivityManager.getActiveNetworkInfo() != null
                && sConnectivityManager.getActiveNetworkInfo().isAvailable()) {
            return true;
        }
        return false;
    }

    public int getNetworkType() {
        return sConnectivityManager.getActiveNetworkInfo().getType();
    }

    public boolean isMobieNetwork() {
        if (isNetworkAvailable()
                && ConnectivityManager.TYPE_MOBILE == getNetworkType()) {
            return true;
        }
        return false;
    }
}
