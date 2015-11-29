#-*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from pprint import pprint
from gcm import *

import simplejson as json

import logging
from logging.handlers import SMTPHandler

import requests
import urllib, urllib2, cookielib, ssl
import re
import MySQLdb
import datetime

logging.basicConfig(level=logging.ERROR, filename='crawler.log')

CATEGORY_META_DATA = {
    'dgu' : [],
    'dyeon' : []
}

def emit(self, record):
    """
    Overwrite the logging.handlers.SMTPHandler.emit function with SMTP_SSL.
    Emit a record.
    Format the record and send it to the specified addressees.
    """
    try:
        import smtplib
        from email.utils import formatdate
        port = self.mailport
        if not port:
            port = smtplib.SMTP_PORT
        smtp = smtplib.SMTP_SSL(self.mailhost, port, timeout=self._timeout)
        msg = self.format(record)
        msg = "From: %s\r\nTo: %s\r\nSubject: %s\r\nDate: %s\r\n\r\n%s" % (self.fromaddr, ", ".join(self.toaddrs), self.getSubject(record), formatdate(), msg)
        if self.username:
            smtp.ehlo()
            smtp.login(self.username, self.password)
        smtp.sendmail(self.fromaddr, self.toaddrs, msg)
        smtp.quit()
    except (KeyboardInterrupt, SystemExit):
        raise
    except:
        self.handleError(record)

def write_log(msg):
    today = datetime.datetime.today() 
    logFormat = "[ERROR]\t%s\t%s\n" % (today.strftime('%Y-%m-%d %H:%M:%S'), msg) 
    f = open("error.log", 'a')
    f.write(logFormat) 

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
    if page == 1:
        print "---------------디연 > %s 크롤링 시작------------" % (dyeon['name'])

    resp = session.open(crawling_url, timeout = 10)

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

            print "제목: %s, 링크: %s, 날짜: %s" % (subscription['title'], subscription['link'], subscription['created_time'])

    if not dyeon['is_meet_last_board_item']:
        parsingSubscriptionData(dyeon, page+1)
    else:
        print "---------------디연 > %s 크롤링 종료, %s개 업데이트------------" % (dyeon['name'], len(dyeon['subscriptions']))
        #print "last_seq change %d to %d" % (int(dyeon['before_last_seq']), int(dyeon['after_last_seq']))
        return;

def parsingSubscriptionDataDGU(dgu, page):
    crawling_url = dgu['url'] + "&spage=" + str(page)
    if page == 1:
        print "---------------동국대학교 홈페이지 > %s 크롤링 시작------------" % (dgu['name'])

    handle = urllib2.urlopen(crawling_url, timeout = 10)

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
            subscription['link'] = 'https://www.dongguk.edu/mbs/kr/jsp/board/' + board_item.group(1).replace("&amp;", "&")
            subscription['boardId'] = board_item.group(2)
            subscription['postId'] = board_item.group(3)
            subscription['title'] = board_item.group(4)
            subscription['created_time'] = board_item.group(7).replace(" ", "").replace("\t", "")
            dgu['subscriptions'].append(subscription)

            print "제목: %s, 링크: %s, 날짜: %s" % (subscription['title'], subscription['link'], subscription['created_time'])


    if not dgu['is_meet_last_board_item']:
        parsingSubscriptionDataDGU(dgu, page+1)
    else:
        print "---------------동국대학교 홈페이지 > %s 크롤링 종료, %s개 업데이트------------" % (dgu['name'], len(dgu['subscriptions']))
        #print "last_seq change %d to %d" % (int(dgu['before_last_seq'], int(dgu['after_last_seq']))
        return;

def crawling():
    # crawling DGU 
    print "----------- 동국대학교 홈페이지 크롤링 시작----------------"
    for idx, dgu in enumerate(CATEGORY_META_DATA['dgu']):
        parsingSubscriptionDataDGU(dgu, 1)
    print "----------- 동국대학교 홈페이지 크롤링 종료----------------"

    # crawling dyeon
    print "----------- 디연 홈페이지 크롤링 시작----------------"
    for idx, dyeon in enumerate(CATEGORY_META_DATA['dyeon']):
        parsingSubscriptionData(dyeon, 1)
    print "----------- 디연 홈페이지 크롤링 종료----------------"

def sendPushMessage():
    print "----------- 푸시 메시지 보내기 시작----------------"
    title = "동알동알 알림도착"
    message = "%s님, 알림 항목 %d 건이 업데이트 되었습니다."

    cursor = db.cursor()
    # Get category metadata
    cursor.execute("""
        select 
        device_key,
            GROUP_CONCAT(category_id) as category_idxes, a.name
            from dongal.user a
            inner join dongal.user_category_settings b on a.idx = b.user_id
            group by b.user_id
    """)
    data = cursor.fetchall()
    
    gcm = GCM("AIzaSyALPptuR-rtsonyGG1j5HuPHuAAzRYi-ck")
    users = []
    for row in data:
        user = {}
        user['device_token'] = str(row[0])
        user['category_idxes'] = str(row[1]).split(",")
        user['sub_count'] = 0
        user['name'] = str(row[2])
        users.append(user)

    cursor.close()

    for dgu in CATEGORY_META_DATA['dgu']:
        for user in users:
            if any(str(dgu['idx']) in s for s in user['category_idxes']): 
                #print "%s 업데이트 갯수: %s" % (dgu['name'], len(dgu['subscriptions'])
                user['sub_count'] = user['sub_count'] + len(dgu['subscriptions'])
        
    for dyeon in CATEGORY_META_DATA['dyeon']:
        for user in users:
            if any(str(dyeon['idx']) in s for s in user['category_idxes']): 
                #print "%s 업데이트 갯수: %s" % (dyeon['name'], len(dyeon['subscriptions'])
                user['sub_count'] = user['sub_count'] + len(dyeon['subscriptions'])

    for user in users:
        #print "device_token: %s, category_idxes: %s, sub_count: %s" % (user['device_token'], user['category_idxes'], user['sub_count'])
        if user['sub_count'] > 0 and (not user['device_token'] == ""):
            user['data'] = {}
            user['data']['title'] = title
            user['data']['message'] = message.replace("%d", str(user['sub_count'])).replace("%s", str(user['name']))
            print "%s에게 %s 전송" % (user['name'], user['data']['message'])
            canonical_id = gcm.plaintext_request(registration_id=str(user['device_token']), data = user['data'])
            if canonical_id:
                # Repace reg_id with canonical_id in your database
                entry = entity.filter(registration_id=reg_id)
                entry.registration_id = canonical_id
                entry.save()

    print "----------- 푸시 메시지 보내기 종료----------------"

txt = open("mysql.json")

json = json.loads(txt.read())
db = MySQLdb.connect(json['host'], json['user'], json['password'], json['db'])

getCategoryMetaDataAndLastSeq()

try:
    crawling()
except Exception as ex:
    logging.exception("[ERROR] Dongal Crawler Error Occured")
    
saveSubscription()
sendPushMessage()

db.close()


