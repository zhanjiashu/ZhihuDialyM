package io.gitcafe.zhanjiashu.newzhihudialy.other;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jiashu on 2015/6/24.
 */
public class JavaScriptInterface {
    private Context mContext;

    public JavaScriptInterface(Context context) {
        mContext = context;
    }

    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void show() {
        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
    }
}
