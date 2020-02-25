package pl.dawidraszka.bookon.tools;

import org.apache.commons.validator.routines.ISBNValidator;

public final class DataVerifier {

    private static ISBNValidator isbnValidator = ISBNValidator.getInstance();

    public static boolean isTitleCorrect(String title) {
        return title.length() >= 5;
    }

    public static boolean isIsbnCorrect(String isbn) {
        return isbnValidator.isValid(isbn);
    }
}
