#-*- coding: utf-8 -*-
from gcm import *

gcm = GCM("AIzaSyALPptuR-rtsonyGG1j5HuPHuAAzRYi-ck")
data = {'title': '동알동알', 'message': '메시지가 10개 도착하였습니다!!'}

reg_id = 'd52yJ10D9Ps:APA91bEl1Pysu7YEkFW8qEaNlqIsM6rvLTHzWWqQufg-2MNGrG5s9RgHmlIvOOq3Lu-ZSgxuz5S3Cfa5G2pJLaZ9MK5-xUquNxdGZirRr4TuNxNQWii6GKUcxi8VIkw2VE1Xmgkzy3de'

gcm.plaintext_request(registration_id=reg_id, data=data)
