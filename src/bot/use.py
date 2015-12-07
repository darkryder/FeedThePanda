# https://www.google.com/settings/security/lesssecureapps

import time
import os
import gmail as G
import requests
from datetime import date
import string

username = os.getenv("app_email_user")
password = os.getenv("app_email_pwd")
gmail = G.login(username, password)
url = "http://192.168.51.207:5000/api/v1/add_from_email"

ALLOWED_SENDER = os.getenv("app_sender_email")
f = open("uids.log", 'r')
SENT_EMAIL_UIDS = set(map(string.strip ,f.readlines()))
f.close()
print SENT_EMAIL_UIDS

def poll():
    mails = gmail.inbox().mail(
        after=date(2012, 12, 5),
        sender=ALLOWED_SENDER,
        # unread=True
        prefetch=True)
    for mail in mails:
        if "+" in mail.to:
            if mail.uid in SENT_EMAIL_UIDS: continue
            channel = mail.to.split("+")[1].split("@")[0]
            title = mail.subject
            description = mail.body.strip()
            print "Sending", channel, title, description
            resp = requests.post(url, data={
                'channel': channel, 'post_title': time, 'post_description': description})
            if resp.json()["status"] == 200:
                with open("uids.log", 'a+') as f:
                    f.write(mail.uid + "\n")
                SENT_EMAIL_UIDS.add(mail.uid)

while True:
    try:
        poll()
        time.sleep(1)
    except Exception, e:
        print e
        pass
