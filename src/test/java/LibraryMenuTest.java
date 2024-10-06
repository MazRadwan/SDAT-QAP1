import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Library.Library;
import Library.LibraryMenu;
import Library.Book;
import Library.Status;
import Library.BookType;
import Library.Patron;

public class LibraryMenuTest {
    private Library library;
    private Patron testPatron;
    private boolean testPassed;

    @BeforeEach
    public void setUp() {
        library = new Library();
        testPatron = new Patron("Test Patron", "123 Test St", "123-456-7890");
        library.addPatron(testPatron);
        testPassed = false;  // Initialize as false, will be set to true only if test passes
    }

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
        library.addItem(newBook);

        // Verify that the book has been added
        assertTrue(library.getItems().contains(newBook), "The book should be added to the library");


        testPassed = true;
    }

    @Test
    public void testReturnLibraryItem() {
        // Create a book that will be borrowed and returned
        Book borrowedBook = new Book("9999", "Borrowed Book", "Test Author", "987654321", "Test Publisher", 1, Status.CHECKED_OUT, BookType.PRINTED);

        // Add the borrowed book to the library
        library.addItem(borrowedBook);

        // Perform the return operation
        library.returnItem(testPatron.getName(), "9999");

        // Check that the book is now marked as AVAILABLE
        assertEquals(Status.AVAILABLE, borrowedBook.getStatus(), "The book should be available after being returned.");


        testPassed = true;
    }

    @Test
    public void testBorrowLibraryItem() {
        // Create a book that will be borrowed
        Book availableBook = new Book("1000", "Borrowable Book", "Test Author", "987654321", "Test Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        library.addItem(availableBook);

        // Perform the borrow operation
        library.borrowItem(testPatron.getName(), "1000");

        // Check that the book is now marked as CHECKED_OUT
        assertEquals(Status.CHECKED_OUT, availableBook.getStatus(), "The book should be checked out after being borrowed.");

        // Check that the patron's list of borrowed items includes the book
        assertTrue(testPatron.getBorrowedItems().contains(availableBook), "The patron should have the borrowed book in their list of borrowed items.");


        testPassed = true;
    }

    @Test
    public void testRemoveLibraryItem() {
        // Create a book that will be added and then removed
        Book removableBook = new Book("1001", "Removable Book", "Test Author", "987654322", "Test Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        library.addItem(removableBook);

        // Verify that the book has been added
        assertTrue(library.getItems().contains(removableBook), "The book should be added to the library");

        // Remove the book from the library
        library.removeItem("1001");

        // Verify that the book has been removed
        assertFalse(library.getItems().contains(removableBook), "The book should be removed from the library");


        testPassed = true;
    }

    @Test
    public void testEditLibraryItem() {
        // Create a book that will be added and then edited
        Book editableBook = new Book("1002", "Original Title", "Original Author", "987654323", "Original Publisher", 1, Status.AVAILABLE, BookType.PRINTED);

        // Add the book to the library
        library.addItem(editableBook);

        // Edit the book's details
        editableBook.setTitle("Edited Title");
        editableBook.setAuthor("Edited Author");
        editableBook.setPublisher("Edited Publisher");
        editableBook.setISBN("111111111");
        editableBook.setNumberOfCopies(2);

        // Save the changes to the library
        library.saveItems();

        // Retrieve the updated book from the library
        Book updatedBook = (Book) library.findItemById("1002");

        // Verify that the book's details have been updated
        assertEquals("Edited Title", updatedBook.getTitle(), "The book's title should be updated.");
        assertEquals("Edited Author", updatedBook.getAuthor(), "The book's author should be updated.");
        assertEquals("Edited Publisher", updatedBook.getPublisher(), "The book's publisher should be updated.");
        assertEquals("111111111", updatedBook.getISBN(), "The book's ISBN should be updated.");
        assertEquals(2, updatedBook.getNumberOfCopies(), "The book's number of copies should be updated.");


        testPassed = true;
    }

    @AfterEach
    public void tearDown() {
        if (testPassed) {
            // Clean up the test entries from the library
            library.removeItem("999");
            library.removeItem("9999");
            library.removeItem("1000");
            library.removeItem("1001");
            library.removeItem("1002");
            library.removePatron(testPatron.getName());
        }
    }
}