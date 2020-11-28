## Micronaut Udemy Demo App

This is the Groovy-based version of Udemy demo app of the [Micronaut Udemy course](https://www.udemy.com/course/learn-micronaut/).

Instead of using **Java+Maven+JUnit** stack , it uses **Groovy+Gradle+Spock** stack.

The original Java-based version can be found [here](https://github.com/danielprinz/micronaut-udemy)

# Prerequisites
- JVM 8.0.275-amzn (with SDKMan)

# Notes

## Micronaut Security
Do not forget `@Singleton` annotation for `AuthenticationProviderUserPassword`
