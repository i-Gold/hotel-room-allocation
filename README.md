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
- **Maven**

### Build the Application

To build the project, run the following command from the root directory:

**Using Maven:**

This command will compile and package the application into an executable JAR file.
```bash
mvn clean install
```

**Running the Application**

To run the application, execute the following command:
```bash
java -jar target/hotel-room-allocation-0.0.1-SNAPSHOT.jar
```

The application will now be accessible at http://localhost:8080.

**Running Tests**

To run the unit tests and ensure the application is working as expected, execute the following command:

```bash
mvn test
```

## Running the Application with run.sh

If you prefer to use the run.sh script to automate the build and run process, follow these steps:

1. Make sure run.sh has executable permissions:
    ```bash
    chmod +x run.sh
    ```
2. Execute the script:
    ```bash
    ./run.sh
    ```

This will clean and build the project, then start the application.