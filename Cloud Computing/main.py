from io import BytesIO
import base64
from flask import Flask, render_template, request, jsonify
from PIL import Image
from tensorflow import keras
import tensorflow as tf
import numpy as np
import json
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'


app = Flask(__name__)


@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        file = request.get_json('file')
        imagefix = Image.open(BytesIO(base64.b64decode(file['base64'])))
        if file['base64'] == "":
            return jsonify({"error": "no file"})
        try:
            model = keras.models.load_model("Model.h5")
            train_dir = os.path.join('dataset/train')
            from keras.preprocessing.image import ImageDataGenerator
            train_datagen = ImageDataGenerator(
                rescale=1.0/255.,
                width_shift_range=0.2,
                height_shift_range=0.2,
                shear_range=0.2,
                rotation_range=40,
                zoom_range=0.2,
                horizontal_flip=True,
                fill_mode='nearest')
            train_generator = train_datagen.flow_from_directory(train_dir,
                                                                batch_size=27,
                                                                class_mode='categorical',
                                                                target_size=(224, 224))

            class_dict = {v: k for k,
                          v in train_generator.class_indices.items()}
            imagefix = tf.image.resize(imagefix, [224, 224])
            x = keras.preprocessing.image.img_to_array(imagefix)/255
            x = np.expand_dims(x, axis=0)
            predict = model.predict(x)
            class_prediction = np.argmax(predict)
            prediction = class_dict[class_prediction]
            if str(prediction) == "blackhead":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Blackhead adalah jenis komedo yang tampak seperti pori-pori yang membesar dan menghitam. Terjadi ketika pori-pori membesar dan terbuka ke permukaan kulit dan ke kelenjar minyak, lalu teroksidasi oleh udara dan berubah menjadi warna hitam/coklat. Komedo jenis ini adalah komedo yang paling sering dialami oleh kebanyakan orang.",
                    "solusi": [
                        "Menggunakan produk dengan asam salisilat",
                        "Gunakan Krim dan Losion Retinoid",
                        "Lakukan Chemical Peeling"
                    ],
                    "saran": [
                        "Wardah Acnederm Pore Blackhead Balm",
                        "Neutrogena Deep Clean Blackhead Eliminating Scrub",
                        "Innisfree Super Volcanic Pore Clay Mask"
                    ]
                }

            if str(prediction) == "whitehead":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Whitehead adalah jenis komedo yang tampak seperti bintik-bintik kecil yang berwarna putih atau kuning. Komedo jenis ini terjadi ketika minyak dan bakteri terperangkap di bawah permukaan kulit lalu mengeras.",
                    "solusi": [
                        "Menggunakan Produk sabun pembersih wajah atau salep yang mengandung benzoyl peroxide"
                    ],
                    "saran": [
                        "COSRX AHA 7 Whitehead Power Liquid",
                        "Klairs Rich Moist Soothing Tencel Sheet Mask"
                    ]
                }
            if str(prediction) == "folikulitis":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Folikulitis adalah peradangan pada folikel rambut atau tempat rambut tumbuh. Kondisi ini biasanya disebabkan oleh infeksi bakteri atau jamur. Meski sering kali tidak berbahaya, folikulitis bisa memburuk dan menyebabkan rambut hilang secara permanen.",
                    "solusi": [
                        "Untuk mengatasi folikulitis yang disebabkan oleh infeksi jamur, dokter akan memberikan obat antijamur dalam bentuk krim, sampo, atau tablet"
                    ],
                    "saran": [
                        "Krim anti bakteri"
                    ]
                }
            if str(prediction) == "melasma":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Melasma (dikenal juga dengan chloasma) adalah jenis penyakit kulit yang ditandai dengan munculnya bercak-bercak hiperpegmentasi pada wajah. Terkadang, bercak ini juga terlihat pada daerah yang sering terpapar sinar matahari seperti leher dan lengan bawah. Terkadang, penyakit ini disebut “mask of pregnancy” karena kejadiannya cukup umum di kalangan ibu hamil. Namun, bercak yang muncul pada masa kehamilan biasanya akan menghilang setelah ibu melahirkan.",
                    "solusi": [
                        ""
                    ],
                    "saran": [
                        "hydroquinone",
                        "Tretinoin dan kortikosteroid"
                    ]
                }
            if str(prediction) == "nodules":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Jerawat nodul adalah jenis jerawat di bawah permukaan kulit yang terasa keras dan sakit. Tidak seperti jerawat biasa yang dapat sembuh dalam beberapa hari, jerawat ini dapat berlangsung selama berminggu-minggu, bahkan berbulan-bulan. Biasanya, jerawat ini berukuran cukup besar, sehingga terlihat jelas jika terjadi di wajah. Kondisi ini tentu mengurangi tampilan kulit dan membuat kebanyakan orang menjadi kurang percaya diri. Jerawat yang termasuk jerawat parah ini akan terasa keras ketika disentuh dan tidak dapat diobati dengan obat tanpa resep",
                    "solusi": [
                        ""
                    ],
                    "saran": [
                        "Antibiotik",
                        "Isotretinoin"
                    ]
                }
            if str(prediction) == "papula":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Papula adalah salah satu jenis jerawat yang muncul di bawah permukaan kulit, seperti jerawat kistik dan nodul. Bentuk jerawat papula dapat dirasakan sebagai benjolan padat yang menimbulkan rasa nyeri. Selain itu, kulit di sekitar benjolan akan tampak memerah, tetapi tidak memiliki titik nanah pada puncaknya. Jerawat papula akan muncul ketika komedo hitam (blackhead) atau komedo putih (whitehead) tidak ditangani hingga memicu iritasi yang parah. Akibatnya, kulit di sekitar komedo pun ikut rusak. Kerusakan kulit ini nantinya menyebabkan peradangan pada kulit yang memicu pertumbuhan jerawat papula. Oleh sebab itu, jerawat ini sering disebut sebagai jerawat inflamasi (peradangan). Apabila dibiarkan, penyakit kulit ini dapat berkembang menjadi pustula (jerawat bernanah).",
                    "solusi": [
                        ""
                    ],
                    "saran": [
                        "Obat topikal jerawat",
                        "Antibiotik"
                    ]
                }
            if str(prediction) == "pustula":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Pustula adalah benjolan kecil di permukaan kulit yang berisi nanah, sehingga dikenal pula dengan sebutan jerawat nanah. Jerawat ini muncul sebagai benjolan yang ukurannya lebih besar dari komedo dengan puncak berwarna keputihan dan kulit sekitarnya berwarna kemerahan. Umumnya, jerawat bernanah ini muncul di area wajah. Namun, bagian tubuh lainnya yang cenderung berminyak juga dapat diserang jerawat ini, seperti dada dan punggung.",
                    "solusi": [
                        ""
                    ],
                    "saran": [
                        "Obat topikal jerawat",
                        "Antibiotik",
                        "Terapi fotodinamik"
                    ]
                }
            if str(prediction) == "rosacea":
                data = {
                    "jenis": str(prediction),
                    "deskripsi": "Rosacea adalah penyakit kulit wajah yang ditandai dengan kulit kemerahan disertai bintik-bintik menyerupai jerawat. Kondisi ini juga dapat menyebabkan kulit wajah menebal dan pembuluh darah di wajah membengkak. Rosacea dapat terjadi pada siapa saja, tetapi umumnya menyerang wanita usia paruh baya yang berkulit terang. Gejala rosacea bisa hilang timbul dan umumnya  berlangsung selama beberapa minggu atau beberapa bulan",
                    "solusi": [
                        ""
                    ],
                    "saran": [
                        "Antibiotik"
                    ]
                }
            return jsonify(data)
        except Exception as e:
            return jsonify({"error": str(e)})

    return render_template('home.html')


if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))
