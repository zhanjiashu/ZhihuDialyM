package io.gitcafe.zhanjiashu.newzhihudialy.model;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class StoryExtraEntity {
    private int comments;
    private int popularity;
    private int short_comments;
    private int long_comments;

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getComments() {
        return comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public int getLong_comments() {
        return long_comments;
    }
}
