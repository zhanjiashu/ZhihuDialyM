package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;

import com.google.gson.Gson;

import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeDetailEntity;

/**
 * Created by Jiashu on 2015/6/9.
 */
public class FetchThemeDetailTask extends FetchTask<ThemeDetailEntity> {

    private static final String URL_THEME_DETAIL = "http://news-at.zhihu.com/api/4/theme/";

    public FetchThemeDetailTask(Context context, int themeId, boolean fetchFromNetwork) {
        super(context, URL_THEME_DETAIL + themeId, fetchFromNetwork);
    }

    @Override
    protected void parseResponse(String response, FetchCallback<ThemeDetailEntity> callback) {
        ThemeDetailEntity entity = new Gson().fromJson(response, ThemeDetailEntity.class);
        if (callback != null) {
            callback.onFetchResponse(entity);
        }
    }
}
