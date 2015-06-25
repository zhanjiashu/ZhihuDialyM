package io.gitcafe.zhanjiashu.newzhihudialy.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.MenuListAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeListEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchThemesTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.fragment.HomeFragment;
import io.gitcafe.zhanjiashu.newzhihudialy.fragment.ThemeFragment;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";

    @InjectView(R.id.dl_drawer)
    DrawerLayout mDrawerLayout;

    @InjectView(R.id.lv_left_nav)
    ListView mLeftNavListView;

    private List<ThemeEntity> mThemeEntities;
    private MenuListAdapter mAdapter;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setLeftNav();
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }
    }

    private void setLeftNav() {
        mAdapter = new MenuListAdapter(MainActivity.this, mThemeEntities);
        mLeftNavListView.setAdapter(mAdapter);
        mLeftNavListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.header_nav_user, mLeftNavListView, false));
        mLeftNavListView.setItemChecked(1, true);
        mLeftNavListView.setSelector(R.drawable.bg_nav_list_item);
        mLeftNavListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    return;
                }

                int newPosition = position - 1;
                Fragment fragment;
                if (newPosition == 0) {
                    fragment = new HomeFragment();
                } else {
                    fragment = ThemeFragment.newInstance(mAdapter.getItem(newPosition).getId());
                }
                replaceFragment(fragment);
                mDrawerLayout.closeDrawers();

            }
        });

        FetchThemesTask task = new FetchThemesTask(this, true);
        task.execute(new FetchTask.FetchCallback<ThemeListEntity>() {
            @Override
            public void onFetchResponse(ThemeListEntity themeListEntity) {
                mThemeEntities = themeListEntity.getOthers();
                ThemeEntity entity = new ThemeEntity();
                entity.setName("首页");
                entity.setThumbnail("assets://menu_home.png");
                mThemeEntities.add(0, entity);
                mAdapter.replaceAll(mThemeEntities);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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

        if (mCurrentFragment instanceof ThemeFragment) {
            mLeftNavListView.setItemChecked(1, true);
            replaceFragment(new HomeFragment());
            return;
        }

        if (mCurrentFragment instanceof HomeFragment) {
            Snackbar
                    .make(findViewById(android.R.id.content), "是否退出程序？", Snackbar.LENGTH_LONG)
                    .setAction("退出", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivity.this.finish();
                        }
                    })
                    .setActionTextColor(ColorStateList.valueOf(getResources().getColor(R.color.material_colorPrimary)))
                    .show();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时清除所有的WebView缓存
        //new WebView(MainActivity.this).clearCache(true);
    }

    private void replaceFragment(Fragment fragment) {
        if (findViewById(R.id.fl_container) != null && fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            mCurrentFragment = fragment;
        }
    }
}
