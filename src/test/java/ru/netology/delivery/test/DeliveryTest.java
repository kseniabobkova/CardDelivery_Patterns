package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

@Data

class DeliveryTest {

    DataGenerator dataGenerator = new DataGenerator();
    String firstDate = dataGenerator.getDate(4);
    String newDate = dataGenerator.getDate(10);
    String city = dataGenerator.getCity();
    String name = dataGenerator.getName();
    String phone = dataGenerator.getPhone();


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
