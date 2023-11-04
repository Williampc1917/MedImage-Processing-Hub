import numpy as np
import cv2
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import img_to_array

# Load the trained model
model = load_model('pneumonia_xray_classifier.h5')


def preprocess_image(image_path, img_size=(256, 256)):
    """
    Loads and preprocesses the image for prediction.
    """
    img = cv2.imread(image_path, cv2.IMREAD_COLOR)
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    img = cv2.resize(img, img_size)
    img = img_to_array(img)  # Convert image to numpy array
    img = img / 255.0  # Rescale
    img = np.expand_dims(img, axis=0)  # Add batch dimension
    return img


def predict_image(image_path):
    """
    Predicts whether an X-ray image has pneumonia and gives a confidence score.
    """
    img = preprocess_image(image_path)
    prediction = model.predict(img)[0][0]  # This extracts the single prediction value from the result

    if prediction > 0.5:
        return f"Pneumonia detected with {prediction * 100:.2f}% confidence"
    else:
        return f"Normal with {(1 - prediction) * 100:.2f}% confidence"


# Example usage
image_path = 'D:/Downloads/x-ray-test.jpeg'
print(predict_image(image_path))
