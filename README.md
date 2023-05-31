# Indra-Avitech Hiring Task 

## App Description
- `ApplicationRunner::main` runs the showcase
  - commands required by the task can be found in `CommandProducer::run`
- `UserRepositoryTest` contains unit tests
- in-memory database H2 is used instead of embedded one

### Producer-Consumer pattern
- Producer component
  - class `CommandProducer`
  - interface `Command` + implementations
- Consumer component
  - class `CommandConsumer`
  - runs in an infinite while loop, shutdown of the loop is managed by `ShutdownCommand`
- `BlockingQueue` is used as the queue interface

### Technologies
- Java 17
- Gradle
- Jakarta + Hibernate
- Hikari Connection Pool
- H2 database
- JUnit 5 + Mockito
- ULID (Universally Unique Lexicographically Sortable Identifier) as replacement for UUID
  - see class `ULID`
- Lombok
- Sl4j + Logback
