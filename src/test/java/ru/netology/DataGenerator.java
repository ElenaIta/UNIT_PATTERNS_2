package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;


@Data
@AllArgsConstructor
public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));


    private static void setUpAll(RegistrationDto user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String getAnotherLogin() {
        String login = faker.name().name();
        return login;
    }

    public static String getAnotherPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            RegistrationDto user = new RegistrationDto(getAnotherLogin(), getAnotherPassword(), status);
            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            setUpAll(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class RegistrationDto {
        private String login;
        private String password;
        private String status;
    }
}

