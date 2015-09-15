import httplib2
import os
import time

from apiclient import discovery
import oauth2client
from oauth2client import client
from oauth2client import tools

try:
    import argparse
    flags = argparse.ArgumentParser(parents=[tools.argparser]).parse_args()
except ImportError:
    flags = None

SCOPES = 'https://www.googleapis.com/auth/gmail.readonly'
CLIENT_SECRET_FILE = 'client_secret.json'
APPLICATION_NAME = 'Gmail API Quickstart'

LAST_TIME_UPDATED = 1442338549999
LAST_MESSAGE_UPDATED = "14fd1ef5d425bc8b"

def get_credentials():
    """Gets valid user credentials from storage.

    If nothing has been stored, or if the stored credentials are invalid,
    the OAuth2 flow is completed to obtain the new credentials.

    Returns:
        Credentials, the obtained credential.
    """
    home_dir = os.path.expanduser('~')
    credential_dir = os.path.join(home_dir, '.credentials')
    if not os.path.exists(credential_dir):
        os.makedirs(credential_dir)
    credential_path = os.path.join(credential_dir,
                                   'gmail-quickstart.json')

    store = oauth2client.file.Storage(credential_path)
    credentials = store.get()
    if not credentials or credentials.invalid:
        flow = client.flow_from_clientsecrets(CLIENT_SECRET_FILE, SCOPES)
        flow.user_agent = APPLICATION_NAME
        if flags:
            credentials = tools.run_flow(flow, store, flags)
        else: # Needed only for compatability with Python 2.6
            credentials = tools.run(flow, store)
        print 'Storing credentials to ' + credential_path
    return credentials

def get_bucket(message):
    headers = message['payload']['headers']
    for header in headers:
        key = header.get("name", "Abc")
        if key == "To":
            value = header.get("value")
            email = value.split()[-1][1:-1]
            name =  email.split('@')[0]
            if '+' in name:
                return name.split('+')[1]
    return None

def main():
    """Shows basic usage of the Gmail API.

    Creates a Gmail API service object and outputs a list of label names
    of the user's Gmail account.
    """
    credentials = get_credentials()
    http = credentials.authorize(httplib2.Http())
    service = discovery.build('gmail', 'v1', http=http)


    while True:
        print "Refreshing"

        all_threads = service.users().threads().list(userId='me', q="after:2015/09/15").execute().get('threads', [])
        threads = []
        for thread in all_threads:
            threads.append(service.users().threads().get(userId='me', id=thread['id']).execute())

        with open('dataset.csv', 'w') as f:
            for thread in threads:
                for message in thread['messages']:
                    bucket = get_bucket(message)
                    if bucket is None: continue
                    data = message['payload']['parts'][0]['body']['data']
                    try:
                        data = str(data).decode("base64")
                    except Exception, e:
                        print thread, message, e
                        continue
                    f.write(str(','.join(['None' if bucket is None else bucket, data])))
                    f.write('!@#$%^&*()\n')

        print "Fetched"
        time.sleep(5)


if __name__ == '__main__':
    main()