package io.gitcafe.zhanjiashu.newzhihudialy.model;

public class TopStoryEntity {
        /**
         * image : http://pic2.zhimg.com/4ad3af18f9a94395a2e86cf8e7699c25.jpg
         * ga_prefix : 053115
         * id : 4771976
         * type : 0
         * title : 三国时代的将军们，有些穿过了铠甲（多图）
         */
        private String image;
        private String ga_prefix;
        private int id;
        private int type;
        private String title;

        public void setImage(String image) {
            this.image = image;
        }

        public void setGa_prefix(String ga_prefix) {
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

        public String getImage() {
            return image;
        }

        public String getGa_prefix() {
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
    }