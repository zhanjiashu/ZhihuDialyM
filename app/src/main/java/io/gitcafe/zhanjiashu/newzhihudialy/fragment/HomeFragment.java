package io.gitcafe.zhanjiashu.newzhihudialy.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.activity.PickerActivity;
import io.gitcafe.zhanjiashu.newzhihudialy.activity.SettingsActivity;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.DialyFragmentAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyType;
import io.gitcafe.zhanjiashu.newzhihudialy.util.DateUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int DIALY_COUNT = 7;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.tl_tab)
    TabLayout mTabLayout;

    @InjectView(R.id.vp_pager)
    ViewPager mViewPager;

    private String[] mTabDates = new String[DIALY_COUNT];
    private List<DialyFragment> mFragmentList;

    private DialyFragmentAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        setupActionBar();
        setupViewPager();

        return view;
    }

    private void setupActionBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }
    }

    private void setupViewPager() {
        mFragmentList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日·E", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < mTabDates.length; i++) {
            DialyFragment fragment;
            Date date = DateUtil.getOffsetDate(calendar.getTimeInMillis(), -i);
            int dialyType;
            if (i == 0) {
                mTabDates[i] = "今日热闻";
                dialyType = DialyType.LATEST_DIALY;
            } else {
                mTabDates[i] = format.format(date);
                dialyType = DialyType.HOME_DIALY;
            }
            fragment = DialyFragment.newInstance(DateUtil.getBeforeDate(date), dialyType);
            mFragmentList.add(fragment);
        }

        mPagerAdapter = new DialyFragmentAdapter<>(
                getActivity().getSupportFragmentManager(),
                mFragmentList,
                mTabDates);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_picker) {
            pickDate();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 选择日期查看某天的日报
     */
    private void pickDate() {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder() {
            @Override
            public void onDateChanged(int oldDay, int oldMonth, int oldYear, int newDay, int newMonth, int newYear) {
                super.onDateChanged(oldDay, oldMonth, oldYear, newDay, newMonth, newYear);

                final DatePickerDialog dialog = (DatePickerDialog) getDialog();

                if (dialog.getDate() > System.currentTimeMillis()) {
                    Toast.makeText(getActivity(), "只能查看今天之前的日报", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String date = newYear + "年" + newMonth + "月" + newDay + "日";

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PickerActivity.startBy(getActivity(), date, dialog.getDate());
                        dialog.dismiss();
                    }
                }, 300);
            }
        };
        DialogFragment dialogFragment = DialogFragment.newInstance(builder);
        dialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }
}
