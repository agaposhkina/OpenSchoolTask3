## About
Simple example of utilizing *Spring AOP* for logging purposes.
The application contains services for managing *Users* and *Orders*.
Spring AOP is used for logging the invocations of service methods(such as user and order creation, update and delete operations).

---
### Log4j2
Log4j2 is used for logging. Logger configuration defined by following property:
```
logging.config=classpath:logger/log4j2.xml
```

---

### Spring AOP
The following advice types are used:

*@Before* for all public service methods to log service name, method name, method params when methods are called

*@AfterThrowing* for all public service methods to log service name, method name, exception message, when methods finished exceptionally

*@AfterReturning* for all public service methods to log service name, method name, result when methods finished normally

---
### Tests
Tests for each service cover some scenarios of using services and verify resulting log output.
As tests intended on testing services and aspects, using Mockito for mocking repositories.

---
### Build/Run application
The project uses Maven build.
To build the executables, use mvnw (the Maven wrapper) at the command line:
```
./mvnw clean package
```
After building:
```
java -jar target/OpenSchoolTask3-0.0.1-SNAPSHOT.jar
```
To run tests only:
```
./mvnw clean test
```