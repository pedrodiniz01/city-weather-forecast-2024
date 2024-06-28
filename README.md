# City-weather-forecast-2024
City Weather Forecast is an application that allows to register a city, list the cities and the weather forecast.

## 1. Main Features
This REST API provides different functionalities such as:
- Registration of valid cities;
- Listing registered cities with its name, country and the forecast link.
- Retrieving weather forecasts for registered cities with options for 3 or 5 days.

## 2. Technologies Used
- Java 17
- SpringBoot 3.2.3

## 3. Architecture Overview
This is a typical Java Springboot application that follows MVC architecture, organizing responsibilities into several distinct layers:

- Controller
- Service
- Repository

In addition to these core layers, the application includes:
- Client Layer: Responsible for interacting with external APIs;
- Custom Exceptions: Handles specific error scenarios;
- Utils: Provides utilitarian methods that are used for validation, objects creation.

## 4. Setup and Installation
You have two methods to run the application:

- If you have Java 17 installed, simply execute it.
- Alternatively, if you have Docker installed, you can build an image and run it smoothly:
  - Build the Docker image with **docker build -t weatherforecast-app .**
  - Run the application using Docker with **docker run -p 8080:8080 weatherforecast-app**

The application runs on port 8080. Access the database via http://localhost:8080/h2-console using the credentials specified in the application properties file.

Additionally, there is a Postman collection within the application containing the requests and payloads that can be used.