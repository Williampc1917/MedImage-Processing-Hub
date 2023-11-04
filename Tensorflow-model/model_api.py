import cv2
import numpy as np
from flask import Flask, request, jsonify
import tensorflow as tf
from keras.models import load_model  # Corrected this import
from keras.utils import img_to_array

app = Flask(__name__)

# Load the model
model = load_model('pneumonia_xray_classifier.h5')


def preprocess_image(image_bytes, img_size=(256, 256)):
    """
    Loads and preprocesses the image for prediction.
    """
    img_arr = np.fromstring(image_bytes, np.uint8)
    img = cv2.imdecode(img_arr, cv2.IMREAD_COLOR)
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    img = cv2.resize(img, img_size)
    img = img_to_array(img)  # Convert image to numpy array
    img = img / 255.0  # Rescale
    img = np.expand_dims(img, axis=0)  # Add batch dimension
    return img


@app.route('/predict', methods=['POST'])
def predict():
    img = request.files['image'].read()
    img = preprocess_image(img)

    prediction = model.predict(img)[0][0]

    # Decide the diagnosis based on the prediction value
    if prediction > 0.5:
        diagnosis = "Pneumonia detected"
        confidence = prediction
    else:
        diagnosis = "Normal"
        confidence = 1 - prediction

    confidence = float(confidence)  # Convert float32 to native Python float

    # Format the diagnosis and confidence as strings for the response
    formatted_result = f"{diagnosis} with {confidence * 100:.2f}% confidence"

    return jsonify({
        'result': formatted_result,
        'diagnosis': diagnosis,
        'confidence': confidence
    })


if __name__ == '__main__':
    app.run(debug=True, port=5000)
