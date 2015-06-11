package io.gitcafe.zhanjiashu.newzhihudialy.model;

import java.util.List;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class LatestDialyEntity extends DialyEntity{

    private List<TopStoryEntity> top_stories;

    @Override
    public void setDate(String date) {
        super.setDate(date);
    }

    public void setTopStories(List<TopStoryEntity> top_stories) {
        this.top_stories = top_stories;
    }

    @Override
    public void setStories(List<StoryEntity> stories) {
        super.setStories(stories);
    }

    public String getDate() {
        return super.getDate();
    }

    public List<TopStoryEntity> getTopStories() {
        return top_stories;
    }

    public List<StoryEntity> getStories() {
        return super.getStories();
    }
}
