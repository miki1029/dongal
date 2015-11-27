#-*- coding: utf-8 -*-
from bs4 import BeautifulSoup
from pprint import pprint

import requests
import urllib, urllib2, cookielib
import re
import time
import datetime

ROOT_URL = 'https://dyeon.net'
DYEON = []

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
            DYEON.append(dyeon)
            #print "%s: %s" % (ROOT_URL+url, title)


def crawling_dyeon(title, url):
        print "-------------" + title + "-----------------"
        resp = session.open(url)
        soup = BeautifulSoup(resp, 'html.parser')

        subjects = soup.select('table.bbs > tbody > tr.hot > td.subject > div.subject > a')
        dates = soup.select('table.bbs > tbody > tr.hot> td.subject > div.name > span.date')

        for idx, subject in enumerate(subjects):
            print "%s (%s)" % (subject.string, subject['href'])

        pattern = re.compile(r'<span class="absolute">(.*?)</span>')
        for idx, date in enumerate(dates):
            m = pattern.search(str(date))
            print "%d: %s" % (idx, m.group(1).replace("년", "-").replace("월", "-").replace("일", "").replace(" ", ""))
        
        print "-----------------------------------------"
        print "-----------------------------------------"
        print "-----------------------------------------"

get_urls()
for dyeon in DYEON:
    crawling_dyeon(dyeon['title'], dyeon['url'])
