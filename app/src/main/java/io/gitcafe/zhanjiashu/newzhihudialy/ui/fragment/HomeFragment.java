package io.gitcafe.zhanjiashu.newzhihudialy.ui.fragment;


import android.content.Context;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.DialyFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int DIALY_COUNT = 7;
    private static final long MILLISECOND_ONE_DAY = 1000 * 60 * 60 * 24;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();

        appCompatActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }

        setupViewPager();

        return view;
    }


    private void setupViewPager() {

        mFragmentList = new ArrayList<>();
        mTabDates[0] = "今日热闻";

        SimpleDateFormat formatZN = new SimpleDateFormat("MM月dd日·E", Locale.CHINA);
        SimpleDateFormat formatSimple = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < mTabDates.length; i++) {

            DialyFragment fragment = new DialyFragment();

            if (i > 0) {
                Date date = new Date(calendar.getTimeInMillis() - MILLISECOND_ONE_DAY * i);
                Date beforeDate = new Date(calendar.getTimeInMillis() - MILLISECOND_ONE_DAY * (i - 1));

                mTabDates[i] = formatZN.format(date);

                Bundle args = new Bundle();
                args.putString(DialyFragment.KEY_BEFORE_DATE, formatSimple.format(beforeDate));
                fragment.setArguments(args);
            }

            mFragmentList.add(fragment);
        }

        mPagerAdapter = new DialyFragmentAdapter<DialyFragment>(
                getActivity().getSupportFragmentManager(),
                mFragmentList,
                mTabDates);

        mViewPager.setAdapter(mPagerAdapter);

        //mViewPager.setOffscreenPageLimit(3);

        mTabLayout.setupWithViewPager(mViewPager);
    }

}
