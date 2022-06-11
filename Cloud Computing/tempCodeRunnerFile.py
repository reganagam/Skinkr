   train_dir = os.path.join('dataset/train')
                from keras.preprocessing.image import ImageDataGenerator
                train_datagen = ImageDataGenerator(
                    rescale = 1.0/255.,
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