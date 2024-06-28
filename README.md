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
This is a typical Java Springboot application that follows MVC architecture, organizing responsibilities into several layers:

- Controller
- Service
- Repository

In addition to these layers, the application includes:
- Client Layer: Responsible for interacting with external APIs;
- Custom Exceptions: Handles specific error scenarios;
- Utils: Provides utilitarian methods that are used for validation, objects creation, etc.

## 4. Setup and Installation
You have two methods to run the application:

- If you have Java 17 installed, simply execute it.
- Alternatively, if you have Docker installed, you can build an image and run it smoothly:
  - Build the Docker image with **docker build -t weatherforecast-app .**
  - Run the application using Docker with **docker run -p 8080:8080 weatherforecast-app**

The application runs on port 8080. Access the database via http://localhost:8080/h2-console using the credentials specified in the application properties file.

Additionally, there is a Postman collection within the application containing the requests and payloads that can be used.

## 4. Endpoints

### 4.1 Register City
**URL:** `/register`  
**Method:** `POST`  
**Description:** Registers a new city.

#### 4.1.1. Request Body:
```json
{
  "name": "CityName"
}
```

#### 4.1.2. Responses:

- **201 Created:** City successfully registered.
  ```json
  {
    "message": "City 'CityName' has been registered with success!"
  }
  ```
- **200 OK:** City has already been registered.
  ```json
  {
    "message": "City 'CityName' has already been registered."
  }

- **400 OK:** City has already been registered.
  ```json
  {
    "message": "Unknown city name 'CityName'."
  }
  ```
#### 4.1.3. API Workflow
- Firstly, there is a validation to check if city is already registered.
- Next, OpenWeather API is called to retrieve city information.
- After validating the API response, relevant data such as country and coordinates are extracted.
- Then, the forecast URL is constructed (based on the coordinates).
- Finally, the information (city name, country, and forecast link) is saved in the database.

### 4.2 List all cities
**URL:** `/list`  
**Method:** `GET`  
**Description:** List all registered cities.

#### 4.2.1 Responses:

- **200 OK:** 
- 
  ```json
  [
    {
        "id": 1,
        "name": "Porto",
        "country": "PT",
        "forecastLink": "https://api.openweathermap.org/data/3.0/onecall?lat=41.1494512&lon=-8.6107884&appid=d450c64ee02b2fd15f6b1b9c628b7660&units=metric&exclude=minutely,hourly,current,alerts"
    }
  ]
  ```
#### 4.2.3. API Workflow
- Simple retrieve information from database.

### 4.3 Forecast City
**URL:** `/forecast/{cityName}?days={day}`  
**Method:** `GET`  
**Description:** Get City forecast for the next following days, depends on day parameter.

#### 4.3.1. Responses:

- **200 OK**
  ```json
  [
    {
        "dt": "2024-06-28",
        "sunrise": "2024-06-28",
        "sunset": "2024-06-28",
        "moonrise": "2024-06-28",
        "moonset": "2024-06-28",
        "moon_phase": 0.75,
        "summary": "Expect a day of partly cloudy with rain",
        "temp": {
            "day": 17.98,
            "min": 17.35,
            "max": 18.8,
            "night": 18.8,
            "eve": 18.14,
            "morn": 17.4
        }
      }
  ]
  ```
- **400 OK:** City not found.
  ```json
  {
    "message": "City 'Guimarães' not found."
  }

- **500 OK:** Internal Error.
  ```json
  {
    "message": "Internal Server Error."
  }
  ```
#### 4.3.3. API Workflow
- First, the city is searched in the database by name.
- If found, the URL is retrieved from the database.
- Next, the external OpenWeather API is called to obtain the weather forecast.
- The response is then mapped, converting the timestamps from Unix to date format.
- To extract the forecast for the desired number of days, a list and a map are created to achieve the desired result.
