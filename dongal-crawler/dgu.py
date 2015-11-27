#-*- coding: utf-8 -*-
from BeautifulSoup import BeautifulSoup
import urllib2
import MySQLdb
import json
 
def crawling(baseUrl):
    handle = urllib2.urlopen(baseUrl)
    data = handle.read()
    soup = BeautifulSoup(data)

    output = {}
    subscriptions = []

    tableRows = soup.findAll("tr")
    for idx, tableRow in enumerate(tableRows):
        if idx > 0:
            for idx, tableData in enumerate(tableRow):
                if tableData != None:
                    if idx == 3:
                        subscription = {}
                        subscription['category_id'] = 0;

                        subscription['title'] = tableData.next.next.string
                        subscription['url'] = baseUrl + "/" + tableData.next.next['href']

                    elif idx == 7:
                        subscription['created_time'] = tableData.string.replace('\t', '').replace('\n','').replace(' ', '')
                        subscriptions.append(subscription)

    output['subscriptions'] = subscriptions

    print json.dumps(output, ensure_ascii=False, sort_keys=True, indent=4).encode('utf8')

# 일반
crawling("https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3646&id=kr_010802000000")

# 학사
crawling("https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3638&id=kr_010801000000")

# 입시
crawling("https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3654&id=kr_010803000000")

# 장학
crawling("https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=3662&id=kr_010804000000")

# 국제공지
crawling("https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=9457435&id=kr_010807000000")

# 학술/행사 공지
crawling("https://www.dongguk.edu/mbs/kr/jsp/board/list.jsp?boardId=11533472&id=kr_010808000000")
