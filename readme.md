# Looqbox Backend Challenge 🚀

---

## Overview
This project is a microservice developed as part of the Looqbox Backend Challenge. The microservice consumes the PokéAPI and exposes two endpoints to search and sort Pokémon data. 🐾

---

## Features ✨
- **Endpoints**:
  - `GET /pokemons`: Searches Pokémon by name and sorts the results. 🔍
  - `GET /pokemons/highlight`: Searches Pokémon by name, sorts the results, and highlights the matching substring. 💡
- **Custom Sorting**:
  - **Alphabetical**: Sorts by Pokémon name in ascending order. 🔤
  - **Length**: Sorts by Pokémon name length in ascending order. 📏
- **Caching**:
  - Implements manual caching to optimize Pokémon data retrieval. ⚡
- **Manual Sorting Algorithm**:
  - Sorting is implemented from scratch (e.g., Bubble Sort or Merge Sort). 🛠️
- **Dockerized**:
  - The application is containerized using Docker for easy deployment. 🐳
- **Unit Testing**:
  - Comprehensive tests to ensure functionality and reliability. ✅

---

## Tech Stack 🛠️
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
- **Java 17**: Programming language. ☕
- **Spring Boot**: Framework for RESTful API development. 🌱
- **Gradle**: Dependency and build management. 📦
- **Docker**: For containerization. 🐳
- **JUnit**: For unit testing. 🔬

---

## Architecture 🏗️
The project follows **Clean Architecture** principles, ensuring high maintainability and scalability. 🔄

### Diagram 🖼️
![Architecture Diagram](/docs/Looqbox-Backend-Challenge-Docs.png)

### Flow 🔄
1. **Client**: Sends HTTP requests. 💻
2. **Entrypoints (Presentation Layer)**: Handles HTTP requests and delegates to the application layer. 📩
3. **Application Layer**: Processes requests via use cases. 📜
4. **Infrastructure Layer**: Retrieves data from the cache or external API. 🌐
5. **Cache**: Optimizes performance by storing previously fetched data. ⚡
6. **Domain Logic**: Applies sorting and filtering. 🔢
7. **Response Mapping**: Maps the response format back to the client. 📤

---

## How to Run ▶️

### Prerequisites 📋
- Docker 🐳

### Steps 🔧

1. Clone the git repository: 🧑‍💻
```bash
git clone git@github.com:pdroacordi/looqbox-backend-challenge.git
```

2. At the project root, run: 🛠️
```bash
sudo docker build -t looqbox-backend-challenge .
```
```bash
sudo docker run -p 8080:8080 --name looqbox-backend-challenge looqbox-backend-challenge
```

3. The application is running. You can use it through a Client (eg.: Insomnia, Postman, etc.) or through the Swagger documentation, at 🌐
```bash
http://localhost:8080/swagger-ui/index.html
```
