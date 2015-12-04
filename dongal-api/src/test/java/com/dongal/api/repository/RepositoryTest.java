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
import java.util.Calendar;
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

//    List<Admin> admins = new ArrayList<>();
//    List<AdminPushMessage> adminPushMessages = new ArrayList<>();


    @Before
    public void setUp() {
        // 기초 데이터 생성
        Sns facebook = new Sns("facebook");
        Sns twitter = new Sns("twitter");
//        Sns instagram = new Sns("instagram");
        snses.add(facebook);
        snses.add(twitter);
//        snses.add(instagram);

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
                categories.get(0), "", "", 12214522L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3638&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010801000000&mcategoryId=0",
                categories.get(1), "", "", 12015537L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3654&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010803000000&mcategoryId=0",
                categories.get(2), "", "", 11567697L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3662&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010804000000&mcategoryId=0",
                categories.get(3), "", "", 11794358L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=9457435&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010807000000&mcategoryId=0",
                categories.get(4), "", "", 11794358L, ""));
        crawlingMetas.add(new CrawlingMeta("", "<tr><td>\\d+<\\/td><td class=\"title\"><a href=\"(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)\">(.*?)<\\/a>(<img alt=\"N\" src=\"\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif\" title=\"새글\"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt=\"파일\" src=\"\\/mbs\\/kr\\/images\\/board\\/ico_file.gif\"\\/>)<\\/td><\\/tr>", "https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=11533472&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010808000000&mcategoryId=0",
                categories.get(5), "", "", 11564303L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/qna",
                categories.get(6), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162771L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/plaza",
                categories.get(7), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1163060L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/anonymous",
                categories.get(8), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1163326L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/love",
                categories.get(9), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1163016L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/nimo",
                categories.get(10), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162990L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/forest",
                categories.get(11), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162990L, ""));
        crawlingMetas.add(new CrawlingMeta("<span class=\"absolute\">(\\d+)년 (\\d+)월 (\\d+)일</span>", "<a href=\"(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))\" page=\"\\d+\">(.*?)</a>", "https://dyeon.net/board/gong",
                categories.get(12), "<span class=\"head label\">비밀글</span>", "<span class=\"label category\" style=\"(.*?)\"><a href=\"(.*?)\">(.*?)</a></span>", 1162200L, ""));
    }

    private void setUpSubscriptions() {
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        calendar.add(Calendar.DATE, -1);
        Date d1 = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date d2 = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date d3 = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date d4 = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date d5 = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date d6 = calendar.getTime();

        calendar.add(Calendar.DATE, -1);
        Date d7 = calendar.getTime();

        subscriptions.add(new Subscription(categories.get(0), "제목제목제목1", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(1), "제목제목제목2", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(2), "제목제목제목3", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(3), "제목제목제목4", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(4), "제목제목제목5", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(5), "제목제목제목6", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(6), "제목제목제목7", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(7), "제목제목제목8", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(8), "제목제목제목9", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(9), "제목제목제목10", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(10), "제목제목제목11", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(11), "제목제목제목12", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(12), "제목제목제목13", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(13), "제목제목제목14", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(14), "제목제목제목15", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(15), "제목제목제목16", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(16), "제목제목제목17", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(17), "제목제목제목18", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(18), "제목제목제목19", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(19), "제목제목제목20", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(20), "제목제목제목21", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(21), "제목제목제목22", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(22), "제목제목제목23", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(23), "제목제목제목24", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(24), "제목제목제목25", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(25), "제목제목제목26", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(26), "제목제목제목27", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(27), "제목제목제목28", "http://dgu.edu", today));
        subscriptions.add(new Subscription(categories.get(28), "제목제목제목29", "http://dgu.edu", today));

        subscriptions.add(new Subscription(categories.get(0), "1일전1", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(1), "1일전2", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(2), "1일전3", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(3), "1일전4", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(4), "1일전5", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(5), "1일전6", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(6), "1일전7", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(7), "1일전8", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(8), "1일전9", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(9), "1일전10", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(10), "1일전11", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(11), "1일전12", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(12), "1일전13", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(13), "1일전14", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(14), "1일전15", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(15), "1일전16", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(16), "1일전17", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(17), "1일전18", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(18), "1일전19", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(19), "1일전20", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(20), "1일전21", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(21), "1일전22", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(22), "1일전23", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(23), "1일전24", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(24), "1일전25", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(25), "1일전26", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(26), "1일전27", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(27), "1일전28", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(28), "1일전29", "http://naver.com", d1));
        subscriptions.add(new Subscription(categories.get(0), "2일전1", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(1), "2일전2", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(2), "2일전3", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(3), "2일전4", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(4), "2일전5", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(5), "2일전6", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(6), "2일전7", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(7), "2일전8", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(8), "2일전9", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(9), "2일전10", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(10), "2일전11", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(11), "2일전12", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(12), "2일전13", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(13), "2일전14", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(14), "2일전15", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(15), "2일전16", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(16), "2일전17", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(17), "2일전18", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(18), "2일전19", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(19), "2일전20", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(20), "2일전21", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(21), "2일전22", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(22), "2일전23", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(23), "2일전24", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(24), "2일전25", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(25), "2일전26", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(26), "2일전27", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(27), "2일전28", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(28), "2일전29", "http://naver.com", d2));
        subscriptions.add(new Subscription(categories.get(0), "3일전1", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(1), "3일전2", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(2), "3일전3", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(3), "3일전4", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(4), "3일전5", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(5), "3일전6", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(6), "3일전7", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(7), "3일전8", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(8), "3일전9", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(9), "3일전10", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(10), "3일전11", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(11), "3일전12", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(12), "3일전13", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(13), "3일전14", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(14), "3일전15", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(15), "3일전16", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(16), "3일전17", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(17), "3일전18", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(18), "3일전19", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(19), "3일전20", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(20), "3일전21", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(21), "3일전22", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(22), "3일전23", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(23), "3일전24", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(24), "3일전25", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(25), "3일전26", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(26), "3일전27", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(27), "3일전28", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(28), "3일전29", "http://naver.com", d3));
        subscriptions.add(new Subscription(categories.get(0), "4일전1", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(1), "4일전2", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(2), "4일전3", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(3), "4일전4", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(4), "4일전5", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(5), "4일전6", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(6), "4일전7", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(7), "4일전8", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(8), "4일전9", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(9), "4일전10", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(10), "4일전11", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(11), "4일전12", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(12), "4일전13", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(13), "4일전14", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(14), "4일전15", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(15), "4일전16", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(16), "4일전17", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(17), "4일전18", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(18), "4일전19", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(19), "4일전20", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(20), "4일전21", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(21), "4일전22", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(22), "4일전23", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(23), "4일전24", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(24), "4일전25", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(25), "4일전26", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(26), "4일전27", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(27), "4일전28", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(28), "4일전29", "http://naver.com", d4));
        subscriptions.add(new Subscription(categories.get(0), "5일전1", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(1), "5일전2", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(2), "5일전3", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(3), "5일전4", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(4), "5일전5", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(5), "5일전6", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(6), "5일전7", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(7), "5일전8", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(8), "5일전9", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(9), "5일전10", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(10), "5일전11", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(11), "5일전12", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(12), "5일전13", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(13), "5일전14", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(14), "5일전15", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(15), "5일전16", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(16), "5일전17", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(17), "5일전18", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(18), "5일전19", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(19), "5일전20", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(20), "5일전21", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(21), "5일전22", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(22), "5일전23", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(23), "5일전24", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(24), "5일전25", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(25), "5일전26", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(26), "5일전27", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(27), "5일전28", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(28), "5일전29", "http://naver.com", d5));
        subscriptions.add(new Subscription(categories.get(0), "6일전1", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(1), "6일전2", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(2), "6일전3", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(3), "6일전4", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(4), "6일전5", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(5), "6일전6", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(6), "6일전7", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(7), "6일전8", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(8), "6일전9", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(9), "6일전10", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(10), "6일전11", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(11), "6일전12", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(12), "6일전13", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(13), "6일전14", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(14), "6일전15", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(15), "6일전16", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(16), "6일전17", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(17), "6일전18", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(18), "6일전19", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(19), "6일전20", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(20), "6일전21", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(21), "6일전22", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(22), "6일전23", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(23), "6일전24", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(24), "6일전25", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(25), "6일전26", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(26), "6일전27", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(27), "6일전28", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(28), "6일전29", "http://naver.com", d6));
        subscriptions.add(new Subscription(categories.get(0), "7일전1", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(1), "7일전2", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(2), "7일전3", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(3), "7일전4", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(4), "7일전5", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(5), "7일전6", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(6), "7일전7", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(7), "7일전8", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(8), "7일전9", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(9), "7일전10", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(10), "7일전11", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(11), "7일전12", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(12), "7일전13", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(13), "7일전14", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(14), "7일전15", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(15), "7일전16", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(16), "7일전17", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(17), "7일전18", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(18), "7일전19", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(19), "7일전20", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(20), "7일전21", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(21), "7일전22", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(22), "7일전23", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(23), "7일전24", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(24), "7일전25", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(25), "7일전26", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(26), "7일전27", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(27), "7일전28", "http://naver.com", d7));
        subscriptions.add(new Subscription(categories.get(28), "7일전29", "http://naver.com", d7));
    }

    @Test
    public void testForServer() throws Exception {
        // 삽입 순서 중요
        for (Sns sns : snses) {
            snsRepository.save(sns);
        }
        assertThat(snsRepository.count(), is(2L));

        for (Category category : categories) {
            categoryRepository.save(category);
        }
        assertThat(categoryRepository.count(), is(29L));

        for (CrawlingMeta crawlingMeta : crawlingMetas) {
            crawlingMetaRepository.save(crawlingMeta);
        }
        assertThat(crawlingMetaRepository.count(), is(13L));
    }

    @Test
    public void testSaveAndCount() throws Exception {
        // 삽입 순서 중요
        for (Sns sns : snses) {
            snsRepository.save(sns);
        }
        assertThat(snsRepository.count(), is(2L));

        for (Category category : categories) {
            categoryRepository.save(category);
        }
        assertThat(categoryRepository.count(), is(29L));

        for (CrawlingMeta crawlingMeta : crawlingMetas) {
            crawlingMetaRepository.save(crawlingMeta);
        }
        assertThat(crawlingMetaRepository.count(), is(13L));

        for (Subscription subscription : subscriptions) {
            subscription.setCrawlingTime(subscription.getCreatedTime());
            subscriptionRepository.save(subscription);
        }
        assertThat(subscriptionRepository.count(), is(29L * 8));

        for (User user : users) {
            user.setLastLoginTime(user.getCreatedTime());
            userRepository.save(user);
        }
        assertThat(userRepository.count(), is(2L));

    }
}
