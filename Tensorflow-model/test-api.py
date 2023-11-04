import requests

API_URL = "http://127.0.0.1:5000/predict"
IMAGE_PATH = "D:/pythonProject/Medical-GPU/X-ray/train/pneumonia/person2_bacteria_3.jpeg"  # Replace this with the path to your image


def test_api(img_path):
    with open(img_path, "rb") as image_file:
        files = {
            "image": image_file
        }
        response = requests.post(API_URL, files=files)

        if response.status_code == 200:
            data = response.json()
            print(f"Result: {data['result']}")
            print(f"Diagnosis: {data['diagnosis']}")
            print(f"Confidence: {data['confidence']:.2f}")
        else:
            print(f"Error {response.status_code}: {response.text}")


if __name__ == "__main__":
    test_api(IMAGE_PATH)