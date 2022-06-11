#import library yang digunakan
import tensorflow as tf
import numpy as np
import csv
import random
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory

#deklarasi list untuk menyimpan label dan feature
labels = []
features = []

#stemmer menggunakan modul Sastrawi
factory = StemmerFactory()
stemmer = factory.create_stemmer()

#extract file dataset ke program
with open('database/dataset.csv') as csvdata:
    reader = csv.reader(csvdata, delimiter=',')
    next(reader)
    for row in reader:
        labels.append(row[0])
        #preprocessing feature
        feature = row[1]
        feature = feature.lower()
        feature = stemmer.stem(feature)
        features.append(feature)

#global variabel yang akan digunakan 
VOCAB_SIZE = 1000
EMBEDDING_DIM = 16
MAXLEN = 25
PADDING = 'post'
OOV_TOKEN = "<OOV>"

#membagi data menjadi train dan test
train_size = int(len(features) * 0.9)

train_feature = features[:train_size]
train_label = labels[:train_size]

test_feature = features[train_size:]
test_label = labels[train_size:]

#tokenization, sequence, dan padding untuk feature
tokenizer = Tokenizer(num_words=VOCAB_SIZE, oov_token="<OOV>")

tokenizer.fit_on_texts(train_feature)

training_sequences = tokenizer.texts_to_sequences(train_feature)
training_padded = pad_sequences(training_sequences, maxlen=MAXLEN, padding='post')

testing_sequences = tokenizer.texts_to_sequences(test_feature)
testing_padded =pad_sequences(testing_sequences, maxlen=MAXLEN, padding='post')

#tokenization dan sequence untuk label
tokenizer_label = Tokenizer()

tokenizer_label.fit_on_texts(labels)

label_train = tokenizer_label.texts_to_sequences(train_label)
label_train_np = np.array(label_train) - 1

label_test = tokenizer_label.texts_to_sequences(test_label)
label_test_np = np.array(label_test) - 1

word_index = tokenizer_label.word_index

#membuat model
if __name__ == "__main__":
    model = tf.keras.Sequential([                       
        tf.keras.layers.Embedding(VOCAB_SIZE, EMBEDDING_DIM, input_length=MAXLEN),
        tf.keras.layers.GlobalAveragePooling1D(),
        tf.keras.layers.Dense(24, activation='relu'),
        tf.keras.layers.Dense(6, activation='softmax')
    ])

    model.compile(loss='sparse_categorical_crossentropy',optimizer='adam',metrics=['accuracy'])
    model.summary()

    #melakukan training
    history = model.fit(training_padded, label_train_np, epochs=200, validation_data=(testing_padded, label_test_np))

    #membuat grafik akurasi dari hasil training
    import matplotlib.pyplot as plt
    
    def plot_graphs(history, metric):
        plt.plot(history.history[metric])
        plt.plot(history.history[f'val_{metric}'])
        plt.xlabel("Epochs")
        plt.ylabel(metric)
        plt.legend([metric, f'val_{metric}'])
        plt.show()
        
    plot_graphs(history, "accuracy")
    plot_graphs(history, "loss")
