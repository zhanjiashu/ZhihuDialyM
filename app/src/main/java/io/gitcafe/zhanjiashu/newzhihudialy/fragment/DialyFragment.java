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

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import io.gitcafe.zhanjiashu.common.BaseRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.StoriesAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchDialyTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchLatestDialyTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.activity.DetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialyFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static final String KEY_BEFORE_DATE = "beforeDate";
    public static final String KEY_DATE = "date";

    @InjectView(R.id.rcv_stories)
    RecyclerView mStoriesRcv;

    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private StoriesAdapter mStoriesAdapter;

    private String mBeforeDate;

    public static DialyFragment newInstance(String date) {

        if (date == null) {
            throw new IllegalArgumentException("The args value can not be null");
        }
        Bundle args = new Bundle();
        args.putString(KEY_BEFORE_DATE, date);
        DialyFragment fragment = new DialyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        if (args != null) {
            mBeforeDate = args.getString(KEY_BEFORE_DATE, "");
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
                fetchDialy(true);
            }
        });


        mStoriesAdapter = new StoriesAdapter(getActivity(), null);

        mStoriesRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoriesRcv.setItemAnimator(new DefaultItemAnimator());

        mStoriesRcv.setAdapter(mStoriesAdapter);

        mStoriesAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener<StoryEntity>() {
            @Override
            public void onItemClick(View view, int position, StoryEntity entity) {
                int storyId = entity.getId();
                DetailActivity.startBy(getActivity(), storyId);
            }
        });

        fetchDialy(false);
    }

    private void fetchDialy(boolean networkFirst) {
        FetchTask<DialyEntity> task;
        if (mBeforeDate != null) {
            task = new FetchDialyTask(getActivity(), mBeforeDate, networkFirst);
        } else {
            task = new FetchLatestDialyTask(getActivity());
        }
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
