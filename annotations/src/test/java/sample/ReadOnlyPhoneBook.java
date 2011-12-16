package sample;

import org.colombbus.annotation.LocalizableClass;
import org.colombbus.annotation.LocalizableMethod;

/**
 *
 */
@SuppressWarnings("nls")
@LocalizableClass(value = "", localize = false)
public class ReadOnlyPhoneBook {

    public ReadOnlyPhoneBook() {
    }

    @LocalizableMethod("ReadOnlyPhoneBook.search")
    public String search(String personName) {
        System.out.println("Search person " + personName);
        return "0145247000";
    }

}
