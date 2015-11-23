package com.dongal.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author miki
 */
@Data
public class ListData implements Serializable {

    private UserInfo userInfo;
    private List<Alarm> alarms;

    @Data
    private class UserInfo {
        private String name;
        private Settings settings;
        private String lastUpdateTime;

        @Data
        private class Settings {
            private Home home;
            private Category category;

            @Data
            private class Home {
                private int lastDate;
                private int count;
            }

            @Data
            private class Category {
                private String official;
                private String dyeon;
                private String dgucoop;
            }
        }
    }

    @Data
    private class Alarm {
        private String date;
        private List<Post> list;

        @Data
        private class Post {
            private String title;
            private String url;
        }
    }
}
