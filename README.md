# Quantity Measurement App

A robust Spring Boot application for managing and converting between different units of measurement. This project provides a RESTful API for quantity measurement operations with security, validation, and Docker support.

## 📋 Features

- **Unit Conversion**: Convert between various units of measurement (Length, Weight, Volume, Temperature, etc.)
- **RESTful API**: Full-featured REST API with comprehensive endpoints
- **Authentication & Security**: Spring Security with JWT token support and OAuth2 integration
- **Data Validation**: Input validation using Bean Validation
- **Database Support**: 
  - H2 for development/testing
  - MySQL for production environments
- **API Documentation**: Swagger/OpenAPI UI for API exploration
- **Docker Support**: Multi-stage Docker build for containerization
- **DevTools**: Spring Boot DevTools for enhanced development experience
- **Monitoring**: Spring Boot Actuator for application metrics and health checks

## 🛠️ Technology Stack

- **Language**: Java 17
- **Framework**: Spring Boot 3.2.3
- **Build Tool**: Maven 3.9.6
- **Databases**: H2 (dev) & MySQL (prod)
- **Authentication**: JWT, OAuth2
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Container**: Docker with multi-stage builds
- **Testing**: JUnit, Spring Security Test

## 📦 Dependencies

### Core
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Security
- Spring Boot Actuator

### Database
- H2 Database (runtime, testing)
- MySQL Connector/J (runtime, production)

### Security & Authentication
- Spring Security
- JWT (JJWT 0.11.5)
- Spring OAuth2 Client

### Developer Tools
- Lombok 1.18.30
- Spring Boot DevTools
- SpringDoc OpenAPI 2.3.0 (Swagger UI)

### Testing
- Spring Boot Test
- Spring Security Test

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.9.6
- MySQL 8.0+ (for production)
- Docker (optional, for containerization)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/rupamkumarbarnwal/QuantityMeasurementApp.git
   cd QuantityMeasurementApp
   ```

2. **Build the project**
   ```bash
   mvn clean package
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

4. **Access Swagger UI**
   ```
   http://localhost:8080/swagger-ui.html
   ```

### Configuration

The application uses Spring Boot configuration. By default, it runs with H2 in-memory database for development.

For production MySQL setup, create or update `application-prod.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quantity_measurement
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

## 🐳 Docker Setup

### Build Docker Image
```bash
docker build -t quantity-measurement-app .
```

### Run with Docker
```bash
docker run -p 8080:8080 quantity-measurement-app
```

The Dockerfile uses a multi-stage build process:
- **Build Stage**: Compiles the Maven project
- **Run Stage**: Runs the JAR in a lightweight Java container

## 📚 API Endpoints

Access Swagger UI at `/swagger-ui.html` for complete API documentation and interactive testing.

### Key Endpoints (examples)
- `GET /api/v1/measurements` - List all measurements
- `POST /api/v1/measurements` - Create a new measurement
- `GET /api/v1/measurements/{id}` - Get measurement by ID
- `PUT /api/v1/measurements/{id}` - Update measurement
- `DELETE /api/v1/measurements/{id}` - Delete measurement
- `/actuator/health` - Health check endpoint

## 🔐 Authentication

The application supports:
- **JWT Authentication**: Token-based authentication for API requests
- **OAuth2**: Social login integration
- **Spring Security**: Role-based access control

Include JWT token in request headers:
```bash
Authorization: Bearer <your_jwt_token>
```

## 🧪 Testing

Run tests with Maven:
```bash
mvn test
```

Tests include:
- Unit tests
- Integration tests
- Security tests

## 📊 Project Structure

```
QuantityMeasurementApp/
├── src/
│   ├── main/
│   │   ├── java/com/app/
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── service/          # Business logic
│   │   │   ├── repository/       # Data access layer
│   │   │   ├── entity/           # JPA entities
│   │   │   ├── dto/              # Data transfer objects
│   │   │   ├── security/         # Security configuration
│   │   │   └── QuantityMeasurementApp.java  # Main application
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-prod.properties
│   └── test/                      # Test files
├── pom.xml                        # Maven configuration
└── Dockerfile                     # Docker configuration
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License.

## 👤 Author

**Rupam Kumar Barnwal**
- GitHub: [@rupamkumarbarnwal](https://github.com/rupamkumarbarnwal)

## 📧 Support

For issues, questions, or suggestions, please open an issue on the [GitHub repository](https://github.com/rupamkumarbarnwal/QuantityMeasurementApp/issues).

## 🔗 Links

- **GitHub Repository**: https://github.com/rupamkumarbarnwal/QuantityMeasurementApp
- **API Documentation**: http://localhost:8080/swagger-ui.html (after running locally)
- **Spring Boot Documentation**: https://spring.io/projects/spring-boot
- **JWT Documentation**: https://github.com/jwtk/jjwt
