package io.gitcafe.zhanjiashu.newzhihudialy.model;

import java.util.List;

/**
 * Created by Jiashu on 2015/6/9.
 */
public class ThemeListEntity {

    /**
     * subscribed : []
     * limit : 1000
     * others : [{"thumbnail":"http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg","color":15007,"name":"日常心理学","description":"了解自己和别人，了解彼此的欲望和局限。","id":13},{"thumbnail":"http://pic4.zhimg.com/2c38a96e84b5cc8331a901920a87ea71.jpg","color":8307764,"name":"用户推荐日报","description":"内容由知乎用户推荐，海纳主题百万，趣味上天入地","id":12},{"thumbnail":"http://pic3.zhimg.com/00eba01080138a5ac861d581a64ff9bd.jpg","color":14483535,"name":"电影日报","description":"除了经典和新片，我们还关注技术和产业","id":3},{"thumbnail":"http://pic4.zhimg.com/4aa8400ba46d3d46e34a9836744ea232.jpg","color":8307764,"name":"不许无聊","description":"为你发现最有趣的新鲜事，建议在 WiFi 下查看","id":11},{"thumbnail":"http://p1.zhimg.com/d3/7b/d37b38d5c82b4345ccd7e50c4ae997da.jpg","color":62140,"name":"设计日报","description":"好设计需要打磨和研习，我们分享灵感和路径","id":4},{"thumbnail":"http://pic4.zhimg.com/aa94e197491fb9c44d384c4747773810.jpg","color":1615359,"name":"大公司日报","description":"商业世界变化越来越快，就是这些家伙干的","id":5},{"thumbnail":"http://pic2.zhimg.com/f2e97ff073e5cf9e79c7ed498727ebd6.jpg","color":16031744,"name":"财经日报","description":"从业者推荐的财经金融资讯","id":6},{"thumbnail":"http://pic2.zhimg.com/98d7b4f8169c596efb6ee8487a30c8ee.jpg","color":9699556,"name":"互联网安全","description":"把黑客知识科普到你的面前","id":10},{"thumbnail":"http://pic3.zhimg.com/2f214a4ca51855670668530f7d333fd8.jpg","color":59647,"name":"开始游戏","description":"如果你喜欢游戏，就从这里开始","id":2},{"thumbnail":"http://pic4.zhimg.com/eac535117ed895983bd2721f35d6e8dc.jpg","color":1564695,"name":"音乐日报","description":"有音乐就很好","id":7},{"thumbnail":"http://pic1.zhimg.com/a0f97c523c64e749c700b2ddc96cfd7c.jpg","color":6123007,"name":"动漫日报","description":"用技术的眼睛仔细看懂每一部动画和漫画","id":9},{"thumbnail":"http://pic1.zhimg.com/bcf7d594f126e5ceb22691be32b2650a.jpg","color":16046124,"name":"体育日报","description":"关注体育，不吵架。","id":8}]
     */
    private List<ThemeEntity> subscribed;
    private int limit;
    private List<ThemeEntity> others;

    public void setSubscribed(List<ThemeEntity> subscribed) {
        this.subscribed = subscribed;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOthers(List<ThemeEntity> others) {
        this.others = others;
    }

    public List<ThemeEntity> getSubscribed() {
        return subscribed;
    }

    public int getLimit() {
        return limit;
    }

    public List<ThemeEntity> getOthers() {
        return others;
    }


}
