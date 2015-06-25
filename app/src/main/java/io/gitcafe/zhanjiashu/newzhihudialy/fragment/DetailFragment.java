package io.gitcafe.zhanjiashu.newzhihudialy.fragment;


import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.adapter.AvatarsAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.other.JavaScriptInterface;
import io.gitcafe.zhanjiashu.newzhihudialy.util.LogUtil;
import io.gitcafe.zhanjiashu.newzhihudialy.util.NetworkHelper;
import io.gitcafe.zhanjiashu.newzhihudialy.util.PreferenceHelper;
import io.gitcafe.zhanjiashu.newzhihudialy.widget.ZHLinearLayout;
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

    private static final String WEBVIEW_DEFAULT_IMG = "file:///android_asset/default_pic_content_image_download_light.png";

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mToolbarLayout;

    @InjectView(R.id.toolbar)
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

    private boolean mIsNoPictureMode;

    public static DetailFragment newInstance(int storyId) {
        Bundle args = new Bundle();
        args.putInt(KEY_STORY_ID, storyId);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        PreferenceHelper helper = PreferenceHelper.getInstance(getActivity());
        mIsNoPictureMode = helper.isNoPictureEnabled() && NetworkHelper.getInstance(getActivity()).isMobieNetwork();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, view);

        setupView();

        return view;
    }

    private void setupView() {

        final AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intiNestedScrollView();

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
        if (!mIsNoPictureMode) {
            ImageLoader.getInstance().displayImage(entity.getImage(), mTopView, mImageOptions);
        }
        List<MemberEntity> recommenderEntities = entity.getRecommenders();
        if (recommenderEntities != null && recommenderEntities.size() > 0) {
            List<String> avatarList = new ArrayList<>();
            for (MemberEntity recommenderEntity : recommenderEntities) {
                avatarList.add(recommenderEntity.getAvatar());
            }
            if (avatarList.size() > 0) {
                mRecommendersLayout.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView.setLayoutManager(manager);
                AvatarsAdapter adapter = new AvatarsAdapter(getActivity(), avatarList, mIsNoPictureMode);
                mRecyclerView.setAdapter(adapter);
            }
        }

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        String contentBody = entity.getBody();
        if (!TextUtils.isEmpty(contentBody)) {
            String html = formatHtml(entity.getCss(), contentBody);
            mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        } else {
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.loadUrl(entity.getShare_url());
        }

        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(getActivity(), entity.getTitle() + " " + entity.getShare_url());
            }
        });
    }

    private String formatHtml(List<String> cssList, String body) {
        LogUtil.d(TAG, "内容长度" + body.length());
        LogUtil.d(TAG, "当前是否为无图模式和非Wifi网络：" + mIsNoPictureMode);
        String cssLink = formatCssLink(cssList);
        Document document = Jsoup.parse(body);
        document.select("div[class=headline]").remove();
        if (mIsNoPictureMode) {
            Elements imgElts = document.select("img");
            for (Element element : imgElts) {
                String imgUrl = element.attr("src");
                element.attr("src", WEBVIEW_DEFAULT_IMG);
                element.attr("origin-src", imgUrl);
            }
        }
        body = document.html();
        String template = "<!doctype Html>\n" +
                "<html>\n" +
                "<head>\n" +
                "{0}\n" +
                "</head>\n" +
                "<body>\n" +
                "{1}\n" +
                "{2}\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        String js = "<script type=\"text/javascript\">\n" +
                "function downloadImg (imgElt) {\n" +
                "\tvar imgSrc = imgElt.attributes[\"src\"].value;\n" +
                "\tvar originImgSrc = imgElt.attributes[\"origin-src\"].value;\n" +
                "\timgElt.src = originImgSrc;\n" +
                "}\n" +
                "x=document.getElementsByTagName(\"img\");\n" +
                "for (i=0;i<x.length;i++){\n" +
                "\tx[i].onclick = function () {\n" +
                "\t\tdownloadImg(this);\n" +
                "\t};\n" +
                "}\n" +
                "</script>";

        return MessageFormat.format(template, cssLink, body, js);

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
            return true;
        }

        if (id == R.id.action_star) {
            shareText(getActivity(), "");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.clearCache(true);
    }

    private void shareText(Activity activity, String shareText) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                shareText);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activity.getResources().getString(R
                .string.app_name)));
    }
}
