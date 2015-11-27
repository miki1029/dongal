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
                IFNULL(C.last_seq, -1) as last_seq
        FROM dongal.category as A
        LEFT OUTER JOIN dongal.crawling_patterns as B on A.idx = B.category_id
        LEFT OUTER JOIN dongal.crawling_last_seq as C on A.idx = C.category_id
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

def get_urls():
    resp = session.open('https://dyeon.net/board')

    soup = BeautifulSoup(resp, 'html.parser')
    li_list = soup.select("ul.nav-list > li > a") 
    for idx, li in enumerate(li_list):
        if li['href'] != '/club':
            dyeon = {}
            dyeon['url'] = ROOT_URL + li['href']
            dyeon['title'] = (li.contents[0] if len(li.contents) == 1 else li.contents[2]).replace("\n", "").replace("\t", "")
            print dyeon
            #CATEGORY_META_DATA['dyeon'][idx]['url'] = ROOT_URL + li['href']

def crawling_dyeon(dyeon, page):
    print "-------------%s(%d)------------" % (dyeon['title'], dyeon['idx'])
    resp = session.open("%s?page=%d" % (dyeon['url'], page))
    soup = BeautifulSoup(resp, 'html.parser')

    board_list = soup.select('table.bbs > tbody')
    pattern = r'<tr class="(new hot|new|hot|)" style="">(.*?)</tr>'
    
    board_data = re.findall(pattern, str(board_list[0]).replace("\n", ""))

    print "len: %d" % (len(board_item))

    last_seq = dyeon['last_seq']

    for idx, board_item in enumerate(board_data):
        if not secret:
            link = re.search(dyeon['link_pattern'], board_item[1])
            date = re.search(dyeon['created_time_pattern'], board_item[1])
            yyyymmdd = date.group(1) + '-' + date.group(2) + '-' + date.group(3)
            category = re.search(dyeon['category_pattern'], board_item[1])

            if last_seq > link.group(2):
                last_seq = link.group(2)
                break;

            if category:
                print "link: %s, postId: %s, title: [%s] %s, date: %s" % (link.group(1), link.group(2), category.group(3), link.group(4), yyyymmdd)
            else:
                print "link: %s, postId: %s, title: %s, date: %s" % (link.group(1), link.group(2), link.group(4), yyyymmdd)

    
    if last_seq == dyeon['last_seq']:
        crawling_dyeon(dyeon, page+1)
    else:
        return;

#getCategoryMetaDataAndLastSeq()
get_urls()

#for idx, dyeon in enumerate(CATEGORY_META_DATA['dyeon']):
#    crawling_dyeon(dyeon, 1)
