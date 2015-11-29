# -*- coding: utf-8 -*-
from flask import Flask, render_template, request, session, redirect, url_for
from dongal import *

import simplejson as json
import requests

SECRET_KEY = 'dongal'

app = Flask(__name__)
app.config.from_object(__name__)

# ROOT_BASE_URL = "http://192.168.0.151:8080/"
ROOT_BASE_URL = "http://localhost:8080/"
SESSION_BASE_URL = ROOT_BASE_URL + "session/"
VIEW_BASE_URL = ROOT_BASE_URL + "view/"

@app.route("/")
@app.route("/index")
def index():
    if hasattr(session, 'userIdx'):
        return redirect("home")
    else:
        return render_template("index2.html", title="Index")

@app.route("/login")
def login():
    return render_template("login.html", title="Login")

@app.route("/loginProcess", methods=['POST'])
def login_process():
    email = request.form['email']
    password = request.form['password']
    try:
        data = json.loads(requests.get(url=SESSION_BASE_URL + "login?email=" + email + "&password=" + password).text)
        session["userIdx"] = str(data["idx"])
        if data['dguVerified']:
            session["userIdx"] = str(data["idx"])
            return redirect(url_for('home'))
        else:
            return render_template("notVerified.html", userIdx = session["userIdx"], email = email, password = password)
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
    # Send Verify email
    init_mail(session["userIdx"], session["email"])
    send_mail(session["email"])
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
        session["userIdx"] = str(data["idx"])
        session["email"] = email
        # Send Verify email
        init_mail(session["userIdx"], session["email"])
        send_mail(session["email"])
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
    return render_template("join.html", title="Join")

@app.route("/home")
def home():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "home?userIdx=" + session["userIdx"]).text)
    if len(data['posts']) == 0 :
        return redirect(url_for('settings'))
    return render_template("home.html", title="Home", userInfo=data['userInfo'], alarms=data['posts'])

@app.route("/list")
def list():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "list?userIdx=" + session["userIdx"]).text)
    return render_template("list.html", title="List", favorites=data['posts'])

@app.route("/favorite")
def favorite():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "favorite?userIdx=" + session["userIdx"]).text)
    return render_template("favorite.html", title="Favorite", favorites=data['posts'])

@app.route("/more")
def more():
    return render_template("more.html", title="More")

@app.route("/settings")
def settings():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "settings?userIdx=" + session["userIdx"]).text)
    return render_template("settings.html", title="Settings", official=data['official'], dyeon=data['dyeon'])

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=9090, debug=True)
