from gcm import *

gcm = GCM("AIzaSyALPptuR-rtsonyGG1j5HuPHuAAzRYi-ck")
data = {'title': 'GCM Test', 'message': 'What the FUck!!'}

reg_id = 'c1rXgtHcyE0:APA91bFByzGxNk1tsazcmjI6qW1t2CmipBKgMUdQi10d1NoYNq1UQvBtVFxWp-jbU-LOMiJYJSjIHJAvuCdSYvm8HCnoipZpYD0h2UmClTlq63NCWSv2YlQIYF89SD__ou1KDXHpn7ML'

gcm.plaintext_request(registration_id=reg_id, data=data)
