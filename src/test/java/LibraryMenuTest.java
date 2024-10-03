import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import Library.LibraryMenu;

public class LibraryMenuTest {

    @Test
    public void testLibraryMenuInitialization() {
        LibraryMenu menu = new LibraryMenu();
        assertNotNull(menu);
    }
}
