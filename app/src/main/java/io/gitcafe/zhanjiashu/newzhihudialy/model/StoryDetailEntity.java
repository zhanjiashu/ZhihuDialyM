package io.gitcafe.zhanjiashu.newzhihudialy.model;

import java.util.List;

/**
 * Created by Jiashu on 2015/5/31.
 */
public class StoryDetailEntity {

    private String image;
    private List<String> css;
    private String share_url;
    private String ga_prefix;
    private List<String> js;
    private SectionEntity section;
    private int id;
    private String body;
    private String title;
    private List<MemberEntity> recommenders;
    private int type;
    private String image_source;

    public void setImage(String image) {
        this.image = image;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRecommenders(List<MemberEntity> recommenders) {
        this.recommenders = recommenders;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getImage() {
        return image;
    }

    public List<String> getCss() {
        return css;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public List<String> getJs() {
        return js;
    }

    public SectionEntity getSection() {
        return section;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public List<MemberEntity> getRecommenders() {
        return recommenders;
    }

    public int getType() {
        return type;
    }

    public String getImage_source() {
        return image_source;
    }

    public class SectionEntity {
        private String thumbnail;
        private String name;
        private int id;

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
