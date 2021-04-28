package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Data;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Data

class DeliveryTest {

    String firstDate = DataGenerator.getDate(4);
    String newDate = DataGenerator.getDate(10);
    String city = DataGenerator.getCity();
    String name = DataGenerator.getName();
    String phone = DataGenerator.getPhone();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {

        $("[data-test-id='city'] input").setValue(city);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id = 'success-notification'] .notification__content").shouldBe(visible, Duration.ofMillis(15000)).shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(newDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id = 'success-notification'] .notification__content").shouldBe(visible, Duration.ofMillis(15000)).shouldHave(exactText("Встреча успешно запланирована на " + newDate));

    }

}
