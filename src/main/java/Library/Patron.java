package Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    private String name;
    private String address;
    private String phoneNumber;
    private List<LibraryItem> borrowedItems;

    public Patron(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.borrowedItems = new ArrayList<>();
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<LibraryItem> getBorrowedItems() {
        return borrowedItems;
    }

    public void borrowItem(LibraryItem item) {
        if (item instanceof Borrowable) {
            ((Borrowable) item).borrowItem();
            borrowedItems.add(item);
        } else {
            System.out.println("This item cannot be borrowed.");
        }
    }

    public void returnItem(LibraryItem item) {
        if (item instanceof Borrowable) {
            ((Borrowable) item).returnItem();
            borrowedItems.remove(item);
        } else {
            System.out.println("This item cannot be returned.");
        }
    }

    public static List<Patron> readFromFile(String fileName) throws IOException {
        List<Patron> patrons = new ArrayList<>();
        List<String> lines = FileUtils.readFile(fileName);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                patrons.add(new Patron(parts[0], parts[1], parts[2]));
            }
        }
        return patrons;
    }

    public static void writeToFile(String fileName, List<Patron> patrons) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Patron patron : patrons) {
            lines.add(patron.getName() + "," + patron.getAddress() + "," + patron.getPhoneNumber());
        }
        FileUtils.writeFile(fileName, lines);
    }
}
