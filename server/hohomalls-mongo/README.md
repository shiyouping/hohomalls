# Hohomalls Mongo

This project is for tracking, versioning, and deploying database changes to MongoDB. It's another standalone project
from hohomalls-app.

## Change Unit Rules

- The id of change units must be identical to the Java class name
- The systemVersion of change units must be determined by the project version
- The order of change units must be increased by 1 each time
- @Execution and @RollbackExecution must be implemented in every single change unit
- Perform DDL operations in @BeforeExecution
- Use MongoTemplate in favour of using Repository classes directly to perform the migrations
- Once the change units are executed successfully, they are not allowed to change again. Any following changes should be
  in new change units.

See the detailed usages at https://mongock.io/