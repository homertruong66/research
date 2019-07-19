# Introduction
A System that helps e-commerce companies reduce digital marketing cost by using Affiliate Network


# ROLEs
1. **ADMIN**: Administration for RMS
2. **AFFILIATE**: Interaction with sales pages of Subscribers
3. **CHANNEL**: Administration of e-shop transactions
4. **SUBS_ADMIN**: Administration for Subscribers
5. **ACCOUNTANT**: Accounting for Subscribers


# Persons
1. **Admin**: Admin of RMS  -> have default ROLE: ADMIN
2. **Affiliate** -> have default ROLE: AFFILIATE
3. **Channel**: sales pages of Subscribers -> have default ROLE: CHANNEL
4. **SubsAdmin**: Admin of Subscribers -> have default ROLE: SUBS_ADMIN, can have ACCOUNTANT


# Features
1	System Security
2	Subscriber Management
3	Channel Management
4	Affiliate Management
5	Discount Code
6	Post Management
7	Share Management
8	Customer Management
9	Product Management
10	Order Management
11	Commission Management
12	Payment Management
13	System Configuration
14	Notification Management
15	Guide Management
16	Bill Management
17	Infusion Integration
18	Getfly Integration
19	Affiliate Commission Trace
20	Affiliate Payment Trace
21	Affiliate Balance Management
22	Data Export


# Technologies
## Architectural Technologies
* [Spring Boot](https://projects.spring.io/spring-boot/) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".
* [Spring and Spring REST](http://projects.spring.io/spring-framework/) - The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications.
* [Spring Security](https://projects.spring.io/spring-security/) - A powerful and highly customizable authentication and role-based authorization framework.
* [JWT](https://jwt.io/) - An open, industry standard RFC 7519 method for representing claims securely between two parties.
* [Hibernate ORM](http://hibernate.org/orm/) - A framework providing idiomatic persistence for Java and relational databases.
* [MySQL] (http://www.mysql.com/) - an open-source RDBMS. Its name is a combination of "My", the name of co-founder Michael Widenius's daughter, and "SQL".


## Flyway 
Flyway is used as a db versioning tool
* run `mvn flyway:migrate` will apply all sql scripts in ~/src/main/resources/flyway/migrations
* every change to db should be put in ~/src/main/resources/flyway/migrations with correct incremental versions


# Architecture
Web Browser/Mobile App <---> **Controller** <---> **Service** <---> **Persistence** <---> MySQL
* **SecurityTokenFilter Layer**: receive HTTP requests, verify security token.
* **Controller Layer**: receive HTTP requests, pass view models to Service to do biz action.
* **Service Layer**: control method-level and data-level authorization, handle biz action (with biz transaction if any),
connect to Persistence to manipulate data, return DTO to Controller.
* **Persistence Layer**: process data with MySQL.


# File Structure
src/main/java/com/rms/rms
|_ **common**          --> contains classes used by many layers          
|&nbsp;&nbsp;&nbsp;|_ config       
|&nbsp;&nbsp;&nbsp;|_ constant      
|&nbsp;&nbsp;&nbsp;|_ dto           
|&nbsp;&nbsp;&nbsp;|_ exception     
|&nbsp;&nbsp;&nbsp;|_ util          
|&nbsp;&nbsp;&nbsp;|_ view_model    
|_ **controller**      
|&nbsp;&nbsp;&nbsp;|_ filter  
|&nbsp;&nbsp;&nbsp;|_ |&nbsp; _AppCorsFilter.java_  --> allow FE to access BE  
|&nbsp;&nbsp;&nbsp;|_ |&nbsp; _HttpLogFilter.java_  --> log request info  
|&nbsp;&nbsp;&nbsp;|_ |&nbsp; _SecurityTokenFilter.java_    --> check security token in request
|&nbsp;&nbsp;&nbsp;|_ v1       
|&nbsp;&nbsp;&nbsp;|_ v2  
|&nbsp;&nbsp;&nbsp;|_ ...
|_ **integration**              --> contains Classes to connect external services
|_ **persistence**      
|_ **service**        
|&nbsp;&nbsp;&nbsp;|_ model      --> contains Domain Objects (DOs)
|_ **task_processor**            --> contains Task Processors to process tasks run by ServiceExecutor


src/main/resources  
|_ flyway.migrations    --> contains .sql files for migrating system data  
|_ hbm                  --> contains hibernate mapping files  
|_ application.yml      --> contains application configuration properties
|_ banner.txt           --> used by Spring Boot to show fancy ASCII text when running
|_ ehcache.xml          --> config L2 Cache (Hibernate Session is L1 Cache)
|_ logback-spring.xml   --> config logging, especially SQL


# Onboarding for new developer
## Setup development environment
1. Install Java 8
2. Install Maven 3.3
3. Install MySQL 5.7
4. Install HeidiSQL (RDBMS tool)
5. Install SourceTree (or other Git tool)
6. Install IntelliJ (IDE)

## Build and run project
* Clone the rms repo: git clone https://username:password@git.devnet.vn/RMS/RMS.git
* Open the **rms** project in IntelliJ
* Press Alt+F12 to open Terminal, then type the command: ```mvn clean package```
* Create a database named: **rms**
* Run the application in IntelliJ
* Connect to **rms** database using HeidiSQL
* Execute file **~/data/rms_init_data.sql** and **~/data/rms_domains.sql** in HeidiSQL to initialize support data


# Coding Convention
## Rules

1. **Order in a class**: 
* properties (sorted ab..z)
* constructor 
* public methods (sorted ab..z)
* utilities (private methods: sorted ab..z)

2. **Naming**:
* Use camel case for Java classes, methods and variables; use lower case and underscore for URL and JSON
* Object name is the name of its Class with the first letter lowered case
* Use **Noun for variables**, **methods start with a Verb**
* For supporting data object (Domain), do not need to create view model when it's nested in another view model

3. **Code Style**: 
* Always use { } for "if" even it has only one statement
* Use new line for "else", "catch", "finally"  
* Use standard flow in Service Layer:
Create case:
{
logger.info("<method name: " + params);

// process view model
  ...
// validate biz rules
  ...
// do authorization
  ...
// do biz action
  ...
}

Update case:
{
logger.info("<method name: " + params);

// process view model
  ...
// validate biz rules
  ...  
// do authorization
  ...
// map input to existingDO
  ...
// do biz action
  ...
}

Search case:
{
logger.info("<method name: " + params);

// setup search criteria
  ...
// process custom criteria
  ...
// do authorization
  ...
// do biz action
  ...
}

4. **Data Loading**: 
* **Be aware of lazily loaded data in Controller Layer to avoid exception**, must get enough data in Service layer
* Create/Update methods in Service Layer return the DTO and its associations as input view model (same properties).
* Other biz methods (except get/search) return the DTO without its associations in Service Layer, ex: activate, changePassword.
* For get/search methods, must specify explicitly lazily loaded associated objects using boolean params in Service Layer.


5. **Association Cascading**  
* **Composition**: cascade=delete  
* **Do not use cascade=all if you can not control how Hibernate will take actions**   
-> in this case, must set inverse=true for the collection in 1-to-many associations  
-> delete item MANUALLY when it is removed from the collection because Hibernate will not manage orphan item  

6. toString(): do not include associations to avoid recursive calls


# Design Note
## Allow anonymous access
* In **SecurityTokenFilter**, add a condition to method noNeedToken() to bypass token checking
* In **WebSecurityConfig**, add a config to method configure() to bypass Spring Security authentication

## Authorization
* Method-level: use ROLE
* Data-level: check LoggedUser and input DOs

## Model Driven Design (MDD)
* **Domain Model**: https://git.devnet.vn/RMS/RMS/blob/develop/data/RMS_Domain%20Model.png

## ORM Mapping Settings
### Default Settings
* 1:1 or n:1 -> eager loading
* 1:n or n:n -> lazy loading

### Specific Settings: many-to-many or eager loading
* **User** n:n **Role** -> **EAGER**

## GenericDao, <DO>Dao, and SpecificDao 
* The GenericDao class supports all operations on a DO. 
* If there's special case for a DO, extend GenericDao, for example: PersonDao.
* For any specific operations relating to more than one DO or custom SQL queries, use SpecificDao class.

## Generic Search
Generic Search supports searching, sorting and paging on a DO with criteria generated from a DO.
Moreover, Generic Search also supports custom criteria on properties of DO:
* ">"
* ">=" 
* "<" 
* "<=" 
* "="
* "<>"
* "~=" (like)

For example: search Admin who have last name = "Truong" and first name starting with "H"
{  
&nbsp;&nbsp; "page_index": 1,  
&nbsp;&nbsp; "page_size": 6,  
&nbsp;&nbsp; "criteria": {  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "last_name": "Truong"  
&nbsp;&nbsp; },  
&nbsp;&nbsp; "custom_criteria": {
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "first_name~=" : "H%"  
&nbsp;&nbsp; }  
}  

# Misc
## Jira sample code format for SCRUD
1) Search
API: POST, /v1/{domain_object_name}s/search

Input
{code}
...
{code}

Output
{code}
...
{code}


2) Create
API: POST, /v1/{domain_object_name}s
Input
{code}
...
{code}

Output
{code}
...
{code}


3) Read
API: GET, /v1/{domain_object_name}s/{id}

Output
{code}
...
{code}


4) Update
API: PUT, /v1/{domain_object_name}s/{id}

Input
{code}
...
{code}

Output
{code}
...
{code}


5) Delete
API: DELETE, /v1/{domain_object_name}s/{id}
