import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Library.Library;
import Library.LibraryMenu;
import Library.Book;
import Library.Status;
import Library.BookType;

public class LibraryMenuTest {

    @Test
    public void testLibraryMenuInitialization() {
        LibraryMenu menu = new LibraryMenu();
        assertNotNull(menu);
    }

    @Test
    public void testAddLibraryItem() {
        // Create a new book to simulate user input
        Book newBook = new Book("999", "Test Book", "Test Author", "123456789", "Test Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        Library library = new Library();
        library.addItem(newBook);

        // Verify that the book has been added
        assertTrue(library.getItems().contains(newBook), "The book should be added to the library");
    }
}
