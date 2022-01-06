# Hohomalls Mongo

This project is for tracking, versioning, and deploying database changes to MongoDB. It's another standalone project
built on top of [Mongock](https://mongock.io/).

## Change Unit Rules

- The id of change units must be identical to the Java class name
- The systemVersion of change units must be determined by the project version
- The order of change units must be increased by 1 each time
- @Execution and @RollbackExecution must be implemented in every single change unit
- Perform DDL operations in @BeforeExecution
- Use MongoTemplate to execute [MongoDB Commands](https://docs.mongodb.com/v4.4/reference/command/) in JSON files
- Once the change units are executed successfully, they are not allowed to change again. Any following changes should be
  in new change units.

## How to Build

````bash
cd hohomalls/server/hohomalls-mongo
../gradlew clean bootJar

# Option 1: Java System Properties (VM Arguments)
java -jar -Dspring.profiles.active=local build/libs/hohomalls-mongo-1.0.0-SNAPSHOT.jar

# Option 2: Program arguments
java -jar build/libs/hohomalls-mongo-1.0.0-SNAPSHOT.jar --spring.profiles.active=local 
````

## How to Run

````bash
cd hohomalls/server/hohomalls-mongo/build/libs/

# Option 1: Java System Properties (VM Arguments)
java -jar -Dspring.profiles.active=local hohomalls-mongo-1.0.0-SNAPSHOT.jar

# Option 2: Program arguments
java -jar hohomalls-mongo-1.0.0-SNAPSHOT.jar --spring.profiles.active=local 
````
