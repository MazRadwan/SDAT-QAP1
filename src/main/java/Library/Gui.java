package Library;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {
    private Library library;
    // Fields for adding items
    private JTextField addTitleField, addAuthorField, addIsbnField, addPublisherField, addCopiesField;
    private JComboBox<String> addItemTypeCombo;
    private JComboBox<BookType> addBookTypeCombo;
    // Fields for editing items
    private JTextField editTitleField, editAuthorField, editIsbnField, editPublisherField, editCopiesField, editIdField;
    private JComboBox<String> editItemTypeCombo;
    private JComboBox<BookType> editBookTypeCombo;
    // Fields for deleting items
    private JTextField deleteIdField;
    // Fields for borrowing items
    private JTextField transactionPatronField, transactionItemIdField;
    private JButton addButton, editButton, deleteButton, borrowButton, returnButton;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    //fields for search
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JButton searchButton;
    private JTextArea searchResultsArea;

    public Gui() {
        library = new Library();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createMenuBar();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createAddItemPanel(), "ADD_ITEM");
        cardPanel.add(createDisplayAllItemsPanel(), "DISPLAY_ALL");
        cardPanel.add(createEditItemPanel(), "EDIT_ITEM");
        cardPanel.add(createDeleteItemPanel(), "DELETE_ITEM");
        cardPanel.add(createTransactionPanel(), "TRANSACTION");
        cardPanel.add(createSearchPanel(), "SEARCH");

        add(cardPanel, BorderLayout.CENTER);
    }


 
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
    
        JMenuItem addItemMenuItem = new JMenuItem("Add Item");
        addItemMenuItem.addActionListener(e -> cardLayout.show(cardPanel, "ADD_ITEM"));
    
        JMenuItem displayAllMenuItem = new JMenuItem("Display All Items");
        displayAllMenuItem.addActionListener(e -> {
            cardLayout.show(cardPanel, "DISPLAY_ALL");
            displayAllItems();
        });
    
        JMenuItem editItemMenuItem = new JMenuItem("Edit Item");
        editItemMenuItem.addActionListener(e -> cardLayout.show(cardPanel, "EDIT_ITEM"));
    
        JMenuItem deleteItemMenuItem = new JMenuItem("Delete Item");
        deleteItemMenuItem.addActionListener(e -> cardLayout.show(cardPanel, "DELETE_ITEM"));
    
        JMenuItem transactionMenuItem = new JMenuItem("Borrow/Return Item");
        transactionMenuItem.addActionListener(e -> cardLayout.show(cardPanel, "TRANSACTION"));

        JMenuItem searchMenuItem = new JMenuItem("Search Items");
        searchMenuItem.addActionListener(e -> cardLayout.show(cardPanel, "SEARCH"));

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> exitApplication());
    
        menu.add(searchMenuItem);
        menu.add(addItemMenuItem);
        menu.add(displayAllMenuItem);
        menu.add(editItemMenuItem);
        menu.add(deleteItemMenuItem);
        menu.add(transactionMenuItem);
        menu.addSeparator(); // Adds a separator line before the Exit option
        menu.add(exitMenuItem);
    
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

        private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Search Items"));

        JPanel searchControls = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchTypeCombo = new JComboBox<>(new String[]{"Title", "Author", "ISBN"});
        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());

        searchControls.add(new JLabel("Search by:"));
        searchControls.add(searchTypeCombo);
        searchControls.add(searchField);
        searchControls.add(searchButton);

        searchResultsArea = new JTextArea(20, 40);
        searchResultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultsArea);

        panel.add(searchControls, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void exitApplication() {
        int confirm = JOptionPane.showOptionDialog(
            this,
            "Are you sure you want to exit the application?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            null);

        if (confirm == JOptionPane.YES_OPTION) {
            // Perform any necessary cleanup here
            System.exit(0);
        }
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        String searchType = (String) searchTypeCombo.getSelectedItem();
        List<LibraryItem> results = new ArrayList<>();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.", "Search Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (LibraryItem item : library.getItems()) {
            switch (searchType) {
                case "Title":
                    if (item.getTitle().toLowerCase().contains(searchTerm)) {
                        results.add(item);
                    }
                    break;
                case "Author":
                    if (item.getAuthor().toLowerCase().contains(searchTerm)) {
                        results.add(item);
                    }
                    break;
                case "ISBN":
                    if (item.getISBN().toLowerCase().contains(searchTerm)) {
                        results.add(item);
                    }
                    break;
            }
        }

        displaySearchResults(results, searchTerm, searchType);
    }

    private void displaySearchResults(List<LibraryItem> results, String searchTerm, String searchType) {
        searchResultsArea.setText(""); // Clear previous results
        searchResultsArea.append("Search Results for " + searchType + ": \"" + searchTerm + "\"\n\n");
        
        if (results.isEmpty()) {
            searchResultsArea.append("No items found.");
        } else {
            for (LibraryItem item : results) {
                searchResultsArea.append(String.format("ID: %s\nTitle: %s\nAuthor: %s\nISBN: %s\nStatus: %s\n\n",
                        item.getId(), item.getTitle(), item.getAuthor(), item.getISBN(), item.getStatus()));
            }
            searchResultsArea.append("Total results: " + results.size());
        }
    }
    
    private JPanel createAddItemPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add New Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addTitleField = new JTextField(20), gbc);

        // Author
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addAuthorField = new JTextField(20), gbc);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addIsbnField = new JTextField(20), gbc);

        // Publisher
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Publisher:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addPublisherField = new JTextField(20), gbc);

        // Number of Copies
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Number of Copies:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addCopiesField = new JTextField(5), gbc);

        // Item Type
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Item Type:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addItemTypeCombo = new JComboBox<>(new String[]{"Book", "Periodical"}), gbc);

        // Book Type
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Book Type:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(addBookTypeCombo = new JComboBox<>(BookType.values()), gbc);

        // Add Item Button
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        addButton = new JButton("Add Item");
        addButton.addActionListener(e -> addItem());
        panel.add(addButton, gbc);

        return panel;
    }

    private JPanel createDisplayAllItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        itemTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> displayAllItems());
        panel.add(refreshButton, BorderLayout.SOUTH);

        return panel;
    }
    private JPanel createEditItemPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Edit Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // ID Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Item ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editIdField = new JTextField(10), gbc);

        // Title
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editTitleField = new JTextField(20), gbc);

        // Author
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editAuthorField = new JTextField(20), gbc);

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editIsbnField = new JTextField(20), gbc);

        // Publisher
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Publisher:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editPublisherField = new JTextField(20), gbc);

        // Number of Copies
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Number of Copies:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editCopiesField = new JTextField(5), gbc);

        // Item Type
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Item Type:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editItemTypeCombo = new JComboBox<>(new String[]{"Book", "Periodical"}), gbc);

        // Book Type
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Book Type:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(editBookTypeCombo = new JComboBox<>(BookType.values()), gbc);

        // Load and Edit buttons
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        JButton loadButton = new JButton("Load Item");
        loadButton.addActionListener(e -> loadItemForEditing());
        panel.add(loadButton, gbc);

        gbc.gridx = 1;
        editButton = new JButton("Save Changes");
        editButton.addActionListener(e -> editItem());
        panel.add(editButton, gbc);

        return panel;
    }

    private void loadItemForEditing() {
        String itemId = editIdField.getText().trim();
        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an item ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryItem item = library.findItemById(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        editTitleField.setText(item.getTitle());
        editAuthorField.setText(item.getAuthor());
        editIsbnField.setText(item.getISBN());
        editPublisherField.setText(item.getPublisher());
        editCopiesField.setText(String.valueOf(item.getNumberOfCopies()));
        editItemTypeCombo.setSelectedItem(item instanceof Book ? "Book" : "Periodical");
        editBookTypeCombo.setSelectedItem(item.getBookType());
    }

    private void editItem() {
        String itemId = editIdField.getText().trim();
        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an item ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryItem item = library.findItemById(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            item.setTitle(editTitleField.getText().trim());
            item.setAuthor(editAuthorField.getText().trim());
            item.setISBN(editIsbnField.getText().trim());
            item.setPublisher(editPublisherField.getText().trim());
            item.setNumberOfCopies(Integer.parseInt(editCopiesField.getText().trim()));
            item.setBookType((BookType) editBookTypeCombo.getSelectedItem());

            library.saveItems();
            JOptionPane.showMessageDialog(this, "Item updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearEditFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number of copies.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addItem() {
        try {
            String title = addTitleField.getText().trim();
            String author = addAuthorField.getText().trim();
            String ISBN = addIsbnField.getText().trim();
            String publisher = addPublisherField.getText().trim();
            String copiesStr = addCopiesField.getText().trim();
            String itemType = (String) addItemTypeCombo.getSelectedItem();
            BookType bookType = (BookType) addBookTypeCombo.getSelectedItem();

            if (title.isEmpty() || author.isEmpty() || ISBN.isEmpty() || publisher.isEmpty() || copiesStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numberOfCopies = Integer.parseInt(copiesStr);
            if (numberOfCopies < 0) {
                throw new NumberFormatException();
            }

            int newId = library.getNewId();
            Status status = Status.AVAILABLE;

            LibraryItem newItem;
            if ("Book".equals(itemType)) {
                newItem = new Book(String.valueOf(newId), title, author, ISBN, publisher, numberOfCopies, status, bookType);
            } else if ("Periodical".equals(itemType)) {
                newItem = new Periodical(String.valueOf(newId), title, author, ISBN, publisher, numberOfCopies, status, bookType);
            } else {
                throw new IllegalArgumentException("Invalid item type selected.");
            }

            library.addItem(newItem);
            library.saveItems();

            JOptionPane.showMessageDialog(this, "Item added successfully with ID: " + newId, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearAddFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number of copies. Please enter a non-negative integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void displayAllItems() {
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"ID", "Title", "Author", "ISBN", "Publisher", "Copies", "Status", "Type"});

        List<LibraryItem> items = library.getItems();

        for (LibraryItem item : items) {
            Object[] row = new Object[]{
                item.getId(),
                item.getTitle(),
                item.getAuthor(),
                item.getISBN(),
                item.getPublisher(),
                item.getNumberOfCopies(),
                item.getStatus(),
                item.getItemType()
            };
            tableModel.addRow(row);
        }
    }

        private JPanel createDeleteItemPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Delete Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Item ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(deleteIdField = new JTextField(10), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(e -> deleteItem());
        panel.add(deleteButton, gbc);

        return panel;
    }

    private void deleteItem() {
        String itemId = deleteIdField.getText().trim();
        if (itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an item ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryItem item = library.findItemById(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this item?\n" + item.getTitle() + " by " + item.getAuthor(), 
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            library.removeItem(itemId);
            library.saveItems();
            JOptionPane.showMessageDialog(this, "Item deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            deleteIdField.setText("");
        }
    }

    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Borrow/Return Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Patron Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(transactionPatronField = new JTextField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Item ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(transactionItemIdField = new JTextField(10), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        borrowButton = new JButton("Borrow Item");
        borrowButton.addActionListener(e -> borrowItem());
        panel.add(borrowButton, gbc);

        gbc.gridx = 1;
        returnButton = new JButton("Return Item");
        returnButton.addActionListener(e -> returnItem());
        panel.add(returnButton, gbc);

        return panel;
    }

    private void borrowItem() {
        String patronName = transactionPatronField.getText().trim();
        String itemId = transactionItemIdField.getText().trim();

        if (patronName.isEmpty() || itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both patron name and item ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patron patron = library.findPatronByName(patronName);
        if (patron == null) {
            JOptionPane.showMessageDialog(this, "Patron not found. Please check the name and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryItem item = library.findItemById(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found. Please check the ID and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (item.getStatus() != Status.AVAILABLE) {
            JOptionPane.showMessageDialog(this, "Item is not available for borrowing.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            library.borrowItem(patronName, itemId);
            library.saveItems();
            library.savePatrons();
            JOptionPane.showMessageDialog(this, "Item borrowed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearTransactionFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error borrowing item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnItem() {
        String patronName = transactionPatronField.getText().trim();
        String itemId = transactionItemIdField.getText().trim();

        if (patronName.isEmpty() || itemId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both patron name and item ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patron patron = library.findPatronByName(patronName);
        if (patron == null) {
            JOptionPane.showMessageDialog(this, "Patron not found. Please check the name and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryItem item = library.findItemById(itemId);
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found. Please check the ID and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (item.getStatus() != Status.CHECKED_OUT) {
            JOptionPane.showMessageDialog(this, "This item is not checked out and cannot be returned.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            library.returnItem(patronName, itemId);
            library.saveItems();
            library.savePatrons();
            JOptionPane.showMessageDialog(this, "Item returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearTransactionFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error returning item: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTransactionFields() {
        transactionPatronField.setText("");
        transactionItemIdField.setText("");
    }

    private void clearAddFields() {
        addTitleField.setText("");
        addAuthorField.setText("");
        addIsbnField.setText("");
        addPublisherField.setText("");
        addCopiesField.setText("");
        addItemTypeCombo.setSelectedIndex(0);
        addBookTypeCombo.setSelectedIndex(0);
    }

    private void clearEditFields() {
        editIdField.setText("");
        editTitleField.setText("");
        editAuthorField.setText("");
        editIsbnField.setText("");
        editPublisherField.setText("");
        editCopiesField.setText("");
        editItemTypeCombo.setSelectedIndex(0);
        editBookTypeCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }
}