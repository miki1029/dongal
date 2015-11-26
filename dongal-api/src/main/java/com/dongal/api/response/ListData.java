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
        StringBuilder officialSB = new StringBuilder();
        StringBuilder dyeonSB = new StringBuilder();

        for (Category category : categories) {
            if (category.getCategoryType().equals(CategoryEnum.DONGGUK)) {
                officialSB.append(category.getName()).append(",");
            } else if (category.getCategoryType().equals(CategoryEnum.DYEON)) {
                dyeonSB.append(category.getName()).append(",");
            }
        }
        // delete last ','
        if (officialSB.length() != 0) {
            officialSB.deleteCharAt(officialSB.length() - 1);
        }
        if (dyeonSB.length() != 0) {
            dyeonSB.deleteCharAt(dyeonSB.length() - 1);
        }

        // userInfo
        userInfo.setName(user.getName());
        userInfo.setLastUpdateTime(sdf.format(subscriptions.get(0).getCreatedTime()));
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
        posts.add(postData);
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
