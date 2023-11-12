# Spring Book Store

Spring Book Store is a web application featuring two distinct roles: Admin and Customer. Admins are empowered to perform essential tasks such as creating and updating book details, managing stock levels, and updating overall information. They also have the ability to retrieve a comprehensive list of all available books, fetch specific books by ID, and view statistics based on customer orders.

On the other hand, Customers engage in activities like placing orders, viewing their order history, and accessing information about all books. However, they are restricted from certain operations, such as viewing details about their own authentication process, which encompasses registration, login, token refresh, and logout processes.

## How it works

1. This app use Java 17, Spring Boot 3.0, Maven, JUnit, Mockito, Test Container, Docker, Prometheus and Grafana, Swagger

2. We have implemented a pessimistic lock mechanism to address scenarios in which multiple users might simultaneously attempt to order books. This mechanism ensures proper synchronization of their actions, preventing potential data conflicts. With pessimistic locking in place, in situations where multiple users strive to order the last available stock of a book, only one of them will be able to successfully place the order, avoiding conflicts and maintaining data integrity.
    ```bash
    # install apache 2
    sudo apt-get install apache2
    ```

3. Documentation API with Swagger
    ```bash
    http://localhost:1221/swagger-ui/index.html
    ```

4. Pre-requisites
    ````bash
   # define .env
   
   # docker
   docker compose up
   
   # docker build
   docker compose up --build
   
   # maven
   mvn clean spring-boot:run
    ````

## Others
Good Luck