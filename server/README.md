# HoHoMalls Server

The server application is built on top
of [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
and [GraphQL](https://graphql.org/) to provide a non-blocking and robust running environment.

## Development

It's recommended to install the following IDE plugins to assist development:

- [EditorConfig](https://editorconfig.org/#download) helps maintain consistent coding styles across various editors and
    IDEs
- [Google Java Format](https://github.com/google/google-java-format) reformats Java source code to comply with Google
    Java Style
- [checkstyle](https://checkstyle.sourceforge.io/index.html) helps programmers write Java code that adheres to Google
    Java Style
- [PMD](https://pmd.github.io/#plugins) finds common programming flaws like unused variables, empty catch blocks,
    unnecessary object creation, and so forth.

Note that Gradle build will fail if the Java source code doesn't pass the check of checkstyle and PMD.
