----
##1. Default Setup
sudo pip install virtualenv
virtualenv venv
virtualenv -p /usr/bin/python2.7 venv
source venv/bin/activate
pip install -r requirements.txt

----
##2. MySQL Setup
dyeon.py 파일의 mysql connection 부분 수정

----
##3. How to run(현재는 디연 크롤러만 정상 작동)
python dyeon.py
