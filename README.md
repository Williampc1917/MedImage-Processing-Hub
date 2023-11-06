# MedImage-Processing-Hub
MedImage Processing Hub is an innovative web platform designed to assist healthcare professionals and researchers in analyzing chest X-ray images. Utilizing advanced machine learning algorithms, the platform provides an accessible and user-friendly interface where users can upload X-ray images and quickly receive assessments for pneumonia presence.

## Architecture Overview

Frontend (React + TypeScript)
- Users interact with the web interface to upload X-ray images for analysis.
- The frontend handles user authentication, image uploads, and displays results from the TensorFlow model.
- It communicates with the backend via RESTful API calls to send images and receive analysis results.

Backend (Java Spring Boot)
- The backend server acts as the middleman between the frontend and the TensorFlow model.
- The backend handles secure storage of images, secure login using AWS RDS, and ensures that only authorized requests are processed.
- For analysis, it receives image uploads from the frontend, performs any necessary preprocessing or formatting, When an image is ready for analysis, the backend dispatches it to the TensorFlow model's API, ensuring the data is packaged correctly for the machine learning model to interpret.


TensorFlow Model (Python API)
- A Python Flask API hosts the TensorFlow model, providing an endpoint for image-based diagnosis predictions. Upon receipt of an image, the model predicts the likelihood of pneumonia.
-  The API includes a function to preprocess incoming X-ray images into a format suitable for the model, ensuring accurate predictions.
- After analysis, the Flask API formats the prediction into a human-readable diagnosis with confidence levels and sends this data back as a JSON response.
 



## Detailed Guide

### Building the TensorFlow Model
#### Data acquisition and preprocessing 
The dataset consists of X-ray images collected from a Dataset off Kaggle. The data was organized into three main directories (train, test, and val) and further split into subdirectories for each category (normal, pneumonia). To ensure the model generalizes well, the dataset was shuffled and then split into training and testing sets with an 80/20 ratio. The images were loaded using OpenCV, converted from BGR to RGB format, and resized to a uniform size (256x256 pixels) for consistency. Data augmentation techniques such as rotation, width and height shifts, shear, zoom, and horizontal flipping were applied to the training images to improve model robustness and help prevent overfitting.

#### Model Architecture
The TensorFlow model is built upon the InceptionV3 architecture, a pre-trained deep convolutional neural network known for its efficiency and accuracy in image recognition tasks.

#### Training Process 
The model is compiled with the Adam optimizer with a learning rate of 1e-4 and a binary cross-entropy loss function, which is suitable for binary classification tasks. We employ an EarlyStopping callback to monitor the validation loss, ceasing training if no improvement is observed for seven epochs, and a ModelCheckpoint callback to save the weights of the best performing iteration based on validation loss.

### Backend
The backend is developed using Java Spring Boot, which exposes RESTful API endpoints for interaction with the frontend. The backend handles the secure SignUp, Login, stroing user credentials in AWS RDS,  and processing of X-ray images. Uses a service layer which contains business logic and service classes responsible for handling data operations and external API communications. It is where most of the application's logic is executed, such as image analysis, user authentication(AWS), and data management.

### API Endpoints 
#### Below is the documentation for the available API endpoints under the `PneumoniaImageAnalysisController`. All endpoints are prefixed with `/api`.

#### Image Upload and Analysis

- **Endpoint:** `/upload`
- **Method:** POST
- **Cross-Origin:** Enabled (recommended to configure CORS to allow only specific trusted domains)
- **Description:** This endpoint accepts an X-ray image file along with the name and age of the patient. It processes the uploaded image and returns the analysis result, which includes the likelihood of pneumonia.
- **Request Parameters:**
  - `image` (required): The X-ray image file to be analyzed (MultipartFile).
  - `name` (required): The name of the patient (String).
  - `age` (required): The age of the patient (int).
- **Success Response:**
  - **Code:** 200 OK
  - **Content:** An `AnalysisResult` object containing the outcome of the pneumonia analysis.
- **Error Response:**
  - **Code:** 400 BAD REQUEST (e.g., if parameters are missing or the file is not an image)
  - **Code:** 500 INTERNAL SERVER ERROR (e.g., if there's an error during the image analysis process)


#### Below is the documentation for the available API endpoints under the `SignInController`. All endpoints are prefixed with `/api`.

#### User Authentication - Sign In
- **Endpoint:** `/signin`
- **Method:** POST
- **Cross-Origin:** Configured for `http://localhost:3000`. For production, specify your frontend domain.
- **Description:** This endpoint is responsible for authenticating users. It expects user credentials and returns a token if authentication is successful.
- **Request Body:** 
  - `email` (required): The email of the user (String).
  - `password` (required): The password of the user (String).
- **Success Response:**
  - **Code:** 200 OK
  - **Content:** A JSON object containing the authentication token and a success message.

    ```json
    {
      "token": "GeneratedAuthenticationToken",
      "message": "Login successful"
    }
    ```

#### Below is the documentation for the available API endpoints under the `SignUpController`. All endpoints are prefixed with `/api`.

#### User Registration - Sign Up

- **Endpoint:** `/signup`
- **Method:** POST
- **Cross-Origin:** Configured for `http://localhost:3000`. For production, specify your frontend domain.
- **Description:** This endpoint is for user registration. It accepts user details and registers a new user if the details are valid and the email does not already exist.
- **Request Body:** 
  - `name` (required): The full name of the user (String).
  - `email` (required): The email of the user (String).
  - `password` (required): The password of the user (String).
  - Additional user detail fields as per the `UserDto` class specifications.
- **Success Response:**
  - **Code:** 200 OK
  - **Content:** A `User` object containing the details of the newly registered user.

    ```json
    {
      "id": "UserId",
      "name": "UserFullName",
      "email": "user@example.com",
    }
    ```

- **Error Response:**
  - **Code:** 400 BAD REQUEST
  - **Content:** A JSON object containing validation error messages for each field.


### Service layer
The service layer contains business logic and service classes responsible for handling data operations and external API communications. It is where most of the application's logic is executed, such as image analysis, user authentication, and data management.
- **Location:** All service classes are located in the `service` directory.
- **Key Components:**
  - `PneumoniaImageAnalysisService`: Handles the logic for analyzing X-ray images to determine the presence of pneumonia.
  - `AuthenticationService`: Handles the logic for authentiating user login information from AWS RDS MySql databse
  - `UserService`: Hangles the logic for saving user SignUp information into the database 

For detailed understanding, you can explore the `service` directory. Each service class is well-documented with comments explaining the methods and their usage. For example, the `PneumoniaImageAnalysisService` class provides a method to analyze images for pneumonia detection and communicates with a Flask-based machine learning API.

### FrontEnd Application
The frontend of this application is built with React, utilizing an open-source template from [https://github.com/cruip/open-react-template]. Significant modifications were made to ensure the frontend communicates effectively with our custom backend services.

### Modifications Overview

- **Open Source Template**: The original template provided a robust starting point with pre-designed components and layout structures.
- **Customization**: All components and service calls have been reworked to interact with the backend API endpoints for pneumonia X-ray analysis.
- **Interactivity**: Added custom hooks and states to manage the application's interactivity and responsiveness to user actions.

### Key Functionalities

- **Image Upload & Analysis**: Users can upload an X-ray image, and the frontend will send it to the backend for pneumonia detection.
- **State Management**: React hooks such as `useState` and `useRef` are used to manage the application's state and to reference form elements.
- **Asynchronous Data Handling**: The frontend handles asynchronous API calls, providing feedback such as loading states to enhance user experience.
- **Error Handling**: Proper error handling is implemented to manage any issues during the image upload and analysis process.

## Configuration

To set up AWS RDS connection configure application.properties in /resources. Check /backend/src/main/resources/application.properties.sample for the format

## License

[MIT](https://choosealicense.com/licenses/mit/)
