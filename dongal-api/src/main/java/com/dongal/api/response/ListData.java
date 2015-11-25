package com.dongal.api.response;

import com.dongal.api.domain.Category;
import com.dongal.api.domain.CategoryEnum;
import com.dongal.api.domain.Subscription;
import com.dongal.api.domain.User;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author miki
 */
@Data
public class ListData implements Serializable {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ListData(User user, List<Subscription> subscriptions, List<Category> categories) {
        // 날짜로 역순 정렬
        Collections.sort(subscriptions, (o1, o2) -> o2.getCreatedTime().compareTo(o1.getCreatedTime()));

        // category
        Category officialCategory = null;
        Category dyeonCategory = null;

        for (Category category : categories) {
            // top category
            if (category.getCategoryType() == CategoryEnum.TOP) {
                switch (category.getName()) {
                    case "공지사항":
                        officialCategory = category;
                    case "디연":
                        dyeonCategory = category;
                }
            }
        }

        StringBuilder officialSB = new StringBuilder();
        StringBuilder dyeonSB = new StringBuilder();

        for (Category category : categories) {
            if (category.getTopCategory() != null) {
                if (category.getTopCategory().equals(officialCategory)) {
                    officialSB.append(category.getName()).append(",");
                } else if (category.getTopCategory().equals(dyeonCategory)) {
                    dyeonSB.append(category.getName()).append(",");
                }
            }
        }
        // delete last ','
        officialSB.deleteCharAt(officialSB.length() - 1);
        dyeonSB.deleteCharAt(dyeonSB.length() - 1);

        // userInfo
        userInfo.setName(user.getName());
        userInfo.setLastUpdateTime(sdf.format(subscriptions.get(0)));
        userInfo.settings.home.lastDate = 3;
        userInfo.settings.home.count = 10;
        userInfo.settings.category.official = officialSB.toString();
        userInfo.settings.category.dyeon = dyeonSB.toString();

        // posts
        PostData postData = new PostData();
        for (Subscription subscription : subscriptions) {
            String subscriptionDate = sdf.format(subscription.getCreatedTime());
            if (postData.date == null) {
                postData.date = subscriptionDate;
            } else if (!postData.date.equals(subscriptionDate)) {
                posts.add(postData);
                postData = new PostData();
                postData.date = subscriptionDate;
            }

            PostListData postListData = new PostListData();
            postListData.title = subscription.getTitle();
            postListData.url = subscription.getUrl();
            postData.list.add(postListData);
        }

    }

    private UserInfoData userInfo = new UserInfoData();
    private List<PostData> posts = new ArrayList<>();

    @Data
    private class UserInfoData {
        private String name;
        private SettingsData settings = new SettingsData();
        private String lastUpdateTime;

        @Data
        private class SettingsData {
            private HomeData home = new HomeData();
            private CategoryData category = new CategoryData();

            @Data
            private class HomeData {
                private int lastDate;
                private int count;
            }

            @Data
            private class CategoryData {
                private String official;
                private String dyeon;
            }
        }
    }

    @Data
    private class PostData {
        private String date;
        private List<PostListData> list = new ArrayList<>();
    }

    @Data
    private class PostListData {
        private String title;
        private String url;
    }
}
