package data;

import com.github.javafaker.Faker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.substring;


public class DataGenerator {
    private DataGenerator() {
    }

    private static String getMonth() {
        LocalDate localDate = LocalDate.now();
        int month = localDate.getMonthValue();
        return String.format("%02d", month);
    }

    private static String getBygoneMonth() {
        LocalDate localDate = LocalDate.now();
        int month = localDate.minusMonths(1).getMonthValue();
        return String.format("%02d", month);
    }

    private static String getYear() {
        DateFormat df = new SimpleDateFormat("yy");
        return df.format(Calendar.getInstance().getTime());
    }

    private static String getBygoneYear() {
        LocalDate localDate = LocalDate.now();
        int year = localDate.minusYears(1).getYear();
        return yearFormat(year);
    }

    private static String getANonExistentYear() {
        LocalDate localDate = LocalDate.now();
        int year = localDate.plusYears(6).getYear();
        return yearFormat(year);
    }

    private static String yearFormat(int year)
    {
        return substring(String.valueOf(year), -2);
    }

    private static String getName() {
        Faker faker = new Faker();
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private static String getCvc() {
        Random random = new Random();
        int cvc = random.nextInt((1000 - 1));
        return String.format("%03d", cvc);
    }

    private static String getIncompleteMonth() {
        Random random = new Random();
        int month = random.nextInt((10-1));
        return String.format("%1d", month);
    }

    private static String getIncompleteYear() {
        Random random = new Random();
        int year = random.nextInt((10-1));
        return String.format("%1d", year);
    }

    private static String getIncompleteCvc() {
        Random random = new Random();
        int cvc = random.nextInt((100-1));
        return String.format("%2d", cvc);
    }

    private static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    private static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static Card getIncompleteCardField() {
        return new Card("4444 4444 4444 444", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getALargeNumberOfDigitsCardFiled() {
        return new Card("4444 4444 4444 44433", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getEmptyCardForm() {
        return new Card("", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getNonExistentCard() {
        return new Card("4444 4444 4444 4445", getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getFillInTheCardFormWithSymbols() {
        return new Card("абвп hkfj /*^$ !hdр", getMonth(), getYear(), getName(), getCvc());
    }


    public static Card getApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getName(), getCvc());
    }

    public static Card getDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), getName(), getCvc());
    }


    public static Card getZeroMonthApprovedCard() {
        return new Card(getApprovedCardNumber(), "00", getYear(), getName(), getCvc());
    }

    public static Card getZeroMonthDeclinedCard() {
        return new Card(getDeclinedCardNumber(), "00", getYear(), getName(), getCvc());
    }

    public static Card getInvalidMonthApprovedCard() {
        return new Card(getApprovedCardNumber(), "13", getYear(), getName(), getCvc());
    }

    public static Card getInvalidMonthDeclinedCard() {
        return new Card(getDeclinedCardNumber(), "13", getYear(), getName(), getCvc());
    }

    public static Card getIncompleteMonthFieldApprovedCard() {
        return new Card(getApprovedCardNumber(), getIncompleteMonth(), getYear(), getName(), getCvc());
    }

    public static Card getIncompleteMonthFieldDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getIncompleteMonth(), getYear(), getName(), getCvc());
    }

    public static Card getBygoneMonthApprovedCard() {
        return new Card(getApprovedCardNumber(), getBygoneMonth(), getYear(), getName(), getCvc());
    }

    public static Card getBygoneMonthDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getBygoneMonth(), getYear(), getName(), getCvc());
    }

    public static Card getFillInTheMonthFormWithSymbolsApprovedCard() {
        return new Card(getApprovedCardNumber(), "l$", getYear(), getName(), getCvc());
    }

    public static Card getFillInTheMonthFormWithSymbolsDeclinedCard() {
        return new Card(getDeclinedCardNumber(), "l$", getYear(), getName(), getCvc());
    }

    public static Card getEmptyMonthFormApprovedCard() {
        return new Card(getApprovedCardNumber(), "", getYear(), getName(), getCvc());
    }

    public static Card getEmptyMonthFormDeclinedCard() {
        return new Card(getDeclinedCardNumber(), "", getYear(), getName(), getCvc());
    }

    public static Card getBygoneYearApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getBygoneYear(), getName(), getCvc());
    }

    public static Card getBygoneYearDeclinedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getBygoneYear(), getName(), getCvc());
    }

    public static Card getANonExistentYearApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getANonExistentYear(), getName(), getCvc());
    }

    public static Card getANonExistentYearDeclinedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getANonExistentYear(), getName(), getCvc());
    }

    public static Card getEmptyYearFormApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), "", getName(), getCvc());
    }

    public static Card getEmptyYearFormDeclinedCard() {
        return new Card(getDeclinedCardNumber(),getMonth(), "", getName(), getCvc());
    }

    public static Card getFillInTheYearFormWithSymbolsApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), "l$", getName(), getCvc());
    }

    public static Card getFillInTheYearFormWithSymbolsDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), "l$", getName(), getCvc());
    }

    public static Card getIncompleteYearFieldApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getIncompleteYear(), getName(), getCvc());
    }

    public static Card getIncompleteYearFieldDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getIncompleteYear(), getName(), getCvc());
    }

    public static Card getCyrillicCharactersInTheOwnerFieldApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Пушкин", getCvc());
    }

    public static Card getCyrillicCharactersInTheOwnerFieldDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), "Пушкин", getCvc());
    }

    public static Card getANumberInTheOwnerNameApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "36482", getCvc());
    }

    public static Card getANumberInTheOwnerNameDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), "36482", getCvc());
    }

    public static Card getCharactersInFieldOwnerApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "<#%^*&$@>", getCvc());
    }

    public static Card getCharactersInFieldOwnerDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), "<#%^*&$@>", getCvc());
    }

    public static Card getOneCharacterInFieldOwnerApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "G", getCvc());
    }

    public static Card getOneCharacterInFieldOwnerDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), "G", getCvc());
    }

    public static Card getShortNameInOwnerApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Di", getCvc());
    }

    public static Card getShortNameInOwnerDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), "Di", getCvc());
    }

    public static Card getThirtySixCharactersInTheOwnerApprovedCardField() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Gyrfijdkfythnfrjsktyunghfwpokurkigju", getCvc());
    }

    public static Card getThirtySixCharactersInTheOwnerDeclinedCardField() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), "Gyrfijdkfythnfrjsktyunghfwpokurkigju", getCvc());
    }

    public static Card getEmptyOwnerFormApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "", getCvc());
    }

    public static Card getEmptyOwnerFormDeclinedCard() {
        return new Card(getDeclinedCardNumber(),getMonth(), getYear(), "", getCvc());
    }

    public static Card getIncompleteCvcFieldApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getName(), getIncompleteCvc());
    }

    public static Card getIncompleteCvcFieldDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), getName(), getIncompleteCvc());
    }

    public static Card getFillInTheCvcFormWithSymbolsApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(),getYear(), getName(), "Kf&");
    }

    public static Card getFillInTheCvcFormWithSymbolsDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), getName(), "Kf&");
    }

    public static Card getEmptyCvcFormApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getName(), "");
    }

    public static Card getEmptyCvcFormDeclinedCard() {
        return new Card(getDeclinedCardNumber(),getMonth(), getYear(), getName(), "");
    }

}
