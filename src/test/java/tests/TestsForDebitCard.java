package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import data.SqlHelper;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.BuyWithDebitCard;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsForDebitCard {
    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    public void setUpEach() {
        String url = System.getProperty("site.url");
        open(url);
        SqlHelper.clearData();
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @Test
    @Description("осуществить покупку с картой Approved")
    void shouldDebitByCardWithApproved() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard buyWithCard = new BuyWithDebitCard();
        buyWithCard.fillData(DataGenerator.getApprovedCard());
        buyWithCard.notificationOfSuccessfulOperation();
        assertEquals("APPROVED", SqlHelper.getDebitStatus());
    }

    @Test
    @Description("осуществить покупку с картой Decline")
    void shouldDebitByCardWithDecline() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard buyWithCard = new BuyWithDebitCard();
        buyWithCard.fillData(DataGenerator.getDeclinedCard());
        buyWithCard.errorNotification();
        assertEquals("DECLINED", SqlHelper.getDebitStatus());
    }

    @Test
    @Description("ввести номер несуществующей карты")
    void shouldNonExistentCardDebitCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getNonExistentCard());
        debitPage.errorNotification();
    }

    @Test
    @Description(" ввести недостаточное количество символов")
    void shouldIncompleteCardField() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteCardField());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("ввести 17 символов в поле \"Номер карты\"")
    void shouldALargeNumberOfDigitsCardFiled() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getALargeNumberOfDigitsCardFiled());
        debitPage.errorNotification();
    }

    @Test
    @Description("поле \"Номер карты\" оставить пустым")
    void shouldEmptyCardForm() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyCardForm());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Номер карты\" ввести символы")
    void shouldFillInTheCardFormWithSymbols() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheCardFormWithSymbols());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести 00")
    void shouldZeroMonthApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getZeroMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести 00")
    void shouldZeroMonthDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getZeroMonthDeclinedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести 13")
    void shouldInvalidMonthApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getInvalidMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести 13")
    void shouldInvalidMonthDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getInvalidMonthDeclinedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести одну цифру: 0-9")
    void shouldIncompleteMonthFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteMonthFieldApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести одну цифру: 0-9")
    void shouldIncompleteMonthFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteMonthFieldDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести прошедший месяц текущего года")
    void shouldBygoneMonthApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getBygoneMonthApprovedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести прошедший месяц текущего года")
    void shouldBygoneMonthDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getBygoneMonthDeclinedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести символы")
    void shouldFillInTheMonthFormWithSymbolsApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheMonthFormWithSymbolsApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Месяц\" ввести символы")
    void shouldFillInTheMonthFormWithSymbolsDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheMonthFormWithSymbolsDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Месяц\" оставить пустым")
    void shouldEmptyMonthFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyMonthFormApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Месяц\" оставить пустым")
    void shouldEmptyMonthFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyMonthFormDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Год\" заполнить прошедшим годом")
    void shouldBygoneYearApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getBygoneYearApprovedCard());
        assertEquals("Истёк срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Год\" заполнить прошедшим годом")
    void shouldBygoneYearDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getBygoneYearDeclinedCard());
        assertEquals("Истёк срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Год\" заполнить значением +6 лет от текущего года")
    void shouldANonExistentYearApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getANonExistentYearApprovedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Год\" заполнить значением +6 лет от текущего года")
    void shouldANonExistentYearDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getANonExistentYearDeclinedCard());
        assertEquals("Неверно указан срок действия карты", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Год\" оставить пустым")
    void shouldEmptyYearFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyYearFormApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Год\" оставить пустым")
    void shouldEmptyYearFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyYearFormDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Год\" ввести символы")
    void shouldFillInTheYearFormWithSymbolsApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheYearFormWithSymbolsApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Год\" ввести символы")
    void shouldFillInTheYearFormWithSymbolsDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheYearFormWithSymbolsDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Год\" ввести одну цифру: 0-9")
    void shouldIncompleteYearFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteYearFieldApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Год\" ввести одну цифру: 0-9")
    void shouldIncompleteYearFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteYearFieldDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Владелец\" ввести имя кирилицей")
    void shouldCyrillicCharactersInTheOwnerFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getCyrillicCharactersInTheOwnerFieldApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Владелец\" ввести имя кирилицей")
    void shouldCyrillicCharactersInTheOwnerFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getCyrillicCharactersInTheOwnerFieldDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Владелец\" ввести цифры")
    void shouldANumberInTheOwnerNameApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getANumberInTheOwnerNameApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Владелец\" ввести цифры")
    void shouldANumberInTheOwnerNameDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getANumberInTheOwnerNameDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Владелец\" ввести специальные символы")
    void shouldCharactersInFieldOwnerApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getCharactersInFieldOwnerApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("в поле \"Владелец\" ввести специальные символы")
    void shouldCharactersInFieldOwnerDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getCharactersInFieldOwnerDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Владелец\" заполнить данными состоящими из 1го символа латинского алфавита")
    void shouldOneCharacterInFieldOwnerApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getOneCharacterInFieldOwnerApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Владелец\" заполнить данными состоящими из 1го символа латинского алфавита")
    void shouldOneCharacterInFieldOwnerDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getOneCharacterInFieldOwnerDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Владелец\" заполнить данными состоящими из 2х символов латинского алфавита")
    void shouldShortNameInOwnerApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard buyWithCard = new BuyWithDebitCard();
        buyWithCard.fillData(DataGenerator.getShortNameInOwnerApprovedCard());
        buyWithCard.notificationOfSuccessfulOperation();
    }

    @Test
    @Description("поле \"Владелец\" заполнить данными состоящими из 2х символов латинского алфавита")
    void shouldShortNameInOwnerDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getShortNameInOwnerDeclinedCard());
        debitPage.errorNotification();
    }

    @Test
    @Description("поле \"Владелец\" заполнить данными из латинского алфавита, в количестве 36 символов")
    void shouldThirtySixCharactersInTheOwnerApprovedCardField() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getThirtySixCharactersInTheOwnerApprovedCardField());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Владелец\" заполнить данными из латинского алфавита, в количестве 36 символов")
    void shouldThirtySixCharactersInTheOwnerDeclinedCardField() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getThirtySixCharactersInTheOwnerDeclinedCardField());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Владелец\" оставить пустым")
    void shouldEmptyOwnerFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyOwnerFormApprovedCard());
        assertEquals("Поле обязательно для заполнения", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"Владелец\" оставить пустым")
    void shouldEmptyOwnerFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyOwnerFormDeclinedCard());
        assertEquals("Поле обязательно для заполнения", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"CVC\" заполнить не полностью")
    void shouldIncompleteCvcFieldApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteCvcFieldApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"CVC\" заполнить не полностью")
    void shouldIncompleteCvcFieldDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getIncompleteCvcFieldDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"CVC\" заполнить символами")
    void shouldFillInTheCvcFormWithSymbolsApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheCvcFormWithSymbolsApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"CVC\" заполнить символами")
    void shouldFillInTheCvcFormWithSymbolsDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getFillInTheCvcFormWithSymbolsDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"CVC\" оставить пустым")
    void shouldEmptyCvcFormApprovedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyCvcFormApprovedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

    @Test
    @Description("поле \"CVC\" оставить пустым")
    void shouldEmptyCvcFormDeclinedCard() {
        StartPage startPage = new StartPage();
        startPage.openBuyWithDebitCard();
        BuyWithDebitCard debitPage = new BuyWithDebitCard();
        debitPage.fillData(DataGenerator.getEmptyCvcFormDeclinedCard());
        assertEquals("Неверный формат", debitPage.getInputInvalid());
    }

}
