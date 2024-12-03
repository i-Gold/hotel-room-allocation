# Hotel Room Allocation

## Introduction

This project is a Room Occupancy Optimization tool designed for hotel clients. It helps allocate rooms (Premium and Economy) to guests based on their willingness to pay for the night. The system will prioritize high-paying guests for Premium rooms and allocate Economy rooms to lower-paying customers. If there are no available Economy rooms, high-paying guests can be booked into Premium rooms.

### API Endpoints:
- **POST /occupancy**: This API endpoint is used to allocate rooms to potential guests.
    - **Request Body:**
      ```json
      {
          "premiumRooms": 7,
          "economyRooms": 5,
          "potentialGuests": [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
      }
      ```
    - **Response Body:**
      ```json
      {
          "usagePremium": 6,
          "revenuePremium": 1054,
          "usageEconomy": 4,
          "revenueEconomy": 189.99
      }
      ```

## How to Build and Run

### Prerequisites
- **JDK 23**
- **Docker** (for running the application in the container)
- **Maven**
- **Postgres** (create `hotel` database on the `5432` port number on the environment where you are going to run the application)

### Build the Application

To build the project, run the following command from the root directory:

**Using Maven:**
```bash
mvn clean install
```

**Docker Run:**

1. Build a Docker image for the application:
```bash
docker build -t hotel-room-allocation .
```

2. Run the Docker container:
```bash
docker run -p 8080:8080 hotel-room-allocation
```
The application will now be accessible at http://localhost:8080.

### Running Tests

To run the unit tests and ensure the application is working as expected, execute the following command:

**Using Maven:**
```bash
mvn test
```

