package ru.netology;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }

    @Value
    public static class User {
        private static final Faker faker = new Faker();
        private static final String username = faker.name().username();
        private static final String password = faker.internet().password();

        public static void activeUserRegistration() {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new testUser(username, password, "active")))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static void inactiveUserRegistration() {
            given()
                    .baseUri("http://localhost:9999")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new testUser(username, password, "blocked")))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String getUsername() {
            return username;
        }

        public static String getPassword() {
            return password;
        }

        public static String getAnotherUsername() {
            return faker.name().username();
        }

        public static String getAnotherPassword() {
            return faker.internet().password();
        }
    }

    @Value
    private static class testUser {
        String login;
        String password;
        String status;
    }
}
