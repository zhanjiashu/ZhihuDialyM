package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;

import com.google.gson.Gson;

import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeListEntity;

/**
 * Created by Jiashu on 2015/6/9.
 */
public class FetchThemesTask extends FetchTask<ThemeListEntity> {

    private static final String THEMES_URL = "http://news-at.zhihu.com/api/4/themes";

    public FetchThemesTask(Context context, boolean fetchFromNetwork) {
        super(context, THEMES_URL, fetchFromNetwork);
    }

    @Override
    protected void parseResponse(String response, FetchCallback<ThemeListEntity> callback) {
        ThemeListEntity themeListEntity = new Gson().fromJson(response, ThemeListEntity.class);
        if (callback != null) {
            callback.onFetchResponse(themeListEntity);
        }
    }
}
