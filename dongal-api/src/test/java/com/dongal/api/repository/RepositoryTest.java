package com.dongal.api.repository;

import com.dongal.api.config.DatabaseConfig;
import com.dongal.api.config.Initializer;
import com.dongal.api.config.WebAppConfig;
import com.dongal.api.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author miki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfig.class, WebAppConfig.class, Initializer.class })
@WebAppConfiguration
public class RepositoryTest {
    @Autowired UserRepository userRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired SubscriptionRepository subscriptionRepository;
    @Autowired SnsRepository snsRepository;

    Map<String, Sns> sns = new HashMap<>();
    Map<String, User> users = new HashMap<>();
    Map<String, UserSns> userSns = new HashMap<>();

    Map<String, Category> categories = new HashMap<>();
    Map<String, Subscription> subscriptions = new HashMap<>();

    Map<String, CrawlingMeta> crawlingMetas = new HashMap<>();

    Map<String, Admin> admins = new HashMap<>();
    Map<String, AdminPushMessage> adminPushMessages = new HashMap<>();

    @Before
    public void setUp() {
        sns.put("facebook", new Sns("facebook"));
        sns.put("twitter", new Sns("twitter"));
        sns.put("instagram", new Sns("instagram"));
        
        users.put("minwoo", new User("kmwkmw5@dongguk.edu", "1234", "김민우", new Date(), false));
        users.put("kisang", new User("felika@dongguk.edu", "5678", "강기상", new Date(), true));

        userSns.put("k-f", new UserSns(users.get("kisang"), sns.get("facebook"), "FACEBOOKVALUE"));
        users.get("kisang").getSns().add(userSns.get("k-f"));

        categories.put("학사", new Category("학사", CategoryEnum.DONGGUK));
        categories.put("장학", new Category("장학", CategoryEnum.DONGGUK));

        categories.put("익게", new Category("익명 게시판", CategoryEnum.DYEON));

        subscriptions.put("학사1", new Subscription(categories.get("학사"), "졸업 공지사항1", "http://dgu.edu/1", new Date()));
        subscriptions.put("학사2", new Subscription(categories.get("학사"), "졸업 공지사항2", "http://dgu.edu/2", new Date()));
        subscriptions.put("장학1", new Subscription(categories.get("장학"), "장학 공지사항1", "http://dgu.edu/3", new Date()));
        subscriptions.put("장학2", new Subscription(categories.get("장학"), "장학 공지사항2", "http://dgu.edu/4", new Date()));

        subscriptions.put("익게1", new Subscription(categories.get("익게"), "익명 게시판1", "http://dgu.edu/5", new Date()));
        subscriptions.put("익게2", new Subscription(categories.get("익게"), "익명 게시판2", "http://dgu.edu/6", new Date()));

        snsRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        subscriptionRepository.deleteAll();
    }

    @Test
    public void testSaveAndCount() throws Exception {
        for (String key : sns.keySet()) {
            snsRepository.save(sns.get(key));
        }
        assertThat(snsRepository.count(), is(3L));

        for (String key : users.keySet()) {
            userRepository.save(users.get(key));
        }
        assertThat(userRepository.count(), is(2L));

        for (String key : categories.keySet()) {
            categoryRepository.save(categories.get(key));
        }
        assertThat(categoryRepository.count(), is(3L));

        for (String key : subscriptions.keySet()) {
            subscriptionRepository.save(subscriptions.get(key));
        }
        assertThat(subscriptionRepository.count(), is(6L));
    }
}
