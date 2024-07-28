
# Phonebook API

  

This is a simple Phonebook API application built with Spring Boot and H2 database. It provides endpoints to manage contacts, including adding, updating, retrieving, and deleting contacts.

  

## Prerequisites

  

- Java 11 or higher

- Maven 3.6.0 or higher

- Docker (optional, for running the application in a container)

  

## Getting Started

  

### Clone the Repository

  

```bash
git clone https://github.com/korenka/RisePhoneBook.git

cd RisePhoneBook
```
## Build the Project

To build the project, use the following command:

```
nvm clean package
```
### Run the Application Locally

To run the application locally, use the following command:
```
java -jar target/phonebook-api-1.0-SNAPSHOT.jar
```
The application will start and be accessible at `http://localhost:8080`.

### Run the Application with Docker

To run the application in a Docker container, follow these steps:
1.  **Build the Docker Image**:
    
    ```docker build -t phonebook-api .``` 
    
2.  **Run the Docker Container**:
    
    ```docker run -d -p 8080:8080 phonebook-api```
    
 ### Access the H2 Database Console

The H2 database console is available at ```http://localhost:8080/h2-console```.

-   **JDBC URL**: ```jdbc:h2:file:~/testdb```
-   **Username**: ```sa```
-   **Password**: ```password```
### API Endpoints

The following endpoints are available:

-   **Get All Contacts**:

    `curl -X GET http://localhost:8080/api/contacts/all` 
    
-   **Get Contacts with Pagination**:
 
       `curl -X GET "http://localhost:8080/api/contacts?page=0&size=10"` 
    
-   **Get Contact by ID**:

    `curl -X GET http://localhost:8080/api/contacts/{id}` 
    
-   **Add a New Contact**:

    `curl -X POST http://localhost:8080/api/contacts -H "Content-Type: application/json" -d '{
      "firstName": "John",
      "lastName": "Doe",
      "phone": "1234567890",
      "address": "123 Main St"
    }'` 
    
-   **Update an Existing Contact**:

    `curl -X PUT http://localhost:8080/api/contacts/{id} -H "Content-Type: application/json" -d '{
      "firstName": "John",
      "lastName": "Doe",
      "phone": "0987654321",
      "address": "456 Elm St"
    }'` 
    
-   **Delete a Contact**:

    `curl -X DELETE http://localhost:8080/api/contacts/{id}` 
    

### Running Tests

To run the tests, use the following command:

`./mvnw test`
