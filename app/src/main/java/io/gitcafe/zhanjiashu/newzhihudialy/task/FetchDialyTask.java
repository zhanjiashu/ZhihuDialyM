package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;

import com.google.gson.Gson;

import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;

/**
 * Created by Jiashu on 2015/6/7.
 */
public class FetchDialyTask extends FetchTask<DialyEntity> {

    private static final String URL_BEFORE = "http://news-at.zhihu.com/api/4/stories/before/";

    public FetchDialyTask(Context context, String date, boolean fetchFromNetwork) {
        super(context, URL_BEFORE + date, fetchFromNetwork);
    }

    @Override
    protected void parseResponse(String response, FetchCallback<DialyEntity> callback) {
        DialyEntity entity = new Gson().fromJson(response, DialyEntity.class);
        if (callback != null && entity != null) {
            callback.onFetchResponse(entity);
        }
    }
}
