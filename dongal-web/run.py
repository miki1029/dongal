from flask import Flask, render_template, request, session, redirect, url_for

import simplejson as json
import requests

SECRET_KEY = 'dongal'

app = Flask(__name__)
app.config.from_object(__name__)

# BASE_URL = "http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/"
ROOT_BASE_URL = "http://localhost:8080/"
SESSION_BASE_URL = ROOT_BASE_URL + "session/"
VIEW_BASE_URL = ROOT_BASE_URL + "view/"

# @app.before_request
# def load_user():
#     session["userIdx"] = "2"

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
    data = json.loads(requests.get(url=SESSION_BASE_URL + "login?email=" + email + "&password=" + password).text)
    session["userIdx"] = str(data["idx"])
    return redirect(url_for('home'))

@app.route("/logout")
def logout():
    session.clear()
    return redirect(url_for('index'))

@app.route("/joinSelect")
def join_select():
    return render_template("join_select.html", title="Join Select")

@app.route("/joinMail")
def join_by_mail():
    return render_template("join_mail.html", title="Join - Mail")

@app.route("/joinProcess", methods=['POST'])
def join_process():
    email = request.form['email']
    password = request.form['password']
    name = request.form['name']
    print email + ',' + password + ',' + name
    data = json.loads(requests.get(url=SESSION_BASE_URL + "join?email=" + email + "&password=" + password + "&name=" + name).text)
    session["userIdx"] = str(data["idx"])
    return redirect(url_for('home'))

@app.route("/join")
def join():
    return render_template("join.html", title="Join")

@app.route("/home")
def home():
    data = json.loads(requests.get(url=VIEW_BASE_URL + "home?userIdx=" + session["userIdx"]).text)
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
    app.run(debug=True)
