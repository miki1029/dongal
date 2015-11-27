SET FOREIGN_KEY_CHECKS = 0;


TRUNCATE TABLE dongal.category;
INSERT INTO dongal.category(name, top_id) VALUES('동국대', 0);
INSERT INTO dongal.category(name, top_id) VALUES('디연', 0);
INSERT INTO dongal.category(name, top_id) VALUES('일반', 1);
INSERT INTO dongal.category(name, top_id) VALUES('학사', 1);
INSERT INTO dongal.category(name, top_id) VALUES('입시', 1);
INSERT INTO dongal.category(name, top_id) VALUES('장학', 1);
INSERT INTO dongal.category(name, top_id) VALUES('국제', 1);
INSERT INTO dongal.category(name, top_id) VALUES('학술/행사공지', 1);
INSERT INTO dongal.category(name, top_id) VALUES('★학사질문게시판', 2);
INSERT INTO dongal.category(name, top_id) VALUES('자유게시판', 2);
INSERT INTO dongal.category(name, top_id) VALUES('익명게시판', 2);
INSERT INTO dongal.category(name, top_id) VALUES('Love카운셀러', 2);
INSERT INTO dongal.category(name, top_id) VALUES('Nimo를 찾아서', 2);
INSERT INTO dongal.category(name, top_id) VALUES('대나무숲', 2);
INSERT INTO dongal.category(name, top_id) VALUES('대외활동', 2);
INSERT INTO dongal.category(name, top_id) VALUES('교내단체소식', 2);
INSERT INTO dongal.category(name, top_id) VALUES('취업&알바', 2);
INSERT INTO dongal.category(name, top_id) VALUES('하숙&자취', 2);
INSERT INTO dongal.category(name, top_id) VALUES('광고게시판', 2);
INSERT INTO dongal.category(name, top_id) VALUES('Dyeon Market', 2);
INSERT INTO dongal.category(name, top_id) VALUES('job아라!', 2);
INSERT INTO dongal.category(name, top_id) VALUES('STUDY그룹', 2);
INSERT INTO dongal.category(name, top_id) VALUES('찾아주세요', 2);
INSERT INTO dongal.category(name, top_id) VALUES('Dyeon 스타일', 2);
INSERT INTO dongal.category(name, top_id) VALUES('어썸피플', 2);
INSERT INTO dongal.category(name, top_id) VALUES('올드보이', 2);
INSERT INTO dongal.category(name, top_id) VALUES('해부학개론', 2);
INSERT INTO dongal.category(name, top_id) VALUES('맛잌다', 2);
INSERT INTO dongal.category(name, top_id) VALUES('유머게시판', 2);
INSERT INTO dongal.category(name, top_id) VALUES('정치사회이슈', 2);
INSERT INTO dongal.category(name, top_id) VALUES('16학번 게시판', 2);

TRUNCATE TABLE dongal.crawling_meta;

-- 일반
INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('', '<tr><td>\\d+<\\/td><td class="title"><a href="(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)">(.*?)<\\/a>(<img alt="N" src="\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif" title="새글"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt="파일" src="\\/mbs\\/kr\\/images\\/board\\/ico_file.gif"\\/>)<\\/td><\\/tr>', 'https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3646&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010802000000&mcategoryId=0',
       3, '', '', 12214522, '');

-- 학사(12015537)
INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('', '<tr><td>\\d+<\\/td><td class="title"><a href="(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)">(.*?)<\\/a>(<img alt="N" src="\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif" title="새글"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt="파일" src="\\/mbs\\/kr\\/images\\/board\\/ico_file.gif"\\/>)<\\/td><\\/tr>', 'https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3638&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010801000000&mcategoryId=0',
       4, '', '', 12015537, '');

-- 입시(11567697)
INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('', '<tr><td>\\d+<\\/td><td class="title"><a href="(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)">(.*?)<\\/a>(<img alt="N" src="\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif" title="새글"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt="파일" src="\\/mbs\\/kr\\/images\\/board\\/ico_file.gif"\\/>)<\\/td><\\/tr>', 'https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3654&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010803000000&mcategoryId=0',
       5, '', '', 11567697, '');

-- 장학(11794358)
INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('', '<tr><td>\\d+<\\/td><td class="title"><a href="(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)">(.*?)<\\/a>(<img alt="N" src="\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif" title="새글"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt="파일" src="\\/mbs\\/kr\\/images\\/board\\/ico_file.gif"\\/>)<\\/td><\\/tr>', 'https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3662&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010804000000&mcategoryId=0',
       6, '', '', 11794358, '');

-- 국제(11568437)
INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('', '<tr><td>\\d+<\\/td><td class="title"><a href="(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)">(.*?)<\\/a>(<img alt="N" src="\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif" title="새글"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt="파일" src="\\/mbs\\/kr\\/images\\/board\\/ico_file.gif"\\/>)<\\/td><\\/tr>', 'https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=9457435&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010807000000&mcategoryId=0',
       7, '', '', 11794358, '');


-- 학술/행사(11564303)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('', '<tr><td>\\d+<\\/td><td class="title"><a href="(view\\.jsp\\?spage=\\d+&amp;boardId=(\\d+)&amp;boardSeq=(\\d+)&amp;id=kr_\\d+&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0)">(.*?)<\\/a>(<img alt="N" src="\\/Web-home\\/manager\\/images\\/mbsPreview\\/icon_new.gif" title="새글"\\/>|)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td>((.*?)|<img alt="파일" src="\\/mbs\\/kr\\/images\\/board\\/ico_file.gif"\\/>)<\\/td><\\/tr>', 'https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=11533472&search=&column=&categoryDepth=&categoryId=0&boardType=01&listType=01&command=list&id=kr_010808000000&mcategoryId=0',
8, '', '', 11564303, '');


-- ★학사질문게시판(1162771)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/qna',
9, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1162771, '');

-- 자유게시판(1163060)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/plaza',
10, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1163060, '');

-- 익명게시판(1163326)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/anonymous',
11, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1163326, '');

-- 럽카(1163016)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/love',
12, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1163016, '');


-- 니모를 찾아서(1162810)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/nimo',
13, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1162990, '');


-- 대나무숲(1162971)
INSERT INTO dongal.crawling_meta(
created_time_pattern, title_pattern, url,
category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/forest',
14, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1162990, '');



INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/gong',
       15, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1162200, '');


INSERT INTO dongal.crawling_meta(
  created_time_pattern, title_pattern, url,
  category_id, secret_pattern, category_pattern, last_seq, url_pattern)
VALUES('<span class="absolute">(\\d+)년 (\\d+)월 (\\d+)일<\/span>', '<a href="(http:\\/\\/dyeon\\.net\\/post\\/(\\d+)(\\?page=\\d+|))" page="\\d+">(.*?)</a>', 'https://dyeon.net/board/announce',
       16, '<span class="head label">비밀글</span>', '<span class="label category" style="(.*?)"><a href="(.*?)">(.*?)</a></span>', 1162200, '');


TRUNCATE TABLE dongal.subscription;




SET FOREIGN_KEY_CHECKS = 1;
