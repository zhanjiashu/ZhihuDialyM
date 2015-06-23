package io.gitcafe.zhanjiashu.newzhihudialy.fragment;


import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import io.gitcafe.zhanjiashu.common.adapter.BaseRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.StoriesAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyType;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchDialyTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchLatestDialyTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.activity.DetailActivity;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialyFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static final String KEY_BEFORE_DATE = "beforeDate";
    public static final String KEY_DIALY_TYPE = "dialyType";

    @InjectView(R.id.rcv_stories)
    RecyclerView mStoriesRcv;

    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private StoriesAdapter mStoriesAdapter;

    private String mBeforeDate;
    private int mDialyType;

    public static DialyFragment newInstance(String beforeDate, int dialyType) {
        if (beforeDate == null) {
            throw new IllegalArgumentException("The args value can not be null");
        }
        Bundle args = new Bundle();
        args.putString(KEY_BEFORE_DATE, beforeDate);
        args.putInt(KEY_DIALY_TYPE, dialyType);
        DialyFragment fragment = new DialyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        if (args != null) {
            mBeforeDate = args.getString(KEY_BEFORE_DATE);
            mDialyType = args.getInt(KEY_DIALY_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialy, container, false);
        ButterKnife.inject(this, view);
        setupView();
        return view;
    }

    private void setupView() {

        mRefreshLayout.setColorSchemeResources(R.color.material_colorAccent);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDialy(mBeforeDate, mDialyType, true);
            }
        });

        mStoriesRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoriesRcv.setItemAnimator(new DefaultItemAnimator());

        if (mDialyType == DialyType.PICK_DIALY) {
            mStoriesAdapter = new StoriesAdapter(getActivity(), new ArrayList<StoryEntity>(), false);
        } else {
            mStoriesAdapter = new StoriesAdapter(getActivity(), new ArrayList<StoryEntity>(), true);
        }

        mStoriesRcv.setAdapter(mStoriesAdapter);
        mStoriesAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener<StoryEntity>() {
            @Override
            public void onItemClick(View view, int position, StoryEntity entity) {
                int storyId = entity.getId();
                DetailActivity.startBy(getActivity(), storyId);
            }
        });

        fetchDialy(mBeforeDate, mDialyType, false);
    }

    private void fetchDialy(String beforeDate, int dialyType, boolean isRefresh) {
        boolean fetchFromNetworkFirst = false;
        boolean cacheOnDisk = true;
        if (isRefresh || dialyType == DialyType.LATEST_DIALY) {
            fetchFromNetworkFirst = true;
        }
        if (dialyType == DialyType.PICK_DIALY) {
            fetchFromNetworkFirst = true;
            cacheOnDisk = false;
        }

        FetchTask<DialyEntity> task;
        task = new FetchDialyTask(getActivity(), mBeforeDate, fetchFromNetworkFirst, cacheOnDisk);
        LogUtil.d(TAG, dialyType + " -> " + fetchFromNetworkFirst + " ->> " + cacheOnDisk);

        task.execute(new FetchTask.FetchCallback<DialyEntity>() {
            @Override
            public void onFetchResponse(DialyEntity dialyEntity) {

                if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }

                if (mStoriesAdapter != null) {
                    mStoriesAdapter.replace(dialyEntity.getStories());
                }
            }
        });
    }
}
