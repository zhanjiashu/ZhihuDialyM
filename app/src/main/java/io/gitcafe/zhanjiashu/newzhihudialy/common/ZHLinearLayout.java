package io.gitcafe.zhanjiashu.newzhihudialy.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Jiashu on 2015/6/5.
 * 中止事件向 子View 传递的 LinearLayout
 */
public class ZHLinearLayout extends LinearLayout {
    public ZHLinearLayout(Context context) {
        this(context, null);
    }

    public ZHLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZHLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
