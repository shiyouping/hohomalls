# HoHoMalls Server

The server application is built on top
of [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
and [GraphQL](https://graphql.org/) to provide a non-blocking and robust running environment.

## Modules

### Bill of Materials

- `hohomalls-bom`: A BOM subproject that provides dependency version management for other subprojects.

### Libraries

- `hohomalls-core`: A library subproject that provides the core functionalities which are the foundation of other
  subprojects. No business logic is included.
- `hohomalls-data`: A library subproject that provides the core functionalities for MongoDB and Redis. No business logic
  is included.
- `hohomalls-web`: A library subproject that provides the core functionalities for GraphQL and Spring WebFlux. No
  business logic is included.
- `hohomalls-aws`: A library subproject that provides the core functionalities for AWS services. All AWS related
  features must reside in this project to enable migration to other cloud platforms if necessary. No business logic is
  included.

### Executable Applications

- `hohomalls-app`: A monolithic, stateless and standalone application that can be scaled up and down with ease. It was
  designed and developed in the way that it can be divided into microservices without difficulties.
- `hohomalls-mongo`: A standalone project for tracking, versioning, and deploying database changes to MongoDB.

## Development

It's recommended to install the following IDE plugins to assist development:

- [EditorConfig](https://editorconfig.org/#download) helps maintain consistent coding styles across various editors and
  IDEs
- [Google Java Format](https://github.com/google/google-java-format) reformats Java source code to comply with Google
  Java Style
- [Checkstyle](https://checkstyle.sourceforge.io/index.html) helps programmers write Java code that adheres to Google
  Java Style
- [PMD](https://pmd.github.io/#plugins) finds common programming flaws like unused variables, empty catch blocks,
  unnecessary object creation, and so forth.

Note that Gradle build will fail if the Java source code doesn't pass the check of checkstyle and PMD
