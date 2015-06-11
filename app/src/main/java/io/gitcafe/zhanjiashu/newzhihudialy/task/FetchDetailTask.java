package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;
import com.google.gson.Gson;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryDetailEntity;


/**
 * Created by Jiashu on 2015/6/5.
 */
public class FetchDetailTask extends FetchTask<StoryDetailEntity> {

    private static final String URL_STORY_DETAIL = "http://news.at.zhihu.com/api/4/news/";

    public FetchDetailTask(Context context, int storyId, boolean fetchFromNetwork) {
        super(context, URL_STORY_DETAIL + storyId, fetchFromNetwork);
    }

    @Override
    protected void parseResponse(String response, FetchCallback<StoryDetailEntity> callback) {
        StoryDetailEntity entity = new Gson().fromJson(response, StoryDetailEntity.class);
        if (callback != null) {
            callback.onFetchResponse(entity);
        }
    }
}
