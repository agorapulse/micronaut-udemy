## Micronaut Udemy Demo App

This is the Groovy-based version of Udemy demo apps created during the [Micronaut Udemy course](https://www.udemy.com/course/learn-micronaut/).

It uses the following stack:
* **Groovy** as coding language
* **Gradle** for build, test and dependency management
* **Spock** for testing
* **Java 8** for runtime

The original Java-based versions can be found [here](https://github.com/danielprinz/micronaut-udemy) and is based on:
* **Java** as coding language
* **Maven** for build, test and dependency management
* **JUnit** for testing
* **Java 11** for runtime

### mn-stock-brocker

Main demo app used during the course to show main Web/API, security and data JPA features.

Used in:
* Section 2: Micronaut Quickstart (`HelloWorldontroller`)
* Section 3: Micronaut Web
* Section 4: Micronaut Security
* Section 5: Micronaut Data

### mn-jdbc-auth 

Simple demo app to experiment with security based on JDBC data provider.

Used in :
* Section 4: Micronaut Security - 25. JDBC Authentication Provider
* Section 4: Micronaut Security - 26. Custom JWT

### mn-funding

Simple demo app to configure and use Liquibase instead of Flyway and data JDBC features.

Used in :
* Section 5: Micronaut Data - 39. Schema migration with Liquibase
* Section 5: Micronaut Data - 40. Micronaut Data JDBC - Repository

### mn-websockets

Simple demo app to show web sockets features.

Used in :
* Section 7: Using Web Sockets
