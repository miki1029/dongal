#-*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from pprint import pprint

import simplejson as json

import requests
import urllib, urllib2, cookielib
import re
import MySQLdb

CATEGORY_META_DATA = {
    'dgu' : [],
    'dyeon' : []
}

def getCategoryMetaDataAndLastSeq():
    cursor = db.cursor()
    # Get category metadata
    cursor.execute("""
        SELECT  A.idx, A.name,
                B.title_pattern, B.created_time_pattern, B.secret_pattern, B.category_pattern,
                IFNULL(B.last_seq, -1), B.url, A.top_id
        FROM dongal.category as A
        INNER JOIN dongal.crawling_meta as B on A.idx = B.category_id
    """)
    data = cursor.fetchall()
    
    for row in data:
        category = {}
        category['idx'] = int(row[0])
        category['name'] = row[1]
        category['title_pattern'] = row[2]
        category['created_time_pattern'] = row[3]
        category['secret_pattern'] = row[4]
        category['category_pattern'] = row[5]
        category['before_last_seq'] = row[6]
        category['after_last_seq'] = row[6]
        category['url'] = row[7]
        category['last_seq_updated'] = False
        category['is_meet_last_board_item'] = False
        category['subscriptions'] = []
        if int(row[8]) == 1:
            CATEGORY_META_DATA['dgu'].append(category)
        else:
            CATEGORY_META_DATA['dyeon'].append(category)

    cursor.close()

def updateLastSeq():
    cursor = db.cursor()
    sql = "UPDATE dongal.crawling_meta SET last_seq = %s WHERE category_id = %s"
    param_dgu = [ (dyeon['after_last_seq'], dyeon['idx'] ) for dyeon in CATEGORY_META_DATA['dgu'] ]
    param_dyeon = [ (dyeon['after_last_seq'], dyeon['idx'] ) for dyeon in CATEGORY_META_DATA['dyeon'] ]
    params = param_dgu + param_dyeon

    try:
        cursor.executemany(sql, params)
        db.commit()
    except:
        cursor.executemany(sql, params)

    cursor.close()

def insertSubscriptions():
    cursor = db.cursor()
    params = []
    for idx, dyeon in enumerate(CATEGORY_META_DATA['dgu']):
        param = [ (dyeon['idx'], subscription['title'], subscription['link'], subscription['created_time']) for subscription in dyeon['subscriptions'] ]
        params += param
        
    for idx, dyeon in enumerate(CATEGORY_META_DATA['dyeon']):
        param = [ (dyeon['idx'], subscription['title'], subscription['link'], subscription['created_time']) for subscription in dyeon['subscriptions'] ]
        params += param

    sql = "INSERT INTO dongal.subscription(category_id, title, url, created_time) VALUES(%s, %s, %s, %s)"
    try:
        cursor.executemany(sql, params)
        db.commit()
    except:
        cursor.executemany(sql, params)

    cursor.close()

def saveSubscription():
    insertSubscriptions()
    updateLastSeq()

def login_dyeon():
    username = 'kang8530'
    password = 'crawler'

    loginUrl = 'https://dyeon.net/user/login'

    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    login_data = urllib.urlencode({'user[login]' : username, 'user[password]' : password})
    opener.open(loginUrl, login_data)

    return opener



session = login_dyeon()

def parsingSubscriptionData(dyeon, page):
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

            if int(dyeon['before_last_seq']) >= int(link.group(2)):
                dyeon['is_meet_last_board_item'] = True
                break;

            if (not dyeon['last_seq_updated']) and (int(dyeon['after_last_seq']) < int(link.group(2))):
                dyeon['after_last_seq'] = int(link.group(2))
                dyeon['last_seq_updated'] = True

            subscription = {}
            subscription['link'] = link.group(1)
            subscription['postId'] = link.group(2)
            subscription['title'] = '[' + category.group(3) + '] ' + link.group(4) if category else link.group(4)
            subscription['created_time'] = yyyymmdd
            dyeon['subscriptions'].append(subscription)

            #print "link: %s, postId: %s, title: %s, date: %s" % (link.group(1), link.group(2), link.group(4), yyyymmdd)

    if not dyeon['is_meet_last_board_item']:
        parsingSubscriptionData(dyeon, page+1)
    else:
        print "last_seq change %d to %d" % (int(dyeon['before_last_seq']), int(dyeon['after_last_seq']))
        return;

def parsingSubscriptionDataDGU(dgu, page):
    crawling_url = dgu['url'] + "&spage=" + str(page)
    print "-------------%s(%d): %s------------" % (dgu['name'], dgu['idx'], crawling_url)
    handle = urllib2.urlopen(crawling_url)
    data = handle.read()
    soup = BeautifulSoup(data, 'html.parser')

    output = {}
    subscriptions = []

    board_list = soup.select('table#board_list > tbody > tr')
    for idx, board_item in enumerate(board_list):
        board_item_pattern = re.compile(dgu['title_pattern'])
        
        board_item = re.search(board_item_pattern, str(board_item).replace("\n", ""))

        if board_item:
            if int(dgu['before_last_seq']) >= int(board_item.group(3)):
                dgu['is_meet_last_board_item'] = True
                break;

            if (not dgu['last_seq_updated']) and (int(dgu['after_last_seq']) < int(board_item.group(3))):
                dgu['after_last_seq'] = int(board_item.group(3))
                dgu['last_seq_updated'] = True

            subscription = {}
            subscription['link'] = 'https://www.dongguk.edu/mbs/kr/jsp/board/' + board_item.group(1)
            subscription['boardId'] = board_item.group(2)
            subscription['postId'] = board_item.group(3)
            subscription['title'] = board_item.group(4)
            subscription['created_time'] = board_item.group(7).replace(" ", "").replace("\t", "")
            dgu['subscriptions'].append(subscription)

    if not dgu['is_meet_last_board_item']:
        parsingSubscriptionDataDGU(dgu, page+1)
    else:
        print "last_seq change %d to %d" % (int(dgu['before_last_seq']), int(dgu['after_last_seq']))
        return;

def crawling():
    # crawling dyeon
    for idx, dgu in enumerate(CATEGORY_META_DATA['dgu']):
        parsingSubscriptionDataDGU(dgu, 1)

    # crawling dyeon
    for idx, dyeon in enumerate(CATEGORY_META_DATA['dyeon']):
        parsingSubscriptionData(dyeon, 1)


txt = open("mysql.json")

json = json.loads(txt.read())
db = MySQLdb.connect(json['host'], json['user'], json['password'], json['db'])

getCategoryMetaDataAndLastSeq()

crawling()

saveSubscription()

db.close()



