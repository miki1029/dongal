from BeautifulSoup import BeautifulSoup

import requests
import urllib, urllib2, cookielib

username = 'kang8530'
password = 'crawler'

rootUrl = 'https://dyeon.net'
loginUrl = rootUrl + '/user/login'
plazaUrl = rootUrl + '/board/plaza'

cj = cookielib.CookieJar()
opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
login_data = urllib.urlencode({'user[login]' : username, 'user[password]' : password})
opener.open(loginUrl, login_data)
resp = opener.open(plazaUrl)

soup = BeautifulSoup(resp)
print soup.prettify()

