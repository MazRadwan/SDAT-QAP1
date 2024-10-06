import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Library.Library;
import Library.Book;
import Library.Status;
import Library.BookType;
import Library.LibraryItem;
import java.util.List;

public class DisplayLibraryItemsTest {
    private Library library;
    private boolean testPassed;

    @BeforeEach
    public void setUp() {
        library = new Library();
        testPassed = false;  // initialize as false, will be set to true only if test passes
    }

    @Test
    public void testDisplayAllLibraryItems() {
        // Get the initial count of items in the library
        int initialCount = library.getItems().size();

        // Create books that will be displayed
        Book book1 = new Book("3001", "First Book", "First Author", "111111111", "First Publisher", 1, Status.AVAILABLE, BookType.PRINTED);
        Book book2 = new Book("3002", "Second Book", "Second Author", "222222222", "Second Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the books to the library
        library.addItem(book1);
        library.addItem(book2);

        // Retrieve all items from the library
        List<LibraryItem> items = library.getItems();

        // Verify that the number of items has increased correctly
        assertEquals(initialCount + 2, items.size(), "The number of items in the library should have increased by 2");

        // Verify that both books are in the library
        assertTrue(items.contains(book1), "The library should contain the first book");
        assertTrue(items.contains(book2), "The library should contain the second book");

        // Mark test as passed
        testPassed = true;
    }

    @AfterEach
    public void tearDown() {
        if (testPassed) {
            // Clean up the test entries from the library
            library.removeItem("3001");
            library.removeItem("3002");
        }
    }
}