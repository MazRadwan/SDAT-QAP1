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

    @Test
    public void testSearchLibraryItemByAuthor() {
        // Create a book that will be searched by author
        Book searchableBook = new Book("2002", "The Mystery Novel", "Mystery Author", "123456789", "Mystery Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        library.addItem(searchableBook);

        // Perform the search operation by author
        LibraryItem foundItem = library.searchItemByAuthor("Mystery Author");

        // Verify that the book has been found
        assertNotNull(foundItem, "The book should be found in the library by author");
        assertEquals("Mystery Author", foundItem.getAuthor(), "The found book's author should match the search query");

        // Mark test as passed
        testPassed = true;
    }

    @Test
    public void testSearchLibraryItemByISBN() {
        // Create a book that will be searched by ISBN
        Book searchableBook = new Book("2003", "The Science Book", "Science Author", "555555555", "Science Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        library.addItem(searchableBook);

        // Perform the search operation by ISBN
        LibraryItem foundItem = library.searchItemByISBN("555555555");

        // Verify that the book has been found
        assertNotNull(foundItem, "The book should be found in the library by ISBN");
        assertEquals("555555555", foundItem.getISBN(), "The found book's ISBN should match the search query");

        // Mark test as passed
        testPassed = true;
    }

    @AfterEach
    public void tearDown() {
        if (testPassed) {
            // Clean up the test entries from the library
            library.removeItem("2001");
            library.removeItem("2002");
            library.removeItem("2003");
        }
    }
}