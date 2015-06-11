package io.gitcafe.zhanjiashu.newzhihudialy.model;

public class MemberEntity {
    private String zhihu_url_token;
    private String name;
    private String bio;
    private int id;
    private String avatar;

    public void setUrlToken(String urlToken) {
        this.zhihu_url_token = urlToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrlToken() {
        return zhihu_url_token;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }
}