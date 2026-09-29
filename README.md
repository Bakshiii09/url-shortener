# URL Shortening Service

URL Shortening Service built with Java and Spring Boot.

## Project Page

https://github.com/Bakshiii09/url-shortener

## Requirements

- Java 17 or later
- Maven
- MySQL

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/Bakshiii09/url-shortener.git
cd url-shortener
```

2. Configure the MySQL database in:

```
src/main/resources/application.properties
```

Set your local database credentials:

```
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=YOUR_DB_PASSWORD
```

3. Start the Spring Boot application.

On Windows:

```bash
mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

4. The application will start at:

```
http://localhost:8080
```

API Endpoints
Create Short URL
POST /shorten

Request:

{
  "url": "https://www.example.com"
}
Retrieve Short URL
GET /shorten/{shortCode}
Update Short URL
PUT /shorten/{shortCode}

Request:

{
  "url": "https://www.example.com/updated"
}
Delete Short URL
DELETE /shorten/{shortCode}
Get URL Statistics
GET /shorten/{shortCode}/stats

Returns the URL mapping including the access count.

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

