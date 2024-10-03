package Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<LibraryItem> items;
    private List<Patron> patrons;

    public Library() {
        try {
            items = LibraryItem.readFromFile("src/main/java/Library/libraryItems.txt");
            patrons = Patron.readFromFile("src/main/java/Library/patron.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getNewId() {
        int maxId = 0;
        for (LibraryItem item : items) {
            try {
                int itemId = Integer.parseInt(item.getId());
                if (itemId > maxId) {
                    maxId = itemId;
                }
            } catch (NumberFormatException e) {
                // Skip items with non-numeric IDs
            }
        }
        return maxId + 1;
    }


    public List<LibraryItem> getItems() {
        return items;
    }

    public List<Patron> getPatrons() {
        return patrons;
    }

    public void addItem(LibraryItem item) {
        items.add(item);
        saveItems();
    }

    public void removeItem(String itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
        saveItems();
    }

    public void addPatron(Patron patron) {
        patrons.add(patron);
        savePatrons();
    }

    public void removePatron(String name) {
        patrons.removeIf(patron -> patron.getName().equals(name));
        savePatrons();
    }

    public void borrowItem(String patronName, String itemId) {
        Patron patron = findPatronByName(patronName);
        LibraryItem item = findItemById(itemId);
        if (patron != null && item != null && item instanceof Borrowable) {
            patron.borrowItem(item);
            saveItems();
        } else {
            System.out.println("Item or Patron not found, or Item cannot be borrowed.");
        }
    }

    public void returnItem(String patronName, String itemId) {
        Patron patron = findPatronByName(patronName);
        LibraryItem item = findItemById(itemId);
        if (patron != null && item != null && item instanceof Borrowable) {
            patron.returnItem(item);
            saveItems();
        } else {
            System.out.println("Item or Patron not found, or Item cannot be returned.");
        }
    }

    public Patron findPatronByName(String name) {
        for (Patron patron : patrons) {
            if (patron.getName().equals(name)) {
                return patron;
            }
        }
        return null;
    }

    public LibraryItem findItemById(String id) {
        for (LibraryItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void saveItems() {
        try {
            LibraryItem.writeToFile("src/main/java/Library/libraryItems.txt", items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePatrons() {
        try {
            Patron.writeToFile("src/main/java/Library/patron.txt", patrons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LibraryItem> searchItemsByTitle(String title) {
        List<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<LibraryItem> searchItemsByAuthor(String author) {
        List<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getAuthor().equalsIgnoreCase(author)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<LibraryItem> searchItemsByISBN(String ISBN) {
        List<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getISBN().equalsIgnoreCase(ISBN)) {
                results.add(item);
            }
        }
        return results;
    }
}
