#-*- coding: utf-8 -*-
from bs4 import BeautifulSoup

import urllib2
import MySQLdb
import json
import re
 
def crawling(baseUrl):
    handle = urllib2.urlopen(baseUrl)
    data = handle.read()
    soup = BeautifulSoup(data, 'html.parser')

    output = {}
    subscriptions = []

    board_list = soup.select('table#board_list > tbody > tr')
    for idx, board_item in enumerate(board_list):
        board_item_pattern = re.compile('<tr><td>\d+<\/td><td class="title"><a href="view\.jsp\?spage=1&amp;boardId=(\d+)&amp;boardSeq=(\d+)&amp;id=kr_010801000000&amp;column=&amp;search=&amp;categoryDepth=&amp;mcategoryId=0">(.*?)<\/a>(<img alt="N" src="\/Web-home\/manager\/images\/mbsPreview\/icon_new.gif" title="새글"\/>|)<\/td><td>(.*?)<\/td><td>(.*?)<\/td><td>(.*?)<\/td><td>((.*?)|<img alt="파일" src="\/mbs\/kr\/images\/board\/ico_file.gif"\/>)<\/td><\/tr>')
        
        board_item = re.search(board_item_pattern, str(board_item).replace("\n", ""))
        if board_item:
            subscription = {}
            subscription['link'] = 'http://dongguk.edu/'+ board_item.group(0)
            subscription['boardId'] = board_item.group(1)
            subscription['postId'] = board_item.group(2)
            subscription['title'] = board_item.group(3)
            subscription['created_time'] = board_item.group(6).replace(" ", "").replace("\t", "")

            print subscription


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
