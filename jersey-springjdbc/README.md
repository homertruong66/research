An application example using Spring Boot &amp; Jersey

## Requirements
* Java 8
* maven 3

## Unit Test
### Approach - Integration test with mocked JDBCTemplate
* Our tests are actually integration tests except for mocked JDBCTemplate.
* The real web environment will be setup for unit test. 
* With this approach all the application context and business flow will be covered by test.

#### Unit test can cover
* Real Spring context creation
* Embedded web container 
* Can test a full business flow (except real db data)
* Good for code refactoring and maintenance (fewer number of test cases)

## Build
**maven script**
mvn clean package

We are using spring-boot-maven-plugin to build a fat jar.

## Deployment
**execute script**
java -jar springboot-jersey.jar --spring.config.location=application-override.yml --logging.config=./logback-spring.xml
