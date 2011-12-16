package sample;

import sample.francais.MonAnnuaire;
import sample.english.MyPhoneBook;

public class PhoneBookRun {

	public static final void main(String[] args) {
		PhoneBook book = new PhoneBook();
		book.add("bob", "45247000");
		book.search("bob");
		book.remove("bob");
		
		MonAnnuaire annuaire = new MonAnnuaire();
		annuaire.ajouterUnePersonne("Robert", "45247000");
		annuaire.rechercherUnePersonne("Robert");
		annuaire.supprimerUnePersone("Robert");
		
		MyPhoneBook myBook = new MyPhoneBook();
		myBook.addAPerson("Bobby", "45247000");
		myBook.searchAPerson("Bobby");
		myBook.removeAPerson("Bobby");
	}

}
