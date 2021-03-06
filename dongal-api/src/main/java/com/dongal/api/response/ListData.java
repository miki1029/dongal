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
import java.util.Date;
import java.util.List;

/**
 * @author miki
 */
@Data
public class ListData implements Serializable {

    private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ListData(User user, List<Subscription> subscriptions, List<Category> categories, Date lastLoginTime) {
        // 날짜로 역순 정렬
         Collections.sort(subscriptions, (o1, o2) -> o2.getCrawlingTime().compareTo(o1.getCrawlingTime()));

        // category
/*        boolean dongguk = false;
        boolean dyeon = false;

        for (Category category : categories) {
            if (category.getCategoryType().equals(CategoryEnum.DONGGUK)) {
                dongguk = true;
            } else if (category.getCategoryType().equals(CategoryEnum.DYEON)) {
                dyeon = true;
            }
            if (dongguk && dyeon) break;
        }*/

        // userInfo
        userInfo.name = user.getName();
        if (subscriptions.size() == 0)
            userInfo.lastCrawlingTime = "";
        else
            userInfo.lastCrawlingTime = sdf.format(subscriptions.get(0).getCrawlingTime());

        // posts
        PostData postData = new PostData();
        for (Subscription subscription : subscriptions) {
            String subscriptionDate = yyyyMMdd.format(subscription.getCreatedTime());
            if (postData.date == null) {
                postData.date = subscriptionDate;
            } else if (!postData.date.equals(subscriptionDate)) {
                posts.add(postData);
                postData = new PostData();
                postData.date = subscriptionDate;
            }

            PostListData postListData = new PostListData();
            postListData.subscriptionIdx = subscription.getIdx();
            postListData.title = subscription.getTitle();
            postListData.url = subscription.getUrl();
            if (subscription.getCrawlingTime().getTime() > lastLoginTime.getTime()) {
                postListData.isNew = true;
            } else {
                postListData.isNew = false;
            }
            if (subscription.getCategory().getCategoryType() == CategoryEnum.DONGGUK) {
                postListData.isDongguk = true;
                postListData.isDyeon = false;
            }
            else if (subscription.getCategory().getCategoryType() == CategoryEnum.DYEON) {
                postListData.isDongguk = false;
                postListData.isDyeon = true;
            } else {
                postListData.isDongguk = false;
                postListData.isDyeon = false;
            }
            postData.list.add(postListData);
        }
        if (postData.date != null)
            posts.add(postData);
    }

    private UserInfoData userInfo = new UserInfoData();
    private List<PostData> posts = new ArrayList<>();

    @Data
    private class UserInfoData {
        private String name;
        private String lastCrawlingTime;
    }

    @Data
    private class PostData {
        private String date;
        private List<PostListData> list = new ArrayList<>();
    }

    @Data
    private class PostListData {
        private Long subscriptionIdx;
        private String title;
        private String url;
        private boolean isNew;
        private boolean isDongguk;
        private boolean isDyeon;
    }
}
