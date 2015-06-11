package io.gitcafe.zhanjiashu.newzhihudialy.util;

import android.content.Context;

import io.gitcafe.zhanjiashu.newzhihudialy.app.App;

/**
 * Created by Jiashu on 2015/6/10.
 */
public class DisplayUtils {

    public static final int STATUS_BAR_HEIGHT = getStatusBarHeight(App.getContext());

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
