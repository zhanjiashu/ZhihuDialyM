package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;

import com.google.gson.Gson;

import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.LatestDialyEntity;

/**
 * Created by Jiashu on 2015/6/7.
 */
public class FetchLatestDialyTask extends FetchTask<DialyEntity> {

    public static final String URL_LATEST = "http://news-at.zhihu.com/api/4/stories/latest";

    public FetchLatestDialyTask(Context context) {
        this(context, true);
    }

    public FetchLatestDialyTask(Context context, boolean fetchFromNetwork) {
        super(context, URL_LATEST, fetchFromNetwork);
    }

    @Override
    protected void parseResponse(String response, FetchCallback<DialyEntity> callback) {
        LatestDialyEntity entity = new Gson().fromJson(response, LatestDialyEntity.class);
        if (callback != null) {
            callback.onFetchResponse(entity);
        }
    }
}
