package io.gitcafe.zhanjiashu.newzhihudialy.model;

import java.util.List;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class DialyEntity {

    private String date;
    private List<StoryEntity> stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoryEntity> stories) {
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoryEntity> getStories() {
        return stories;
    }
}
