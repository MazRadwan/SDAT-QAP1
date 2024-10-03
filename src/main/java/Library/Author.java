package Library;
import java.util.ArrayList;
import java.util.List;

public class Author {
    private String name;
    private String dateOfBirth;
    private List<LibraryItem> writtenItems;

    public Author(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.writtenItems = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<LibraryItem> getWrittenItems() {
        return writtenItems;
    }

    public void setWrittenItems(List<LibraryItem> writtenItems) {
        this.writtenItems = writtenItems;
    }

    public void addWrittenItem(LibraryItem item) {
        writtenItems.add(item);
    }
}
