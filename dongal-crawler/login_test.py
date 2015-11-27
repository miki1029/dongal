from BeautifulSoup import BeautifulSoup

import requests
import urllib, urllib2, cookielib

ROOT_URL = 'https://dyeon.net/board'
PLAZA_URL = ROOT_URL + '/plaza'
ANOMY_URL = ROOT_URL + '/anonymous'
LOVE_URL = ROOT_URL + '/love'
NIMO_URL = ROOT_URL + '/nimo'
FOREST_URL = ROOT_URL + '/forest'
# 대회 활동
GONG_URL = ROOT_URL + '/gong'
# 교내 단체 소식
ANNOUNCE_URL = ROOT_URL + '/announce'

#URL_NAMES = [
#    '자유 게시판', '익명 게시판', '러브 카운셀러', '니모를 찾아서', '대나무 숲', 
#    '대외 활동', '교내 단체 소식', '취업&알바', '하숙&자취', '광고게시판', '압구정 S&B안과', '필립 치과', '설레임피부과', '제휴업체', 
#    'Dyeon Market', 'job아라!', 'STUDY그룹', '찾아주세요', 'Dyeon 스타일', '어썸피플', '올드보이']

#URL_POST_FIX = ['plaza', 'anonymous', 'love', 'nimo', 'forest', 'gong', 'announce']
#URLS = []

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

    soup = BeautifulSoup(resp)
    print soup.prettify()


get_urls()
