# -*- coding: utf-8 -*-
from flask import Flask, render_template, request, session, redirect, url_for
from dongal import *

import simplejson as json
import requests
import ConfigParser

SECRET_KEY = 'dongal'

app = Flask(__name__)
app.config.from_object(__name__)

config = ConfigParser.RawConfigParser()
config.read('ConfigFile.properties')
ROOT_BASE_URL = config.get('HostSection', 'host.name')
SESSION_BASE_URL = ROOT_BASE_URL + "session/"
VIEW_BASE_URL = ROOT_BASE_URL + "view/"

@app.route("/")
@app.route("/index")
def index():
    if hasattr(session, 'userIdx'):
        return redirect("home")
    else:
        return render_template("index2.html", title="Index", root_url=ROOT_BASE_URL)

@app.route("/login")
def login():
    return render_template("login.html", title="Login", root_url=ROOT_BASE_URL)

@app.route("/loginProcess", methods=['POST'])
def login_process():
    email = request.form['email']
    password = request.form['password']
    try:
        data = json.loads(requests.get(url=SESSION_BASE_URL + "login?email=" + email + "&password=" + password).text)
        session["userIdx"] = str(data["idx"])
        session["email"] = request.form['email']
        session["lastLoginTime"] = str(data["lastLoginTime"])
        requests.get(url=SESSION_BASE_URL + "updateLoginTime?userIdx=" + session["userIdx"])
        print 'loginProcess ~ session["userIdx"]=' + session["userIdx"]
        if data['dguVerified']:
            session["userIdx"] = str(data["idx"])
            return redirect(url_for('home'))
        else:
            return render_template("notVerified.html", userIdx = session["userIdx"], email = email, password = password, root_url=ROOT_BASE_URL)
    except json.scanner.JSONDecodeError as e:
        return '''
            <script>
                alert('이메일 또는 비밀번호를 확인해 주세요.');
                location.href='login';
            </script>
        '''

@app.route("/logout")
def logout():
    session.clear()
    return redirect(url_for('index'))

@app.route("/sendVerifyEmail")
def send_verify_mail():
    print 'sendVerifyEmail ~ session["userIdx"]=' + session["userIdx"]
    # Send Verify email
    init_mail(request["userIdx"], request["email"], ROOT_BASE_URL)
    send_mail(request["email"])
    data = {'result': 'success'}
    return json.dumps(data)

@app.route("/joinProcess", methods=['POST'])
def join_process():
    email = request.form['email']
    password = request.form['password']
    name = request.form['name']
    deviceKey = request.form['deviceKey']
    try:
        data = json.loads(requests.get(url=SESSION_BASE_URL + "join?email=" + email + "&password=" + password + "&name=" + name + "&deviceKey=" + deviceKey).text)
        print data
        session["userIdx"] = str(data["idx"])
        session["email"] = email
        session["lastLoginTime"] = str(data["lastLoginTime"])
        # Send Verify email
        init_mail(str(data["idx"]), email, ROOT_BASE_URL)
        send_mail(email)
        return '''
            <script>
                alert('인증 이메일이 전송되었습니다.\\n인증 후 로그인 해 주세요.');
                location.href='login';
            </script>
        '''
    except json.scanner.JSONDecodeError as e:
        return '''
            <script>
                alert('이미 가입된 이메일입니다');
                location.href='join';
            </script>
        '''

@app.route("/join")
def join():
    return render_template("join.html", title="Join", root_url=ROOT_BASE_URL)

@app.route("/home")
def home():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "home?userIdx=" + session["userIdx"] + "&timestamp=" + session["lastLoginTime"]).text)
    if len(data['posts']) == 0 :
        return redirect(url_for('settings'))
    return render_template("home.html", title="Home", userInfo=data['userInfo'], alarms=data['posts'], root_url=ROOT_BASE_URL)

@app.route("/list")
def list():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "list?userIdx=" + session["userIdx"]).text)
    return render_template("list.html", title="List", favorites=data['posts'], root_url=ROOT_BASE_URL)

@app.route("/favorite")
def favorite():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "favorite?userIdx=" + session["userIdx"]).text)
    return render_template("favorite.html", title="Favorite", favorites=data['posts'], root_url=ROOT_BASE_URL)

@app.route("/more")
def more():
    return render_template("more.html", title="More", root_url=ROOT_BASE_URL)

@app.route("/settings")
def settings():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "settings?userIdx=" + session["userIdx"]).text)
    return render_template("settings.html", title="Settings", official=data['official'], dyeon=data['dyeon'], root_url=ROOT_BASE_URL)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9090, debug=True)
