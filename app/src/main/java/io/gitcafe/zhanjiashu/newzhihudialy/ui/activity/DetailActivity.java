package io.gitcafe.zhanjiashu.newzhihudialy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.ui.fragment.DetailFragment;

public class DetailActivity extends BaseActivity {

    private static final String EXTRA_STORY_ID = "story_id";

/*    @InjectView(R.id.status_bar)
    View mStatusBar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.inject(this);

        //setupView();

        Intent intent = getIntent();
        int storyId = 0;
        if (intent != null) {
            storyId = intent.getIntExtra(EXTRA_STORY_ID, 0);
        }

        if (savedInstanceState == null && storyId != 0) {
            Fragment fragment = new DetailFragment();

            Bundle arguments = new Bundle();
            arguments.putInt(DetailFragment.KEY_STORY_ID, storyId);
            fragment.setArguments(arguments);

            replaceFragment(R.id.fl_container, fragment);
        }
    }

/*    private void setupView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.post(new Runnable() {
                @Override
                public void run() {
                    Rect rect = new Rect();
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mStatusBar.getLayoutParams();
                    params.height = rect.top;
                }
            });
        }
    }*/

    public static void startBy(Context context, int param) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_STORY_ID, param);
        context.startActivity(intent);
    }

    public void replaceFragment(@IdRes int containerViewId, Fragment fragment) {
        if (findViewById(containerViewId) != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment)
                    .commit();
        }
    }
}
