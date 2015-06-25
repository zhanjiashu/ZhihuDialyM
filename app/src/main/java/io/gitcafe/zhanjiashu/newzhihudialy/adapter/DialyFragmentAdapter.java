package io.gitcafe.zhanjiashu.newzhihudialy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class DialyFragmentAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> mFragmentList;
    private String[] mTitles;

    private FragmentManager mFm;

    public DialyFragmentAdapter(FragmentManager fm, List<T> fragments, String[] titles) {
        super(fm);
        mFragmentList = fragments != null ? fragments : new ArrayList<T>();
        mTitles = titles;
        mFm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= mTitles.length) {
            return "Default";
        }
        return mTitles[position];
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        mFm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        Fragment fragment = mFragmentList.get(position);
        mFm.beginTransaction().hide(fragment).commit();
    }
}
