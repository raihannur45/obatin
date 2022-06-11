#import library yang digunakan
from flask import Flask, request, jsonify
import string
from chatbot_model import*

#memuat model yang telah dibuat sebelumnya
new_model = tf.keras.models.load_model('saved_model/my_model')
new_model.summary()
loss, acc = new_model.evaluate(testing_padded, label_test_np)

#memuat stopwords bahasa indonesia
stopwords = []
with open('database/stopwords.txt') as txt_stopwords:
  for word in txt_stopwords:
    word = word.strip()
    stopwords.append(word)

#memuat list yang berisi nama-nama obat di pasaran ke program
list_namaobat = []
list_namaobat_lower = []
with open('database/nama-obat.txt') as txtnamaobat:
  for namaobat in txtnamaobat:
    namaobat = namaobat.strip()
    namaobatlower = namaobat.lower()
    list_namaobat_lower.append(namaobatlower)

#memuat file yang berisi respon dari bot untuk setiap label
responses = {}
tag = ''
with open('database/data_respon.csv') as csvdata:
  readcsv = csv.reader(csvdata, delimiter=',')
  for row in readcsv:
    if tag == row[0]:
      responses[row[0]].append(row[1])
    else:
      newlist = []
      newlist.append(row[1])
      responses[row[0]] = newlist

    tag = row[0]

#memuat file yang berisi database obat yang dimiliki
listobat = {}
with open('database/data_obat.csv') as csvdata:
  readcsv = csv.reader(csvdata, delimiter=';')
  for row in readcsv:
    newdict = {}
    newdict['kandungan'] = row[1]
    newdict['indikasi'] = row[2]
    newdict['efeksamping'] = row[3]
    newdict['cara'] = row[4]
    listobat[row[0]] = newdict

#label-label yang digunakan untuk mencari data di database obat
search = ['efeksamping', 'cara', 'kandungan', 'indikasi']

#inisialisasi Flask
app = Flask(__name__)

#endpoint untuk memprediksi input user
@app.route("/bot")
def chatbot():
  nama_obat = ''
  search_database = 0
  input_user = []

  user_input = request.args.get('text')
  user_input = user_input.lower()
  for char in user_input:
    if char in string.punctuation:
        user_input = user_input.replace(char, "")
  user_input = stemmer.stem(user_input)
  input_user.append(user_input)

  user_input_token = tokenizer.texts_to_sequences(input_user)
  user_input_token = pad_sequences(user_input_token, maxlen=MAXLEN, padding='post')

  predik = new_model.predict(user_input_token)
  if np.amin(predik) > 0.001:
    label_output = 'tidakpaham'
  else:
    output = predik.argmax() + 1
    for k,v in word_index.items():
      if v == output:
        label_output = k

  if label_output in search:
    list_user_input = list(user_input.split())
    for obat in list_namaobat_lower:
      if any(word for word in list_user_input if (obat in word or word in obat) and word not in stopwords):
        nama_obat = obat
        search_database = 1
        break

  return jsonify(label = label_output, namaobat = nama_obat, status = str(search_database))

#endpoint untuk mencari data di database obat yang dimiliki
@app.route("/database")
def search_database():
  ditemukan = 0
  jawaban = ''
  req_label = request.args.get('label')
  req_nama_obat = request.args.get('namaobat')
  for obat in listobat:
    if req_nama_obat.lower() in obat.lower() or obat.lower() in req_nama_obat.lower():
      jawaban = jawaban + obat + '\n' + listobat[obat][req_label] + '\n'
      ditemukan = 1
  if ditemukan == 0:
    jawaban = 'Data obat ' + req_nama_obat + ' tidak ditemukan'
  else:
    jawaban = jawaban[:(len(jawaban) - 1)]
  return jsonify(jawab = jawaban)

#endpoint untuk mencari respon bot sesuai label yang diprediksi
@app.route("/respon")
def respon_bot():
  req_label = request.args.get('label')
  jawaban = random.choice(responses[req_label])
  return jsonify(jawab = jawaban)

if __name__ == '__main__':
    app.run(debug=True, port=5000)
