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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author miki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfig.class, WebAppConfig.class, Initializer.class })
@WebAppConfiguration
public class RepositoryTest {
    @Autowired SnsRepository snsRepository;
    @Autowired UserRepository userRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired SubscriptionRepository subscriptionRepository;
    @Autowired CrawlingMetaRepository crawlingMetaRepository;

    List<Sns> snses = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<UserSns> userSns = new ArrayList<>();

    List<Category> categories = new ArrayList<>();
    List<Subscription> subscriptions = new ArrayList<>();

    List<CrawlingMeta> crawlingMetas = new ArrayList<>();

    List<Admin> admins = new ArrayList<>();
    List<AdminPushMessage> adminPushMessages = new ArrayList<>();


    @Before
    public void setUp() {
        // 기초 데이터 생성
        Sns facebook = new Sns("facebook");
        Sns twitter = new Sns("twitter");
        Sns instagram = new Sns("instagram");
        snses.add(facebook);
        snses.add(twitter);
        snses.add(instagram);

        User minwoo = new User("kmwkmw5@dongguk.edu", "1234", "김민우", new Date(), false);
        User kisang = new User("felika@dongguk.edu", "5678", "강기상", new Date(), true);
        users.add(minwoo);
        users.add(kisang);

        setUpCategories();
        setUpCrawlingMetas();
        setUpSubscriptions();

        // 관계 설정
        kisang.getSns().add(new UserSns(kisang, facebook, "FACEBOOKVALUE"));
        minwoo.getCategories().add(categories.get(0));
        minwoo.getCategories().add(categories.get(2));
        minwoo.getCategories().add(categories.get(4));
        minwoo.getCategories().add(categories.get(6));
        minwoo.getCategories().add(categories.get(8));
        minwoo.getCategories().add(categories.get(10));
        minwoo.getFavorites().add(subscriptions.get(2));

        // 삭제 순서 중요
        userRepository.deleteAll();
        snsRepository.deleteAll();
        subscriptionRepository.deleteAll();
        crawlingMetaRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    private void setUpCategories() {
        categories.add(new Category("일반", CategoryEnum.DONGGUK));
        categories.add(new Category("학사", CategoryEnum.DONGGUK));
        categories.add(new Category("입시", CategoryEnum.DONGGUK));
        categories.add(new Category("장학", CategoryEnum.DONGGUK));
        categories.add(new Category("국제", CategoryEnum.DONGGUK));
        categories.add(new Category("학술/행사공지", CategoryEnum.DONGGUK));
        categories.add(new Category("*학사질문게시판", CategoryEnum.DYEON));
        categories.add(new Category("자유게시판", CategoryEnum.DYEON));
        categories.add(new Category("익명게시판", CategoryEnum.DYEON));
        categories.add(new Category("Love카운셀러", CategoryEnum.DYEON));
        categories.add(new Category("Nimo를 찾아서", CategoryEnum.DYEON));
        categories.add(new Category("대나무숲", CategoryEnum.DYEON));
        categories.add(new Category("대외활동", CategoryEnum.DYEON));
        categories.add(new Category("교내단체소식", CategoryEnum.DYEON));
        categories.add(new Category("취업&알바", CategoryEnum.DYEON));
        categories.add(new Category("하숙&자취", CategoryEnum.DYEON));
        categories.add(new Category("광고게시판", CategoryEnum.DYEON));
        categories.add(new Category("Dyeon Market", CategoryEnum.DYEON));
        categories.add(new Category("job아라!", CategoryEnum.DYEON));
        categories.add(new Category("STUDY그룹", CategoryEnum.DYEON));
        categories.add(new Category("찾아주세요", CategoryEnum.DYEON));
        categories.add(new Category("Dyeon 스타일", CategoryEnum.DYEON));
        categories.add(new Category("어썸피플", CategoryEnum.DYEON));
        categories.add(new Category("올드보이", CategoryEnum.DYEON));
        categories.add(new Category("해부학개론", CategoryEnum.DYEON));
        categories.add(new Category("맛잌다", CategoryEnum.DYEON));
        categories.add(new Category("유머게시판", CategoryEnum.DYEON));
        categories.add(new Category("정치사회이슈", CategoryEnum.DYEON));
        categories.add(new Category("16학번 게시판", CategoryEnum.DYEON));
    }

    private void setUpCrawlingMetas() {
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3646&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010802000000&mcategoryId=0",
                categories.get(1), "", "", 12214522L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3638&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010801000000&mcategoryId=0",
                categories.get(2), "", "", 12015537L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3654&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010803000000&mcategoryId=0",
                categories.get(3), "", "", 11567697L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3662&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010804000000&mcategoryId=0",
                categories.get(4), "", "", 11794358L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=9457435&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010807000000&mcategoryId=0",
                categories.get(5), "", "", 11794358L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=11533472&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010808000000&mcategoryId=0",
                categories.get(6), "", "", 11564303L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/qna",
                categories.get(7), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162771L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/plaza",
                categories.get(8), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1163060L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/anonymous",
                categories.get(9), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1163326L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/love",
                categories.get(10), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1163016L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/nimo",
                categories.get(11), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162990L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/forest",
                categories.get(12), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162990L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/gong",
                categories.get(13), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162200L, ""));
    }

    private void setUpSubscriptions() {
        subscriptions.add(new Subscription(categories.get(0), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(1), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(2), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(3), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(4), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(5), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(6), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(7), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(8), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(9), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(10), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(11), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(12), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(13), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(14), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(15), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(16), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(17), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(18), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(19), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(20), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(21), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(22), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(23), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(24), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(25), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(26), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(27), "제목제목제목", "http://dgu.edu", new Date()));
        subscriptions.add(new Subscription(categories.get(28), "제목제목제목", "http://dgu.edu", new Date()));
    }

    @Test
    public void testSaveAndCount() throws Exception {
        // 삽입 순서 중요
        for (Sns sns : snses) {
            snsRepository.save(sns);
        }
        assertThat(snsRepository.count(), is(3L));

        for (Category category : categories) {
            categoryRepository.save(category);
        }
        assertThat(categoryRepository.count(), is(29L));

        for (CrawlingMeta crawlingMeta : crawlingMetas) {
            crawlingMetaRepository.save(crawlingMeta);
        }
        assertThat(crawlingMetaRepository.count(), is(13L));

        for (Subscription subscription : subscriptions) {
            subscriptionRepository.save(subscription);
        }
        assertThat(subscriptionRepository.count(), is(29L));

        for (User user : users) {
            userRepository.save(user);
        }
        assertThat(userRepository.count(), is(2L));

    }
}
