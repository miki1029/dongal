#-*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from pprint import pprint

import requests
import urllib, urllib2, cookielib
import re
import MySQLdb

ROOT_URL = 'https://dyeon.net'
DYEON = []

CATEGORY_META_DATA = {
    'dongguk' : [],
    'dyeon' : []
}

db = MySQLdb.connect("localhost", "root", "rkdrltkd", "dongal")
cursor = db.cursor()

def getCategoryMetaDataAndLastSeq():
    # Get category metadata
    cursor.execute("""
        SELECT  A.idx, A.name,
                B.title_pattern, B.created_time_pattern, B.secret_pattern, B.category_pattern,
                IFNULL(B.last_seq, -1), B.url
        FROM dongal.category as A
        INNER JOIN dongal.crawling_meta as B on A.idx = B.category_id
        WHERE A.top_id = 2;
    """)
    data = cursor.fetchall()
    
    for row in data:
        category = {}
        category['idx'] = row[0]
        category['name'] = row[1]
        category['title_pattern'] = row[2]
        category['created_time_pattern'] = row[3]
        category['secret_pattern'] = row[4]
        category['category_pattern'] = row[5]
        category['last_seq'] = row[6]
        category['url'] = row[7]
        CATEGORY_META_DATA['dyeon'].append(category)

def login_dyeon():
    username = 'kang8530'
    password = 'crawler'

    loginUrl = ROOT_URL + '/user/login'

    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    login_data = urllib.urlencode({'user[login]' : username, 'user[password]' : password})
    opener.open(loginUrl, login_data)

    return opener

session = login_dyeon()

def crawling_dyeon(crawling_meta, dyeon, page):
    crawling_url = dyeon['url'] + "?page=" + str(page)
    print "-------------%s(%d): %s------------" % (dyeon['name'], dyeon['idx'], crawling_url)
    resp = session.open(crawling_url)
    soup = BeautifulSoup(resp, 'html.parser')

    board_list = soup.select('table.bbs > tbody')
    pattern = r'<tr class="(new hot|new|hot|)" style="">(.*?)</tr>'
    
    board_data = re.findall(pattern, str(board_list[0]).replace("\n", ""))

    for idx, board_item in enumerate(board_data):
        secret = re.search(dyeon['secret_pattern'], board_item[1])
        if not secret:
            title_pattern = re.compile(dyeon['title_pattern'])
            created_time_pattern = re.compile(dyeon['created_time_pattern'])
            category_pattern = re.compile(dyeon['category_pattern'])

            link = re.search(title_pattern, board_item[1])
            date = re.search(created_time_pattern, board_item[1])
            yyyymmdd = date.group(1) + '-' + date.group(2) + '-' + date.group(3)
            category = re.search(category_pattern, board_item[1])

            if int(crawling_meta['before_last_seq']) >= int(link.group(2)):
                crawling_meta['is_meet_last_board_item'] = True
                break;

            if (not crawling_meta['last_seq_updated']) and (int(crawling_meta['after_last_seq']) < int(link.group(2))):
                crawling_meta['after_last_seq'] = int(link.group(2))
                crawling_meta['last_seq_updated'] = True

            subscription = {}
            subscription['link'] = link.group(1)
            subscription['postId'] = link.group(2)
            subscription['title'] = '[' + category.group(3) + '] ' + link.group(4) if category else link.group(4)
            subscription['created_time'] = yyyymmdd

            crawling_meta['data'].append(subscription)
            #print "link: %s, postId: %s, title: %s, date: %s" % (link.group(1), link.group(2), link.group(4), yyyymmdd)

    if not crawling_meta['is_meet_last_board_item']:
        crawling_dyeon(crawling_meta, dyeon, page+1)
    else:
        print "last_seq change %d to %d" % (int(crawling_meta['before_last_seq']), int(crawling_meta['after_last_seq']))
        return;

getCategoryMetaDataAndLastSeq()

for idx, dyeon in enumerate(CATEGORY_META_DATA['dyeon']):
    crawling_meta = {
        'last_seq_updated': False,
        'is_meet_last_board_item': False,
        'before_last_seq': dyeon['last_seq'],
        'after_last_seq': dyeon['last_seq'],
        'data': [],
    }
    crawling_dyeon(crawling_meta, dyeon, 1)

