package Library;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class LibraryMenu {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println();
            System.out.println("Welcome to the Library Management System");
            System.out.println("1. Add Library Item");
            System.out.println("2. Edit Library Item");
            System.out.println("3. Delete Library Item");
            System.out.println("4. Borrow Library Item");
            System.out.println("5. Return Library Item");
            System.out.println("6. Search Library Items");
            System.out.println("7. Display All Library Items");
            System.out.println("8. Exit");
            System.out.print("Enter your choice (1-8): ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addLibraryItem();
                    break;
                case 2:
                    editLibraryItem();
                    break;
                case 3:
                    deleteLibraryItem();
                    break;
                case 4:
                    borrowLibraryItem();
                    break;
                case 5:
                    returnLibraryItem();
                    break;
                case 6:
                    searchLibraryItems();
                    break;
                case 7:
                    displayAllLibraryItems();
                    break;
                case 8:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        } while (choice != 8);

        scanner.close();
    }

    private static void addLibraryItem() {
        System.out.println("Enter details of the new library item:");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String ISBN = scanner.nextLine();
        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter number of copies: ");
        int numberOfCopies = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        System.out.println("Select item type:");
        System.out.println("1. Book");
        System.out.println("2. Periodical");
        int itemType = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        System.out.println("Select book type:");
        for (BookType bt : BookType.values()) {
            System.out.println(bt.ordinal() + 1 + ". " + bt);
        }
        int bookTypeIndex = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        BookType bookType = BookType.values()[bookTypeIndex - 1];
    
        int newId = getNewId();
        Status status = Status.AVAILABLE; // Set default status to AVAILABLE
        LibraryItem newItem;
        if (itemType == 1) {
            newItem = new Book(String.valueOf(newId), title, author, ISBN, publisher, numberOfCopies, status, bookType);
            library.addItem((Book) newItem);
        } else if (itemType == 2) {
            newItem = new Periodical(String.valueOf(newId), title, author, ISBN, publisher, numberOfCopies, status, bookType);
            library.addItem((Periodical) newItem);
        } else {
            System.out.println("Invalid item type selected.");
            return;
        }
        library.saveItems(); // Ensure the new item is saved to the file
    
        // Success notification with book details in a single line
        System.out.println();
        System.out.println();
        System.out.println("Library item added successfully: \n" + newItem.getTitle() + " by " + newItem.getAuthor() + ", ISBN: " + newItem.getISBN() + ", Publisher: " + newItem.getPublisher() + ", Copies: " + newItem.getNumberOfCopies() + ", Status: " + newItem.getStatus() + ", Type: " + newItem.getBookType());
        System.out.println();
        System.out.println();
    }
    


    private static void editLibraryItem() {
        System.out.println();
        System.out.print("Enter ID of the item to edit: ");
        String itemId = scanner.nextLine();
        LibraryItem item = library.findItemById(itemId);
        if (item != null) {
            System.out.println("Editing " + item.getTitle());
            System.out.print("Enter new title: ");
            item.setTitle(scanner.nextLine());
            System.out.print("Enter new author: ");
            item.setAuthor(scanner.nextLine());
            System.out.print("Enter new ISBN: ");
            item.setISBN(scanner.nextLine());
            System.out.print("Enter new publisher: ");
            item.setPublisher(scanner.nextLine());
            System.out.print("Enter new number of copies: ");
            item.setNumberOfCopies(scanner.nextInt());
            scanner.nextLine();  // Consume newline
            library.saveItems();
            System.out.println();
            System.out.println();
            System.out.println("Item edited successfully.");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void deleteLibraryItem() {
        System.out.println();
        System.out.println();
        System.out.print("Enter ID of the item to delete: ");
        String itemId = scanner.nextLine();
        LibraryItem item = library.findItemById(itemId);
        
        if (item != null) {
            System.out.println(item.getTitle() + "\nAre you sure you want to delete this item? (Y/N): ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("Y")) {
                library.removeItem(itemId);
                library.saveItems();
                System.out.println();
                System.out.println("Item deleted successfully.");
                System.out.println();
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Deletion cancelled.");
                System.out.println();
            }
        } else {
            System.out.println("Item not found.");
        }
    }
    
    private static void borrowLibraryItem() {
        System.out.print("Enter name of the patron: ");
        String patronName = scanner.nextLine();
        System.out.print("Enter ID of the item to borrow: ");
        String itemId = scanner.nextLine();
        LibraryItem item = library.findItemById(itemId);
    
        if (item != null) {
            Patron patron = library.findPatronByName(patronName);
            if (patron != null) {
                if (item.getStatus() == Status.AVAILABLE && item.getNumberOfCopies() > 0) {
                    library.borrowItem(patronName, itemId);
                    System.out.println();
                    System.out.println("Item: "+ item.getTitle() + " " + "borrowed successfully.");
                } else {
                    System.out.println(item.getTitle() + " is not available for borrowing.");
                }
            } else {
                System.out.println();
                System.out.println("Patron not found.");
            }
        } else {
            System.out.println();
            System.out.println("Item not found.");
        }
    }
    

    private static void returnLibraryItem() {
        System.out.print("Enter name of the patron: ");
        String patronName = scanner.nextLine();
        System.out.print("Enter ID of the item to return: ");
        String itemId = scanner.nextLine();
        LibraryItem item = library.findItemById(itemId);
    
        if (item != null) {
            Patron patron = library.findPatronByName(patronName);
            if (patron != null) {
                if (item instanceof Borrowable) {
                    patron.returnItem(item);
                    if (item.getStatus() == Status.CHECKED_OUT) {
                        item.setStatus(Status.AVAILABLE);
                    }
                    item.setNumberOfCopies(item.getNumberOfCopies());
                    library.saveItems();
                    System.out.println("Item returned successfully.");
                } else {
                    System.out.println("This item cannot be returned.");
                }
            } else {
                System.out.println("Patron not found.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }
    
    

    private static void searchLibraryItems() {
        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
    
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();  // Convert to lowercase for case-insensitive search
    
        List<LibraryItem> results = new ArrayList<>();
        switch (searchChoice) {
            case 1:
                for (LibraryItem item : library.getItems()) {
                    if (item.getTitle().toLowerCase().contains(searchTerm)) {
                        results.add(item);
                    }
                }
                break;
            case 2:
                for (LibraryItem item : library.getItems()) {
                    if (item.getAuthor().toLowerCase().contains(searchTerm)) {
                        results.add(item);
                    }
                }
                break;
            case 3:
                for (LibraryItem item : library.getItems()) {
                    if (item.getISBN().toLowerCase().contains(searchTerm)) {
                        results.add(item);
                    }
                }
                break;
            default:
                System.out.println("Invalid search choice.");
                return;
        }
    
        if (results.isEmpty()) {
            System.out.println("No items found.");
        } else {
            System.out.println("Search Results:");
            for (LibraryItem item : results) {
                System.out.println(item.getId() + ": " + item.getTitle() + " by " + item.getAuthor() + " (" + item.getItemType() + "), Status: " + item.getStatus() + ", Book Type: " + item.getBookType());
            }
        }
    }
    

    private static void displayAllLibraryItems() {
        System.out.println();
        System.out.println();
        System.out.println("Library Items:");
        for (LibraryItem item : library.getItems()) {
            System.out.println(item.getId() + ": " + item.getTitle() + " by " + item.getAuthor() + " (" + item.getItemType() + "), Status: " + item.getStatus() + ", Book Type: " + item.getBookType());
        }
        System.out.println(); // First line break
        System.out.println(); // Second line break
    }

    private static int getNewId() {
        int maxId = 0;
        for (LibraryItem item : library.getItems()) {
            int itemId = Integer.parseInt(item.getId());
            if (itemId > maxId) {
                maxId = itemId;
            }
        }
        return maxId + 1;
    }
}
