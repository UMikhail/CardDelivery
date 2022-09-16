import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeEach
    public void setupTest() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999/");
    }

    @Test
    public void testValidForm() {

        //задаю дату заявки через переменную planningDate + 3 дня, для проверки валидности даты
        DataService service = new DataService();
        String planningDate = service.generateDate(3);

        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Иван Петров-Сидоров");
        $("[name=\"phone\"]").setValue("+79876543210");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__content\"]").click();
        String text = $("[data-test-id=\"notification\"]").should(Condition.visible, Duration.ofSeconds(30)).getText();
        Assertions.assertEquals("Успешно!\nВстреча успешно забронирована на " + planningDate, text.trim());
    }

    @Test
    public void testInValidData() {

        //задаю дату заявки через переменную planningDate + 1 день, для проверки невалидности даты
        DataService service = new DataService();
        String planningDate = service.generateDate(1);

        $("[placeholder=\"Город\"]").setValue("Йошкар-Ола");
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Иван Петров-Сидоров");
        $("[name=\"phone\"]").setValue("+79876543210");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__content\"]").click();
        String text = $x("//*[contains(text(),'Заказ на выбранную дату невозможен')]").getText();
        Assertions.assertEquals("Заказ на выбранную дату невозможен", text);
    }
}
