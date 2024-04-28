# Expression Evaluator API

## Description

This project is a Spring Boot application designed to evaluate logical expressions based on user input. 
It includes a RESTful API with endpoints for creating and evaluating expressions. 
The API documentation is powered by SpringDoc OpenAPI, which provides interactive documentation through Swagger UI.

## Features

- **Create Expression**: Allows users to create a logical expression.
- **Evaluate Expression**: Evaluates a stored expression based on provided JSON data.
- **API Documentation**: Auto-generated interactive API documentation using SpringDoc OpenAPI.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java JDK 17 or newer
- Maven 3.6 or newer 

## Installation

To install Expression Evaluator API, follow these steps:

1. Clone the repository:

       git clone https://github.com/lux-coder/expression-evaluator.git
   
       cd expression-evaluator

2. Build the project:

       mvn clean install

3. Run the application:

       mvn spring-boot:run

## Usage

Once the application is running, you can access the API documentation at:

    http://localhost:8080/swagger-ui.html

Here you can find detailed information about all the available API endpoints, including example requests and responses.

## Examples
Creating an Expression

    curl -X POST 'http://localhost:8080/expression/create?name=Sample%20Expression' \
     -H 'Content-Type: text/plain' \
     -d '{"value": "(data > 100)"}'


Evaluating an Expression

     curl -X POST http://localhost:8080/expression/evaluate/{id} -H "Content-Type: application/json" -d '{"data":150}'

## Running with Docker
To build and run the application with Docker:

1. Build the Docker image:

       docker build -t expression-evaluator .

2. Run the Docker container:

       docker run -p 8080:8080 expression-evaluator



