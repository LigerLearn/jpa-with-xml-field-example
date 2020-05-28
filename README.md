# Creating Java JPA Entities with an XML Field

This is the code sample to accompany the blog post: [Creating Java JPA entities with an XML field](https://ligerlearn.com/creating-java-jpa-entities-with-an-xml-field/).

This repository illustrates the usage of an XML field within a JPA entity.

Steps to see everything in action:
1. Ensure that you have created the PostgreSQL DB, and the schema (see `src/main/resources/db` for the schema scripts).
2. Update the `src/main/resources/application.properties` file to have the correct values for your data source.
3. Run the `bootRun` Gradle task to launch the Spring Boot application.
4. Go to `http://localhost:8080/swagger-ui.html` to access the UI to easily make requests.
5. Make a POST request to create a new Person
6. Check the DB - you will have stored XML!
7. Make a PUT request to update the Person
8. Check the DB - your XML would have been updated!

Enjoy