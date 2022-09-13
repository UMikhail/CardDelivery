import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

class CardDeliveryTest {
    @Test
    void testValidForm() {
        //Configuration.holdBrowserOpen=true;

        //задаю дату заполнения через переменную
        String data = "31.12.2222";

        Selenide.open("http://localhost:9999/");
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(data);
        $("[name=\"name\"]").setValue("Иван Петров-Сидоров");
        $("[name=\"phone\"]").setValue("+79876543210");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__content\"]").click();
        String text = $("[data-test-id=\"notification\"]").should(Condition.visible, Duration.ofSeconds(15)).getText();
        Assertions.assertEquals("Успешно!\nВстреча успешно забронирована на " + data, text.trim());
    }

    @Test
    void testInValidData() {
        //Configuration.holdBrowserOpen=true;

        //задаю дату заполнения через переменную
        String data = "13.09.2022";

        Selenide.open("http://localhost:9999/");
        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(data);
        $("[name=\"name\"]").setValue("Иван Петров-Сидоров");
        $("[name=\"phone\"]").setValue("+79876543210");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__content\"]").click();
        String text = $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").getText();
        Assertions.assertEquals("Заказ на выбранную дату невозможен", text);
    }
}
