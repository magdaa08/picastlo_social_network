# Picastlo Social Network

A social network centered around tactile paintings and transformation pipelines, developed for the Internet Applications Design and Implementation course at NOVA University Lisbon.

## Key Features
- User authentication & profiles
- Timeline with posts from public sources, friends, and groups
- Transformation pipelines for modifying images
- Storage for images and pipelines
- Microservices-based backend for scalability

## Technology
- **Backend:** Kotlin + Spring Boot (Microservices, REST API, H2 Database)
- **Frontend:** React + Redux + TypeScript
- **API Documentation:** OpenAPI 3.0
- **Storage:** Images and pipelines stored in a relational database

## Architecture
- Microservices-based backend with a RESTful API
- API Gateway handling service communication
- Secure authentication with JWT tokens
- Client-side application built with React & Redux

## Installation & Setup

**1. Clone the repository:**
   ```
   git clone https://github.com/magdaa08/picastlo_social_network.git
   cd picastlo_social_network
   ```

**2. Run the backend (microservices):**

    cd registry-service
    ./gradlew bootRun
    
    cd ../api-gateway
    ./gradlew bootRun
   
    cd ../user-service
    ./gradlew bootRun
  
    cd ../post-service
    ./gradlew bootRun
    
    cd ../pipeline-service
    ./gradlew bootRun
   
    cd ../connections-service
    ./gradlew bootRun

**3. Run the frontend (React + Redux):**
   ```
   cd ../react-redux
   npm install
   npm start
   ```
