package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import data.SQL;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.BuyWithCreditCard;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsForCreditCard {
    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @SneakyThrows
    @BeforeEach
    public void setUpEach() {
        String url = System.getProperty("site.url");
        open(url);
        SQL.clearData();
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @SneakyThrows
    @Test
//    осуществить покупку с картой Approved
    void shouldCreditByCardWithApproved() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val buyWithCreditCard = new BuyWithCreditCard();
        buyWithCreditCard.fillData(DataGenerator.getApprovedCard());
        buyWithCreditCard.notificationOfSuccessfulOperation();
        assertEquals("APPROVED", SQL.getCreditStatus());
    }

    @SneakyThrows
    @Test
//    осуществить покупку с картой Decline
    void shouldCreditByCardWithDecline() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val buyWithCreditCard = new BuyWithCreditCard();
        buyWithCreditCard.fillData(DataGenerator.getDeclinedCard());
        buyWithCreditCard.errorNotification();
        assertEquals("DECLINED", SQL.getCreditStatus());
    }

    @Test
//    ввести номер несуществующей карты
    void shouldNonExistentCardCreditCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getNonExistentCard());
        creditPage.errorNotification();
    }

    @Test
//    ввести недостаточное количество символов
    void shouldIncompleteCardField() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteCardField());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    ввести 17 символов в поле "Номер карты"
    void shouldALargeNumberOfDigitsCardFiled() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getALargeNumberOfDigitsCardFiled());
        creditPage.errorNotification();
    }

    @Test
//    поле "Номер карты" оставить пустым
    void shouldEmptyCardForm() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyCardForm());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Номер карты" ввести символы
    void shouldFillInTheCardFormWithSymbols() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheCardFormWithSymbols());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести 00
    void shouldZeroMonthApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getZeroMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести 00
    void shouldZeroMonthDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getZeroMonthDeclinedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести 13
    void shouldInvalidMonthApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getInvalidMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести 13
    void shouldInvalidMonthDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getInvalidMonthDeclinedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести одну цифру: 0-9
    void shouldIncompleteMonthFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteMonthFieldApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести одну цифру: 0-9
    void shouldIncompleteMonthFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteMonthFieldDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести прошедший месяц текущего года
    void shouldBygoneMonthApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getBygoneMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести прошедший месяц текущего года
    void shouldBygoneMonthDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getBygoneMonthDeclinedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести символы
    void shouldFillInTheMonthFormWithSymbolsApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheMonthFormWithSymbolsApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Месяц" ввести символы
    void shouldFillInTheMonthFormWithSymbolsDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheMonthFormWithSymbolsDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Месяц" оставить пустым
    void shouldEmptyMonthFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyMonthFormApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Месяц" оставить пустым
    void shouldEmptyMonthFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyMonthFormDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Год" заполнить прошедшим годом
    void shouldBygoneYearApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getBygoneYearApprovedCard());
        assertEquals("Истёк срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    поле "Год" заполнить прошедшим годом
    void shouldBygoneYearDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getBygoneYearDeclinedCard());
        assertEquals("Истёк срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    поле "Год" заполнить значением +6 лет от текущего года
    void shouldANonExistentYearApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getANonExistentYearApprovedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//    поле "Год" заполнить значением +6 лет от текущего года
    void shouldANonExistentYearDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getANonExistentYearDeclinedCard());
        assertEquals("Неверно указан срок действия карты", creditPage.getInputInvalid());
    }

    @Test
//     поле "Год" оставить пустым
    void shouldEmptyYearFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyYearFormApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//     поле "Год" оставить пустым
    void shouldEmptyYearFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyYearFormDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Год" ввести символы
    void shouldFillInTheYearFormWithSymbolsApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheYearFormWithSymbolsApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Год" ввести символы
    void shouldFillInTheYearFormWithSymbolsDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheYearFormWithSymbolsDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Год" ввести одну цифру: 0-9
    void shouldIncompleteYearFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteYearFieldApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Год" ввести одну цифру: 0-9
    void shouldIncompleteYearFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteYearFieldDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Владелец" ввести имя кирилицей
    void shouldCyrillicCharactersInTheOwnerFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getCyrillicCharactersInTheOwnerFieldApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Владелец" ввести имя кирилицей
    void shouldCyrillicCharactersInTheOwnerFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getCyrillicCharactersInTheOwnerFieldDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Владелец" ввести цифры
    void shouldANumberInTheOwnerNameApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getANumberInTheOwnerNameApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    в поле "Владелец" ввести цифры
    void shouldANumberInTheOwnerNameDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getANumberInTheOwnerNameDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//     в поле "Владелец" ввести специальные символы
    void shouldCharactersInFieldOwnerApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getCharactersInFieldOwnerApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//     в поле "Владелец" ввести специальные символы
    void shouldCharactersInFieldOwnerDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getCharactersInFieldOwnerDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Владелец" заполнить данными состоящими из 1го символа латинского алфавита
    void shouldOneCharacterInFieldOwnerApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getOneCharacterInFieldOwnerApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Владелец" заполнить данными состоящими из 1го символа латинского алфавита
    void shouldOneCharacterInFieldOwnerDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getOneCharacterInFieldOwnerDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Владелец" заполнить данными состоящими из 2х символов латинского алфавита
    void shouldShortNameInOwnerApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val buyWithCreditCard = new BuyWithCreditCard();
        buyWithCreditCard.fillData(DataGenerator.getShortNameInOwnerApprovedCard());
        buyWithCreditCard.notificationOfSuccessfulOperation();
    }

    @Test
//    поле "Владелец" заполнить данными состоящими из 2х символов латинского алфавита
    void shouldShortNameInOwnerDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getShortNameInOwnerDeclinedCard());
        creditPage.errorNotification();
    }

    @Test
//    поле "Владелец" заполнить данными из латинского алфавита, в количестве 36 символов
    void shouldThirtySixCharactersInTheOwnerApprovedCardField() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getThirtySixCharactersInTheOwnerApprovedCardField());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Владелец" заполнить данными из латинского алфавита, в количестве 36 символов
    void shouldThirtySixCharactersInTheOwnerDeclinedCardField() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getThirtySixCharactersInTheOwnerDeclinedCardField());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "Владелец" оставить пустым
    void shouldEmptyOwnerFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyOwnerFormApprovedCard());
        assertEquals("Поле обязательно для заполнения", creditPage.getInputInvalid());
    }

    @Test
//    поле "Владелец" оставить пустым
    void shouldEmptyOwnerFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyOwnerFormDeclinedCard());
        assertEquals("Поле обязательно для заполнения", creditPage.getInputInvalid());
    }

    @Test
//    поле "CVC" заполнить не полностью
    void shouldIncompleteCvcFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteCvcFieldApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "CVC" заполнить не полностью
    void shouldIncompleteCvcFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getIncompleteCvcFieldDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "CVC" заполнить символами
    void shouldFillInTheCvcFormWithSymbolsApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheCvcFormWithSymbolsApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "CVC" заполнить символами
    void shouldFillInTheCvcFormWithSymbolsDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getFillInTheCvcFormWithSymbolsDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "CVC" оставить пустым
    void shouldEmptyCvcFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyCvcFormApprovedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

    @Test
//    поле "CVC" оставить пустым
    void shouldEmptyCvcFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithCreditCard();
        val creditPage = new BuyWithCreditCard();
        creditPage.fillData(DataGenerator.getEmptyCvcFormDeclinedCard());
        assertEquals("Неверный формат", creditPage.getInputInvalid());
    }

}
