package page;

import com.codeborne.selenide.SelenideElement;
import data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyWithDebitCard {
    private SelenideElement heading = $$(".button").find(exactText("Купить"));
    private SelenideElement numberCard = $("input[type=\"text\"][placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement monthRelease = $("input[type=\"text\"][placeholder=\"08\"]");
    private SelenideElement yearRelease = $("input[type=\"text\"][placeholder=\"22\"]");
    private SelenideElement owner = $$(".input").find(exactText("Владелец")).$(".input__control");
    private SelenideElement codeSecurity = $("input[type=\"text\"][placeholder=\"999\"]");
    private SelenideElement button = $$(".button").find(exactText("Продолжить"));

    private final SelenideElement statusOk = $(".notification_status_ok");
    private final SelenideElement statusError = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub");

    public BuyWithDebitCard() {
        heading.shouldBe(visible);
    }

    public void notificationOfSuccessfulOperation() {
        statusOk.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void errorNotification() {
        statusError.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void fillData(Card card) {
        numberCard.setValue(card.getNumber());
        monthRelease.setValue(card.getMonth());
        yearRelease.setValue(card.getYear());
        owner.setValue(card.getOwner());
        codeSecurity.setValue(card.getCvc());
        button.click();
    }

    public String getInputInvalid() {
        return inputInvalid.getText();
    }
}
