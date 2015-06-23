package io.gitcafe.zhanjiashu.newzhihudialy.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.common.adapter.BaseRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.StoriesAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeDetailEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchOldStoryTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchThemeDetailTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.activity.DetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemeFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static final String KEY_THEME_ID = "theme_id";

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mToolbarLayout;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.iv_top)
    ImageView mTopView;

    @InjectView(R.id.rcv_stories)
    RecyclerView mStoriesRcv;

    private DisplayImageOptions mImageOptions;

    private LinearLayoutManager mRcvLayoutManager;

    private StoriesAdapter mStoriesAdapter;

    private int mThemeId;

    public static ThemeFragment newInstance(int themeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_THEME_ID, themeId);
        ThemeFragment fragment = new ThemeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        ButterKnife.inject(this, view);

        initData();

        setupView();

        fetchStories(true);

        return view;
    }

    private void initData() {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mThemeId = arguments.getInt(KEY_THEME_ID);
        }

        mImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    private void setupView() {

        final AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_menu);
        }

        mStoriesAdapter = new StoriesAdapter(getActivity(), null);
        mRcvLayoutManager = new LinearLayoutManager(getActivity());

        mStoriesRcv.setAdapter(mStoriesAdapter);
        mStoriesRcv.setLayoutManager(mRcvLayoutManager);
        mStoriesRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mRcvLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem + 1 == mStoriesAdapter.getItemCount()) {
                    loadStories();
                }
            }
        });

        mStoriesAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener<StoryEntity>() {
            @Override
            public void onItemClick(View view, int position, StoryEntity entity) {
                DetailActivity.startBy(getActivity(), entity.getId());
            }
        });
    }

    private void setupThemeDetail(ThemeDetailEntity entity) {
        mToolbarLayout.setTitle(entity.getName());
        ImageLoader.getInstance().displayImage(entity.getImage(), mTopView, mImageOptions);

        mStoriesAdapter.replace(entity.getStories());
    }

    private void loadStories() {

        FetchOldStoryTask task = new FetchOldStoryTask(
                getActivity(),
                mThemeId,
                mStoriesAdapter.getLastItem().getId(),
                true);
        task.execute(new FetchTask.FetchCallback<DialyEntity>() {
            @Override
            public void onFetchResponse(DialyEntity dialyEntity) {
                mStoriesAdapter.addAll(dialyEntity.getStories());
            }
        });
    }

    private void fetchStories(boolean cacheFirst) {

        FetchThemeDetailTask task = new FetchThemeDetailTask(getActivity(), mThemeId, cacheFirst);
        task.execute(new FetchTask.FetchCallback<ThemeDetailEntity>() {
            @Override
            public void onFetchResponse(ThemeDetailEntity entity) {
                setupThemeDetail(entity);
            }
        });
    }

}
