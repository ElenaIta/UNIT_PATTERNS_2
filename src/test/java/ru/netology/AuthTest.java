package ru.netology;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getUser;
import static ru.netology.DataGenerator.getAnotherLogin;
import static ru.netology.DataGenerator.getAnotherPassword;


public class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Successful active login registered user")
    void successfulActiveLoginRegisteredUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(byText("Продолжить")).click();
        $(byText("Личный кабинет")).should(Condition.visible);
    }

    @Test
    @DisplayName("Error when logging in with the wrong username")
    void errorWhenLoggingWithWrongUsername() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getAnotherLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $(byText("Продолжить")).click();
        $x("//div[text()=\"Ошибка\"]").should(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getAnotherPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $(byText("Продолжить")).click();
        $x("//div[text()=\"Ошибка\"]").should(Condition.visible);
    }

    @Test
    @DisplayName("Error when logging in as a blocked registered user")
    void errorWhenLoggingBlockedRegisteredUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $(byText("Продолжить")).click();
        $x("//div[text()=\"Ошибка\"]").should(Condition.visible);
    }

    @Test
    @DisplayName("Error when logging in as an unregistered user")
    void errorWhenLoggingUnregisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $(byText("Продолжить")).click();
        $x("//div[text()=\"Ошибка\"]").should(Condition.visible);
    }
}
