package io.gitcafe.zhanjiashu.newzhihudialy.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.jakewharton.disklrucache.DiskLruCache;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.gitcafe.zhanjiashu.newzhihudialy.app.App;

/**
 * Created by Jiashu on 2015/6/3.
 */
public class ZHStorageUtils {

    private static DiskLruCache mFilesDiskCache;

    public static File getDiskCacheDir(Context context, String dir) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return new File(getExternalCacheDir(context), dir);
        } else {
            return new File(context.getCacheDir(), dir);
        }
    }

    public static DiskLruCache getFilesDiskCache(Context context) {
        if (mFilesDiskCache == null || mFilesDiskCache.isClosed()) {
            try {
                File cacheDir = getDiskCacheDir(context, "files");
                if (!cacheDir.exists()) {
                    cacheDir.mkdirs();
                }
                mFilesDiskCache = DiskLruCache.open(cacheDir, App.getAppVersion(context), 1, 30 * 1024 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mFilesDiskCache;
    }

    public static boolean hasCacheInDisk(DiskLruCache diskLruCache, String key) {
        if (diskLruCache != null && !diskLruCache.isClosed() && !TextUtils.isEmpty(key)) {
            try {
                DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
                if (snapshot != null) {
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    private static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                L.w("Unable to create external cache directory");
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                L.i("Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static double getDirSize(File file) {
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children) {
                    size += getDirSize(f);
                }
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                return (double) file.length() / 1024 / 1024;
            }
        } else {
            return 0.0;
        }
    }
}
