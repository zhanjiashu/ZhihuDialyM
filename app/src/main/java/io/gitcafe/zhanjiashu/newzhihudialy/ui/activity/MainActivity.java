package io.gitcafe.zhanjiashu.newzhihudialy.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.NavListAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeListEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchThemesTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.ui.fragment.HomeFragment;
import io.gitcafe.zhanjiashu.newzhihudialy.ui.fragment.ThemeFragment;
import io.gitcafe.zhanjiashu.newzhihudialy.util.DisplayUtils;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";

    @InjectView(R.id.dl_drawer)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.fl_container)
    FrameLayout mContainerLayout;

    @InjectView(R.id.id_lv_left_menu)
    ListView mNavListView;

    private NavListAdapter mNavListAdapter;

    private List<ThemeEntity> mThemeEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        mNavListAdapter = new NavListAdapter(MainActivity.this, mThemeEntities);

        FetchThemesTask task = new FetchThemesTask(this, true);
        task.execute(new FetchTask.FetchCallback<ThemeListEntity>() {
            @Override
            public void onFetchResponse(ThemeListEntity themeListEntity) {
                mThemeEntities = themeListEntity.getOthers();
                mNavListAdapter.replaceAll(mThemeEntities);
            }
        });
        
        setUpNavMenu();
    }

    private void setUpNavMenu()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        mNavListView.addHeaderView(inflater.inflate(R.layout.header_nav_user, mNavListView, false));
        mNavListView.addHeaderView(inflater.inflate(R.layout.header_nav_home, mNavListView, false));
        mNavListView.setAdapter(mNavListAdapter);
        mNavListView.setItemChecked(1, true);
        mNavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mDrawerLayout.closeDrawers();

                Fragment fragment = null;
                if (1 == position) {
                    fragment = new HomeFragment();
                } else if (position >= 2) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(ThemeFragment.KEY_THEME_ID, mThemeEntities.get(position - 2).getId());

                    fragment = new ThemeFragment();
                    fragment.setArguments(arguments);
                }
                replaceFragment(fragment);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }

        if (mNavListView.getSelectedItemPosition() != 1) {
            replaceFragment(new HomeFragment());
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时清除所有的WebView缓存
        new WebView(MainActivity.this).clearCache(true);
    }

    private void replaceFragment(Fragment fragment) {
        if (findViewById(R.id.fl_container) != null && fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
        }
    }
}
