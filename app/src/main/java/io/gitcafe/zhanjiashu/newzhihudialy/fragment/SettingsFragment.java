package io.gitcafe.zhanjiashu.newzhihudialy.fragment;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;

import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.app.App;
import io.gitcafe.zhanjiashu.newzhihudialy.util.ZHStorageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private final String TAG = getClass().getSimpleName();

    public static final String CLEAR_CACHE = "clear_cache";
    public static final String ABOUT_APP = "about_app";
    public static final String APP_VERSION = "app_version";
    public static final String ENABLE_SISTER = "enable_sister";
    public static final String ENABLE_FRESH_BIG = "enable_fresh_big";

    Preference mClearCachePref;
    Preference mAppVersionPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mClearCachePref = findPreference(CLEAR_CACHE);
        mAppVersionPref = findPreference(APP_VERSION);

        mAppVersionPref.setSummary("当前版本号：" + App.getVersionName());

        double cacheSize = calculateCacheSize();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        mClearCachePref.setSummary("缓存大小：" + decimalFormat.format(cacheSize) + " M");
        mClearCachePref.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (CLEAR_CACHE.equals(key)) {
            Snackbar.make(getView(), "确定要清除缓存？", Snackbar.LENGTH_LONG)
                    .setActionTextColor(ColorStateList.valueOf(getResources().getColor(R.color.material_colorPrimary)))
                    .setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clearCache();
                        }
                    })
                    .show();
        }
        return true;
    }

    private double calculateCacheSize() {
        File uilCacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
        return ZHStorageUtils.getDirSize(uilCacheFile) +
                ZHStorageUtils.getFilesDiskCache(getActivity()).size() / (1024 * 1024);
    }

    private void clearCache() {
        try {
            ImageLoader.getInstance().getDiskCache().clear();
            ZHStorageUtils.getFilesDiskCache(getActivity()).delete();
            new WebView(getActivity()).clearCache(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (calculateCacheSize() == 0.0) {
            Snackbar.make(getView(), "缓存清理完成", Snackbar.LENGTH_SHORT).show();
        }
        mClearCachePref.setSummary("缓存大小：0.00 M");
    }
}
