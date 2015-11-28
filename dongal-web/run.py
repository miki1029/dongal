from flask import Flask, render_template, request

import simplejson as json
import requests

app = Flask(__name__)
userIdx = '13'

# BASE_URL = "http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/"
BASE_URL = "http://localhost:8080/view/"

@app.route("/index")
def index():
    return render_template("index2.html", title="Index")

@app.route("/joinSelect")
def join_select():
    return render_template("join_select.html", title="Join Select")

@app.route("/joinMail")
def join_by_mail():
    return render_template("join_mail.html", title="Join - Mail")

@app.route("/join")
def join():
    return render_template("join.html", title="Join")

@app.route("/")
@app.route("/home")
def home():
    data = json.loads(requests.get(url=BASE_URL+"home?userIdx="+userIdx).text)
    return render_template("home.html", title="Home", userInfo=data['userInfo'], alarms=data['posts'])

@app.route("/list")
def list():
    data = json.loads(requests.get(url=BASE_URL+"list?userIdx="+userIdx).text)
    return render_template("list.html", title="List", favorites=data['posts'])

@app.route("/favorite")
def favorite():
    data = json.loads(requests.get(url=BASE_URL+"favorite?userIdx="+userIdx).text)
    return render_template("favorite.html", title="Favorite", favorites=data['posts'])

@app.route("/more")
def more():
    return render_template("more.html", title="More")

@app.route("/settings")
def settings():
    data = json.loads(requests.get(url=BASE_URL+"settings?userIdx="+userIdx).text)
    return render_template("settings.html", title="Settings", official=data['official'], dyeon=data['dyeon'])

if __name__ == "__main__":
    app.run(debug=True)
