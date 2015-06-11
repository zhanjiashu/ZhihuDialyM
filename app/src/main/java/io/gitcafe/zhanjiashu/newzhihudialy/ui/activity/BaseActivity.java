package io.gitcafe.zhanjiashu.newzhihudialy.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.util.DisplayUtils;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;
import io.gitcafe.zhanjiashu.newzhihudialy.util.VolleyUtils;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class BaseActivity extends AppCompatActivity {

    EventBus mEventBus;
    VolleyUtils mVolleyUtils;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = EventBus.getDefault();
        mVolleyUtils = VolleyUtils.getInstance(this);
        mFragmentManager = getSupportFragmentManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            View view = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    DisplayUtils.STATUS_BAR_HEIGHT);
            view.setBackgroundColor(getResources().getColor(R.color.material_colorPrimaryDark));
            view.setLayoutParams(params);

            ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
            rootView.addView(view);

            if(!(this instanceof DetailActivity)) {
                View contentView = rootView.findViewById(android.R.id.content);
                contentView.setPadding(0, DisplayUtils.STATUS_BAR_HEIGHT, 0, 0);
            }
        }
    }
}
