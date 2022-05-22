package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.DataGenerator.User.*;

public class AuthTest {

    SelenideElement submitButton = $("[data-test-id='action-login'] .button__text");
    SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");

    @BeforeEach
    void setUp() {
        activeUserRegistration();
        open("http://localhost:9999/");
        $("[data-test-id='login'] .input__control").setValue(getUsername());
        $("[data-test-id='password'] .input__control").setValue(getPassword());
    }

    @Test
    void shouldSucceedLogin() {
        submitButton.click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSucceedLoginByName() {
        $("[data-test-id='login'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, getAnotherUsername());
        submitButton.click();
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSucceedLoginByPassword() {
        $("[data-test-id='password'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, getAnotherPassword());
        submitButton.click();
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldNotSucceedLoginByBlock() {
        inactiveUserRegistration();
        submitButton.click();
        errorMessage.shouldBe(visible).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}
