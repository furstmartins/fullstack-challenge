# Fullstack Code Challenge
___
## Acceptance criteria

- Provide clear instructions on how to run the application in development mode
  - pre required
    - java 1.8+
    - maven
  - **using IntelliJ**
    - Open the project
    - go to menu "Run" >> "Edit configurations..."
      - click button 'add configuration' choose maven
         - on 'Command line:' field add
            > spring-boot:run -Dactive.profile=dev
         - click OK button
      - go to menu "Run" >> "Run" select the previous configuration (IntelliJ start the application).
      
  - **using command-line**
    - type this command in root path of applications (../../sysorder)
    > mvn clean package spring-boot:repackage -Dactive.profile=dev

    - and then
    > java -jar target/sysorder.war

  - PS. for dev environment 3 (three) categories are created into the database when run the application


- Provide clear instructions on how the application would run in a production environment
  - **using command-line**
    - type this command in root path of applications (../../sysorder)
        > mvn clean package spring-boot:repackage -Dactive.profile=prod
    - and then
        > java -jar target/sysorder.war 

  - PS. it's possible to see the sql logs in dev environment, but not in prod environment


- Describe how you would implement an authentication layer for the web application (don't need to implement)
  - spring-security using basic authentication because it's using spring + thymeleaf and they maintain the user session


- RESTful API allowing CRUD and list operations on the orders
    - Endpoint to create/retrieve/update/delete order
    - Endpoint to list order
    - **see swagger into source code**


- RESTful API allowing CRUD operations on the categories
    - Endpoint to create/retrieve/update/delete category
    - Endpoint to list categories
    - **see swagger into source code**


- Database to store data from the following resources
    - Order
    - Category
    - **for this demo a embebed database H2 run with the application. When the application finish the database finished too**


- Describe how you would structure the database to account for
    - Real estate agency registration data
    - Company registration data
    - Contact registration data
        - this topic is quite confuse, I guess this problem can be solved using Role
    - Describe what needs to be changed on the API you implemeted
        - create tables USER, ROLE and USER_ROLE. During the authentication the application get the user's roles
        
