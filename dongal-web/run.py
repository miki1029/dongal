from flask import Flask, render_template

import json, requests

app = Flask(__name__)

BASE_URL = "http://dna.dongguk.ac.kr/~felika/dongal/dongal-backend/"

@app.route("/")
@app.route("/home")
def home():
    data = json.loads(requests.get(url=BASE_URL+"home.php").text)['data']
    return render_template("home.html", title="Home", userInfo=data['userInfo'], alarms=data['alarms'])

@app.route("/list")
def list():
    data = json.loads(requests.get(url=BASE_URL+"favorite.php").text)['data']
    return render_template("list.html", title="List", favorites=data['favorites'])

@app.route("/favorite")
def favorite():
    data = json.loads(requests.get(url=BASE_URL+"favorite.php").text)['data']
    return render_template("favorite.html", title="Favorite", favorites=data['favorites'])

@app.route("/more")
def more():
    return render_template("more.html", title="More")

if __name__ == "__main__":
    app.run(debug=True)
