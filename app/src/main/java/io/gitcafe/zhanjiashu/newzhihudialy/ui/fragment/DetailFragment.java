package io.gitcafe.zhanjiashu.newzhihudialy.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.AvatarsRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.common.ZHLinearLayout;
import io.gitcafe.zhanjiashu.newzhihudialy.model.MemberEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryDetailEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchDetailTask;
import io.gitcafe.zhanjiashu.newzhihudialy.task.FetchTask;
import io.gitcafe.zhanjiashu.newzhihudialy.util.DisplayUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static final String KEY_STORY_ID = "story_id";

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mToolbarLayout;

    @InjectView(R.id.tool_bar)
    Toolbar mToolbar;

    @InjectView(R.id.iv_top)
    ImageView mTopView;

    @InjectView(R.id.nsv_scroll)
    NestedScrollView mScrollView;

    @InjectView(R.id.ll_editors)
    ZHLinearLayout mRecommendersLayout;

    @InjectView(R.id.rcv_recommenders)
    RecyclerView mRecyclerView;

    @InjectView(R.id.wv_content)
    WebView mWebView;

    @InjectView(R.id.fab_share)
    FloatingActionButton mShareBtn;

    private DisplayImageOptions mImageOptions;

    private TypedArray mActionbarSizeTypedArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, view);

        setupView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        Bundle arguments = getArguments();
        if (arguments != null) {
            int storyId = arguments.getInt(KEY_STORY_ID);
            FetchDetailTask task= new FetchDetailTask(getActivity(), storyId, false);
            task.execute(new FetchTask.FetchCallback<StoryDetailEntity>() {
                @Override
                public void onFetchResponse(StoryDetailEntity storyDetailEntity) {
                    showStoryDetail(storyDetailEntity);
                }
            });
        }

        mActionbarSizeTypedArray.recycle();
    }

    private void setupView() {

        final AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
/*            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

            ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            params.gravity = GravityCompat.END;
            View actionBarBtns = LayoutInflater.from(getActivity()).inflate(R.layout.menu_detail_layout,
                    null);
            actionBar.setCustomView(actionBarBtns, params);*/
        }

        intiNestedScrollView();
    }

    /**
     * 在布局文件中如果对 NestedScrollView 设置了
     *      app:layout_behavior="@string/appbar_scrolling_view_behavior" ，
     * 将会导致作为其 子View 的 WebView 无法正常显示。这个可能是一个 Bug, 通过本方法可以解决。
     */
    private void intiNestedScrollView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int statusHeight = DisplayUtils.STATUS_BAR_HEIGHT;

        mActionbarSizeTypedArray = getActivity().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarHeight = (int) mActionbarSizeTypedArray.getDimension(0, 0);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mScrollView.getLayoutParams();
        params.height = metrics.heightPixels - statusHeight - actionBarHeight;
        mScrollView.setLayoutParams(params);
    }

    private void showStoryDetail(final StoryDetailEntity entity) {

        mToolbarLayout.setTitle(entity.getTitle());

        ImageLoader.getInstance().displayImage(entity.getImage(), mTopView, mImageOptions);


        List<MemberEntity> recommenderEntities = entity.getRecommenders();
        if (recommenderEntities != null && recommenderEntities.size() > 0) {

            List<String> avatarList = new ArrayList<>();
            for (MemberEntity recommenderEntity : recommenderEntities) {
                avatarList.add(recommenderEntity.getAvatar());
            }

            if (avatarList.size() > 0) {
                mRecommendersLayout.setVisibility(View.VISIBLE);
                mRecommendersLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "HH", Toast.LENGTH_SHORT).show();
                    }
                });

                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(avatarList.size(), StaggeredGridLayoutManager.VERTICAL));
                AvatarsRcvAdapter adapter = new AvatarsRcvAdapter(getActivity(), avatarList);
                mRecyclerView.setAdapter(adapter);
            }
        }

        String html = formatHtml(entity.getCss(), entity.getBody());

        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entity != null) {
                    shareText(getActivity(), entity.getTitle() + " " + entity.getShare_url());
                }
            }
        });
    }

    private String formatHtml(List<String> cssList, String body) {
        String cssLink = formatCssLink(cssList);

        Document document = Jsoup.parse(body);
        document.select("div[class=headline]").remove();

        body = document.select("body").html();

        String temp = "<!doctype Html>\n" +
                "<html>\n" +
                "<head>\n" +
                "{0}\n" +
                "</head>\n" +
                "<body>\n" +
                "{1}\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        return MessageFormat.format(temp, cssLink, body);

    }

    private String formatCssLink(List<String> cssList) {

        if (cssList != null && cssList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String cssUrl : cssList) {
                if (cssUrl.equals("http://news.at.zhihu.com/css/news_qa.auto.css?v=1edab")) {
                    cssUrl = "file:///android_asset/news_qa.auto.css";
                }
                String cssLink = "<link rel=\"stylesheet\" type=\"text/css\" href=\""+ cssUrl +"\">";
                stringBuilder.append(cssLink);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareText(Activity activity, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                shareText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }
}
