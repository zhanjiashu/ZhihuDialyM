package io.gitcafe.zhanjiashu.newzhihudialy.model;

import java.util.List;

public class StoryEntity {
    private List<String> images;
    private String ga_prefix;
    private int id;
    private int type;
    private String title;

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setGaPrefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public String getGaPrefix() {
        return ga_prefix;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "StoryEntity{" +
                "images=" + images +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                '}';
    }
}