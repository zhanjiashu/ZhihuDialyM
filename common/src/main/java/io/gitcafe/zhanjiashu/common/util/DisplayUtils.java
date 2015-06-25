package io.gitcafe.zhanjiashu.common.util;

import android.content.Context;

/**
 * Created by Jiashu on 2015/6/10.
 */
public class DisplayUtils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
