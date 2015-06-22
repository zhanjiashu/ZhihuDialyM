package io.gitcafe.zhanjiashu.newzhihudialy.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.fragment.DetailFragment;

public class DetailActivity extends BaseActivity {

    private static final String EXTRA_STORY_ID = "story_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int storyId = getIntent().getIntExtra(EXTRA_STORY_ID, 0);

        if (savedInstanceState == null) {
            replaceFragment(R.id.fl_container, DetailFragment.newInstance(storyId));
        }
    }

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
