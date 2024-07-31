
# Phonebook API
This is a simple Phonebook API application built with Spring Boot and MySQL database. It provides endpoints to manage contacts, including adding, updating, retrieving, and deleting contacts.

### 1. Install Prerequisites

#### Install Java
Make sure you have Java 17 installed. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/):

```sh
# For Ubuntu/Debian
sudo apt-get update
sudo apt-get install openjdk-17-jdk

# For macOS using Homebrew
brew install openjdk@17
brew link --force --overwrite openjdk@17
```

#### Install Maven
Ensure you have Maven installed. You can download it from [Apache Maven](https://maven.apache.org/download.cgi):

```sh
# For Ubuntu/Debian
sudo apt-get update
sudo apt-get install maven

# For macOS using Homebrew
brew install maven
```

#### Install Redis
Install Redis locally. Instructions can be found on the [Redis website](https://redis.io/download).

```sh
# For Ubuntu/Debian
sudo apt-get update
sudo apt-get install redis-server
sudo systemctl enable redis-server.service
sudo systemctl start redis-server.service

# For macOS using Homebrew
brew install redis
brew services start redis
```

#### Install Docker
Ensure you have Docker installed. You can download it from [Docker](https://www.docker.com/get-started):

```sh
# For Ubuntu/Debian
sudo apt-get update
sudo apt-get install docker.io
sudo systemctl start docker
sudo systemctl enable docker

# For macOS
brew install --cask docker
```

#### Install Redis


### 2. Clone the Repository

Clone your repository to your local machine:

```sh
git clone https://github.com/korenka/RisePhoneBook.git
cd RisePhoneBook/phonebook-api
```

### 3. Build and Run the Application

#### Build with Maven

Navigate to the project directory and build the application:

```sh
mvn clean install
```

#### Run with Java

Run the application:

```sh
java -jar target/phonebook-api-1.0-SNAPSHOT.jar
```

### 4. Running the Application with Docker

#### Dockerfile

Ensure your `Dockerfile` is as follows:

```Dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/phonebook-api-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} phonebook-api.jar
ENTRYPOINT ["java","-jar","/phonebook-api.jar"]
```

#### Build Docker Image

Build the Docker image:

```sh
docker pull openjdk:17-jdk-slim
docker build -t phonebook-api .
```

#### Run Docker Container

Run the Docker container:

```sh
sudo docker run -d -p 8080:8080 --name phonebook-container phonebook-api
```

### 5. Verify the Setup

You can verify if the application is running by sending a request to the API endpoint:

```sh
curl -X GET http://localhost:8080/api/contacts/all
```
### 6. Check the logs

You can follow the docker running application logs by using this command:

```sh
docker logs -f phonebook-container
```
