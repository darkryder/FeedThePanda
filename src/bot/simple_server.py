import json
from flask import Flask

app = Flask(__name__)


@app.route('/categories.json')
def categories():
    labels_to_data = {}

    with open('dataset.csv', 'r') as f:
        p = f.read()
    for collect in p.split("!@#$%^&*()"):
        # import pdb; pdb.set_trace()
        collect = collect.strip()
        collect = collect.split(',')
        label = collect[0]
        data = ''.join(collect[1:])

        if label == '' and data == '': continue

        data_collection = labels_to_data.get(label, [])
        data_collection.append(data)
        labels_to_data[label] = data_collection
    return json.dumps(labels_to_data.keys())



@app.route('/allPosts.json')
def serve():
    labels_to_data = {}

    with open('dataset.csv', 'r') as f:
        p = f.read()
    for collect in p.split("!@#$%^&*()"):
        # import pdb; pdb.set_trace()
        collect = collect.strip()
        collect = collect.split(',')
        label = collect[0]
        data = ''.join(collect[1:])

        if label == '' and data == '': continue

        data_collection = labels_to_data.get(label, [])
        data_collection.append(data)
        labels_to_data[label] = data_collection
    return json.dumps(labels_to_data)


if __name__ == '__main__':
    app.run(debug=True)