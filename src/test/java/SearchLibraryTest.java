import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Library.Library;
import Library.Book;
import Library.Status;
import Library.BookType;
import Library.LibraryItem;

public class SearchLibraryTest {
    private Library library;
    private boolean testPassed;

    @BeforeEach
    public void setUp() {
        library = new Library();
        testPassed = false;  // Initialize as false, will be set to true only if test passes
    }

    @Test
    public void testSearchLibraryItemByTitle() {
        // Create a book that will be searched
        Book searchableBook = new Book("2001", "The Great Adventure", "Adventure Author", "987654321", "Adventure Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        library.addItem(searchableBook);

        // Perform the search operation by title
        LibraryItem foundItem = library.searchItemByTitle("The Great Adventure");

        // Verify that the book has been found
        assertNotNull(foundItem, "The book should be found in the library by title");
        assertEquals("The Great Adventure", foundItem.getTitle(), "The found book's title should match the search query");

        // Mark test as passed
        testPassed = true;
    }

    @AfterEach
    public void tearDown() {
        if (testPassed) {
            // Clean up the test entries from the library
            library.removeItem("2001");
        }
    }
}