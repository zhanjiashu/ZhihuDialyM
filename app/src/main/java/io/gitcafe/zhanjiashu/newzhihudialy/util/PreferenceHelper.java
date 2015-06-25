package io.gitcafe.zhanjiashu.newzhihudialy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

import io.gitcafe.zhanjiashu.newzhihudialy.R;

/**
 * Created by Jiashu on 2015/6/23.
 */
public class PreferenceHelper {
    private static PreferenceHelper sPreferenceHelper;

    private Context mAppContext;
    private SharedPreferences mPreferences;

    private PreferenceHelper(Context context) {
        mAppContext = context.getApplicationContext();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

    public static PreferenceHelper getInstance(Context context) {
        if (sPreferenceHelper == null) {
            synchronized (PreferenceHelper.class) {
                if (sPreferenceHelper == null) {
                    sPreferenceHelper = new PreferenceHelper(context);
                }
            }
        }
        return sPreferenceHelper;
    }

    public boolean isNoPictureEnabled() {
        String key = mAppContext.getResources().getString(R.string.pref_key_no_picture_mode);
        return mPreferences.getBoolean(key, false);
    }
}
