# StreamSphere - Video Sharing Platform

StreamSphere is a comprehensive video-sharing platform inspired by YouTube, allowing users to upload, view, rate, share, and comment on videos. It is designed to provide a seamless and personalized user experience through a dynamic interface and robust backend infrastructure.

## Features

- **User-Friendly Interface**: 
  Developed with **Angular 17**, delivering a responsive and dynamic front-end experience.

- **Robust Backend**: 
  Built with **Spring Boot 3**, ensuring efficient video processing, storage, and secure data handling.

- **Database Management**: 
  Utilizes **MongoDB** for managing non-relational data, making it ideal for handling video metadata and user interactions.

- **Recommendation Algorithm**: 
  Integrates a custom recommendation engine to deliver personalized content suggestions to users based on their preferences and viewing history.

- **User Authentication and Authorization**: 
  Implemented with **Auth0** to ensure secure login, registration, and data protection.

- **RESTful APIs**: 
  Facilitates smooth communication between the front-end and back-end systems, enabling efficient data exchange.

## Tech Stack

- **Frontend**: Angular 17
- **Backend**: Spring Boot 3
- **Database**: MongoDB
- **Authentication**: Auth0
- **Recommendation Engine**: Custom algorithm for personalized content delivery

## Getting Started

To run StreamSphere locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/streamsphere.git
   cd streamsphere
   ```
   
2. Set up the Backend:
- Ensure MongoDB is installed and running.
- Update the database configurations in the application.properties file.
- Run the Spring Boot application:
```bash
./mvnw spring-boot:run
```

3. Set up the Frontend:
- Navigate to the frontend directory:
```bash
cd streamsphere-frontend
```
- Install dependencies and start the Angular application:
```bash
npm install
ng serve
```

4. Access the Application:
Visit http://localhost:4200 to access the StreamSphere frontend.
The backend API will be accessible at http://localhost:8080.
## Contributing
Contributions are welcome! Feel free to open issues or submit pull requests for improvements and bug fixes.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contact
For any inquiries or support, reach out to eryk.kubiak.firma@gmail.com.
