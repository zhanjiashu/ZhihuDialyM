package io.gitcafe.zhanjiashu.newzhihudialy.ui.fragment;


import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.StoriesRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchDialyTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchLatestDialyTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.ui.activity.DetailActivity;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialyFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static final String KEY_BEFORE_DATE = "beforeDate";

    @InjectView(R.id.rcv_stories)
    RecyclerView mStoriesRcv;

    @InjectView(R.id.srl_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private StoriesRcvAdapter mRcvAdapter;

    private String mBeforeDate;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle args = getArguments();
        if (args != null) {
            mBeforeDate = args.getString(KEY_BEFORE_DATE, "");
        }

        LogUtil.d(TAG, mBeforeDate + " onAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG, mBeforeDate + " onDestroyView");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialy, container, false);
        ButterKnife.inject(this, view);
        setupView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupView() {

        mRefreshLayout.setColorSchemeResources(R.color.material_colorAccent);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDialy(true);
            }
        });

        mRcvAdapter = new StoriesRcvAdapter(getActivity(), null);

        mStoriesRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoriesRcv.setAdapter(mRcvAdapter);

        mRcvAdapter.setOnItemClickListener(new StoriesRcvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                int storyId = mRcvAdapter.getData().get(postion).getId();
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

                List<StoryEntity> entities = dialyEntity.getStories();
                if (entities != null && mStoriesRcv != null && mRcvAdapter != null) {
                    mRcvAdapter.replace(entities);
                }
            }
        });
    }

}
