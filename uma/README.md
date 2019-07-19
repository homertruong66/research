# University Management Application

1. Analysis (MDD Approach)

    * Domain Model: Associations among Domain Objects
        - Inheritance
        - Composition
        - Aggregation
        - Dependency    
    
    * Business Flows
        - Main Flow:        
            . Admin creates Universities, Departments            
            . User (staffs) imports Teachers, creates Courses, Classes            
            . Student (un)registers Classes            
            . Teacher views assigned Classes
        - Class Flow:        
            . User starts NEW Classes  
            . User ends STARTED Classes                               
    
    * Business Rules            
            . User can not start STARTED Classes            
            . User can not start ENDED Classes  
            . User can not start CANCELLED Classes  
            . User can not end ENDED Classes            
            . User can not end CANCELLED Classes  
            . User can not cancel STARTED Classes            
            . User can not cancel ENDED Classes            
            . User can not cancel CANCELLED Classes  
            . Student can not unregister STARTED Classes             
            . Student can not unregister ENDED Classes            
            . Student can not unregister CANCELLED Classes
    
    * Features and User Stories
        - User Management
            1. Create a User (ROLE_ADMIN)
            2. Get a User (ROLE_ADMIN)
            3. Update a User (ROLE_ADMIN)
            4. Delete a User (ROLE_ADMIN)
            5. Search Users (ROLE_ADMIN)
    
        - University Management
            1. Create a University (ROLE_USER)
            2. Get a University (ROLE_USER)
            3. Update a University (ROLE_USER)
            4. Delete a University (ROLE_ADMIN)
            5. Search Universities (ROLE_USER, ROLE_ADMIN)
    
        - Department Management
            1. Create a Department for a University (ROLE_USER)
            2. Get a Department (ROLE_USER)
            3. Update a Department (ROLE_USER)
            4. Delete a Department (ROLE_ADMIN)
            5. Search Departments (ROLE_USER, ROLE_ADMIN)
    
        - Course Management
            1. Create a Course for a Department (ROLE_USER)
            2. Get a Course (ROLE_USER)
            3. Update a Course (ROLE_USER)
            4. Delete a Course (ROLE_ADMIN)
            5. Search Courses (ROLE_USER, ROLE_ADMIN)
    
        - Student Management
            1. Create a Student for a Department (ROLE_USER)
            2. Get a Student (ROLE_USER)
            3. Update a Student (ROLE_USER)
            4. Delete a Student (ROLE_ADMIN)
            5. Search Students (ROLE_USER, ROLE_ADMIN)
    
        - Teacher Management
            1. Import Teachers from a file (csv) (ROLE_USER)
            2. Create a Teacher (ROLE_USER)
            3. Get a Teacher (ROLE_USER)
            4. Update a Teacher (ROLE_USER)
            5. Delete a Teacher (ROLE_USER, ROLE_ADMIN)
            6. Search Teachers (ROLE_USER, ROLE_ADMIN)
            7. Assign a Teacher to Department(s) (ROLE_USER)
    
        - Class Management
            1. Create a Class for a Course (ROLE_USER)
            2. Get a Class (ROLE_USER)
            3. Update a Class (ROLE_USER)
            4. Delete a Class (ROLE_ADMIN)
            5. Search Classes
            6. View Classes of a Student (ROLE_STUDENT)
            7. Register a Class (ROLE_STUDENT)
            8. Unregister  a Class (ROLE_STUDENT)
            9. Start a Class (ROLE_USER)
            10. End a Class (ROLE_USER)
            11. Cancel a Class (ROLE_USER)
            12. View Classes of a Teacher (ROLE_TEACHER)

2. Design    
    1. Core                                              
        * Platform: Java 8   
        * Main Framework: Spring Framework 4.3.3, Spring Boot 1.4.1   
        * Build Tool: Maven 3.3.1             
    
    2. Controller                              
        * Request Processing: Spring REST 4.3.3   
        * Versioning: URL         
    
    3. Service                                                                      
        * VM -> DO -> DTO: Model Mapper 0.7.5 (Dozer 5.5.1)          
    
    4. Persistence                                                                  
        * ORM: Hibernate JPA 2.1                                                     
            - Projection: Snapshot (Multiple Classes per Table)   
            - Optimistic Locking: Version Attribute             
            - Type-safe Query: Metadata Model                              
        * JDBC: Spring JDBCTemplate      
    
    5. Cross-Cutting Concerns (3C)                                                                          
        * Logging: Request, Method                         
        * Exception Handling: Business Exception, Specific Exceptions, Runtime Exception     
        * Transaction: Spring Transaction 4.1.3                       
            - Isolation: Isolation.DEFAULT (isolation level of the database)
            - Propagation: Propagation.REQUIRED (use existing or create new)
            - Rollback: Exception.class
            - Timeout: -1
        * Security: Spring Security 4.1.3                                          
            - Authentication: Token-based with Username/Password                              
            - Authorization: ROLE-based (Request, Method)                             
            - Password Encryption: jBCrypt 0.4
            - Threats: 
                . Cross-Site Scripting (XSS): Encoding                      
                . SQL Injection: Named Parameters                                                                             
        * Caching                                                                                            
            - Service Layer: Spring Data Redis 1.7.5                                                         
            - ORM                                                                                           
                . L1: Entity                                                         
                . L2: Hibernate EHCache 5.0.9                                        
        * Uploading                                                                                


3. Development
    * Core
        - Architecture        
            . Setup Project Structure            
            . Config embedded server  
            . Config ORM and database  
            . Create Domain Objects  
            . Run project to generate database  
            . Run data/data.sql to init data  
            . Implement /health endpoint            
            . Setup Logging            
            . Setup Exception Handling            
            . Setup Transaction            
            . Setup Security            
            . Setup Caching with Spring Data Redis and Redis            
            . Implement Uploading    
            . Setup Spring Session   
            . Setup Spring Boot Actuator  
               
        - User Management  
            . Setup Model Mapper  
            . Implement User Stories  
            . Implement Password Encryption  
            
    * Application-Specific
        - University Management
        - Department Management
        - Course Management
        - Student Management
        - Teacher Management
        - Class Management

4. Testing
    * Unit Test
        - Line Coverage: 80%
        - Cyclomatic Complexity Coverage: 80%
    * Integration Test
        - rest-consume
    * Load Test
        - JMeter

5. Deployment
    * Load Balancer: HAProxy 1.4.24
    * Web Server: Embedded Jetty 9.3.11
    * NoSQL DB: Redis 2.8.4
    * RDBMS: MySQL 5.5.54

6. Monitoring
    * JMX & JConsole
    * Spring Boot Actuator

*** Environment Setup ***
* Development Environment
    - Install JDK 8
    - Install Maven 3.3+
    - Install Intellij (or your prefer IDE)
    - Clone project from Git repo: http://git.harveynash.vn/hoangtruong/common-java-app/commits/develop 
    - Open/Import project to the IDE
    - Install MySQL
    - Install Redis 
    - Update configuration params
    - Create database
    - Run project in IDE 
    - Access http://localhost:9090/v1/health to verify the app is working well
    - Install Postman
    - Open Postman, import ~/data/postman.json

* Stage/Production Environment
    - At Dev Env: Build: ```mvn clean package```
    - Deploy artifact <filename>.jar in ~/target to Stage/Prod Env
    - Install JRE 8
    - Install MySQL
    - Install Redis 
    - Update configuration params
    - Create database
    - Setup SSL (Secure Socket Layer)
    - Override configuration params in, for example, application-override.yml
    - Run: ```java -jar artifact.jar --spring.config.location=application-override.yml --logging.config=./logback-spring.xml```
