package Library;

public class Book extends LibraryItem implements Borrowable {
    public Book(String id, String title, String author, String ISBN, String publisher, int numberOfCopies, Status status, BookType bookType) {
        super(id, title, author, ISBN, publisher, numberOfCopies, status, bookType);
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    @Override
    public void borrowItem() {
        if (getNumberOfCopies() > 0) {
            setNumberOfCopies(getNumberOfCopies() - 1);
            if (getNumberOfCopies() == 0) {
                setStatus(Status.CHECKED_OUT);
            }
        } else {
            System.out.println("Item not available for borrowing.");
        }
    }
    @Override
    public void returnItem() {
        setNumberOfCopies(getNumberOfCopies() + 1);
        if (getStatus() == Status.CHECKED_OUT || getStatus() == Status.OVERDUE) {
            setStatus(Status.AVAILABLE);
        }
    }
    
}
