package io.gitcafe.zhanjiashu.newzhihudialy.task;

import android.content.Context;

import com.google.gson.Gson;

import java.text.MessageFormat;

import io.gitcafe.zhanjiashu.newzhihudialy.model.DialyEntity;

/**
 * Created by Jiashu on 2015/6/16.
 */
public class FetchOldStoryTask extends FetchTask<DialyEntity> {


    public FetchOldStoryTask(Context context, int themeId, int lastStoryId, boolean fetchFromNetwork) {

        super(context, formatUrl(themeId, lastStoryId), fetchFromNetwork);
    }

    @Override
    protected void parseResponse(String response, FetchCallback<DialyEntity> callback) {
        DialyEntity entity = new Gson().fromJson(response, DialyEntity.class);
        if (callback != null && entity != null) {
            callback.onFetchResponse(entity);
        }
    }

    private static String formatUrl(int themeId, int lastStoryId) {
        return  "http://news-at.zhihu.com/api/4/theme/" + themeId + "/before/" + lastStoryId;
    }
}
