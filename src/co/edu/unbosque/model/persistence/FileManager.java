package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Book;
import co.edu.unbosque.model.Loan;
import co.edu.unbosque.model.User;

import java.io.*;
import java.util.List;

public class FileManager {
    
    private static final String DATA_DIR = StorageDetector.detectStorageLocation();
    
    private final UserFileManager userFileManager;
    private final BookFileManager bookFileManager;
    private final LoanFileManager loanFileManager;
    
    public FileManager() {
        this.userFileManager = new UserFileManager(DATA_DIR);
        this.bookFileManager = new BookFileManager(DATA_DIR);
        this.loanFileManager = new LoanFileManager(DATA_DIR);
        
        System.out.println("\n=== File Manager Configuration ===");
        System.out.println("📁 Data directory: " + DATA_DIR);
        System.out.println("   Absolute path: " + new File(DATA_DIR).getAbsolutePath());
        checkIfDataDirectoryExists();
        displayStorageInfo();
        System.out.println("===================================\n");
    }
    
    private void displayStorageInfo() {
        String storageType = StorageDetector.getStorageType(DATA_DIR);
        
        switch (storageType) {
            case "Google Drive":
                System.out.println("☁️  Google Drive sync enabled");
                System.out.println("⚠️  Wait for sync before closing app");
                break;
            case "Dropbox":
                System.out.println("☁️  Dropbox sync enabled");
                System.out.println("⚠️  Wait for sync before closing app");
                break;
            case "OneDrive":
                System.out.println("☁️  OneDrive sync enabled");
                break;
            default:
                System.out.println("💾 Local storage (no cloud sync)");
                break;
        }
    }

    private void checkIfDataDirectoryExists() {
        File dataDir = new File(DATA_DIR);

        if (!dataDir.exists()) {
            if (dataDir.mkdirs()) {
                System.out.println("Created data directory: " + DATA_DIR);
            } else {
                System.out.println("Warning: Could not create data directory: " + DATA_DIR);
            }
        }
    }

    public List<User> loadUsers() {
        return userFileManager.load();
    }

    public boolean saveUsers(List<User> users) {
        return userFileManager.save(users);
    }

    public List<Book> loadBooks() {
        return bookFileManager.load();
    }

    public boolean saveBooks(List<Book> books) {
        return bookFileManager.save(books);
    }

    public List<Loan> loadLoans() {
        return loanFileManager.load();
    }

    public boolean saveLoans(List<Loan> loans) {
        return loanFileManager.save(loans);
    }

    public boolean checkFiles() {
        boolean usersExist = userFileManager.exists();
        boolean booksExist = bookFileManager.exists();
        boolean loansExist = loanFileManager.exists();
        
        boolean allExist = usersExist && booksExist && loansExist;
        
        if (!allExist) {
            System.out.println("File status:");
            System.out.println("  - users.txt: " + (usersExist ? "exists" : "not found"));
            System.out.println("  - books.txt: " + (booksExist ? "exists" : "not found"));
            System.out.println("  - loans.txt: " + (loansExist ? "exists" : "not found"));
        }
        
        return allExist;
    }

    public void initializeFiles() {
        userFileManager.createIfNotExists();
        bookFileManager.createIfNotExists();
        loanFileManager.createIfNotExists();
    }

    public static String getDelimiter() {
        return UserFileManager.getDelimiter();
    }
}
