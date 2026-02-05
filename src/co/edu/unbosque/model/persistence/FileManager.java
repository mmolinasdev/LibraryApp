package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Book;
import co.edu.unbosque.model.Loan;
import co.edu.unbosque.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    
    private static final String DATA_DIR = getDataDirectory();
    private static final String USERS_FILE = DATA_DIR + "users.txt";
    private static final String BOOKS_FILE = DATA_DIR + "books.txt";
    private static final String LOANS_FILE = DATA_DIR + "loans.txt";
    private static final String DELIMITER = "|";
    
    public FileManager() {
        System.out.println("\n=== File Manager Configuration ===");
        System.out.println("📁 Data directory: " + DATA_DIR);
        System.out.println("   Absolute path: " + new File(DATA_DIR).getAbsolutePath());
        checkIfDataDirectoryExists();
        checkCloudStorage();
        System.out.println("===================================\n");
    }
    
    private static String getDataDirectory() {
        String envPath = System.getenv("LIBRARY_DATA_PATH");
        
        if (envPath != null && !envPath.isEmpty()) {
            return ensureTrailingSeparator(envPath);
        }
        
        String googleDrivePath = detectGoogleDrive();
        if (googleDrivePath != null) {
            return googleDrivePath;
        }
        
        String dropboxPath = detectDropbox();
        if (dropboxPath != null) {
            return dropboxPath;
        }
        
        return "data" + File.separator;
    }
    
    private static String detectGoogleDrive() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        
        if (os.contains("mac")) {
            File cloudStorage = new File(userHome + "/Library/CloudStorage");

            if (cloudStorage.exists()) {
                File[] dirs = cloudStorage.listFiles((dir, name) -> name.startsWith("GoogleDrive-"));

                if (dirs != null && dirs.length > 0) {
                    String[] possiblePaths = {
                        dirs[0].getAbsolutePath() + "/Mi unidad/BD_1/LibraryManagementApp/data/",
                        dirs[0].getAbsolutePath() + "/My Drive/BD_1/LibraryManagementApp/data/",
                        dirs[0].getAbsolutePath() + "/Mi unidad/LibraryManagementApp/data/",
                        dirs[0].getAbsolutePath() + "/My Drive/LibraryManagementApp/data/"
                    };
                    
                    for (String path : possiblePaths) {
                        if (new File(path).exists()) {
                            return path;
                        }
                    }
                }
            }
        } else if (os.contains("win")) {
            String[] basePaths = {
                userHome + "\\\\Google Drive",
                userHome + "\\\\GoogleDrive",
                "G:\\\\My Drive",
                "G:\\\\Mi unidad"
            };
            
            String[] subPaths = {
                "\\\\BD_1\\\\LibraryManagementApp\\\\data\\\\",
                "\\\\LibraryManagementApp\\\\data\\\\"
            };
            
            for (String basePath : basePaths) {
                for (String subPath : subPaths) {
                    String fullPath = basePath + subPath;
                    
                    if (new File(fullPath).exists()) {
                        return fullPath;
                    }
                }
            }
            
            File userHomeDir = new File(userHome);
            File[] userDirs = userHomeDir.listFiles();

            if (userDirs != null) {
                for (File dir : userDirs) {
                    if (dir.isDirectory() && (dir.getName().contains("Google") || dir.getName().contains("Drive"))) {
                        for (String subPath : subPaths) {
                            String fullPath = dir.getAbsolutePath() + subPath;
                            if (new File(fullPath).exists()) {
                                return fullPath;
                            }
                        }
                    }
                }
            }
        }
        
        return null;
    }
    
    private static String detectDropbox() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String path = null;
        
        if (os.contains("mac") || os.contains("linux")) {
            path = userHome + "/Dropbox/LibraryManagementApp/data/";
        } else if (os.contains("win")) {
            path = userHome + "\\Dropbox\\LibraryManagementApp\\data\\";
        }
        
        if (path != null && new File(path).exists()) {
            return path;
        }
        return null;
    }
    
    private static String ensureTrailingSeparator(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        
        String separator = File.separator;
        return path.endsWith(separator) ? path : path + separator;
    }
    
    private void checkCloudStorage() {
        String absolutePath = new File(DATA_DIR).getAbsolutePath();
        
        if (absolutePath.contains("GoogleDrive") || absolutePath.contains("Google Drive")) {
            System.out.println("☁️  Google Drive sync enabled");
            System.out.println("⚠️  Wait for sync before closing app");
        } else if (absolutePath.contains("Dropbox")) {
            System.out.println("☁️  Dropbox sync enabled");
            System.out.println("⚠️  Wait for sync before closing app");
        } else if (absolutePath.contains("OneDrive")) {
            System.out.println("☁️  OneDrive sync enabled");
        } else {
            System.out.println("💾 Local storage (no cloud sync)");
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
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            System.out.println("Users file not found. Starting with empty user list.");
            return users;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                User user = UserFileMapper.fromFileLine(line);

                if (user != null) {
                    users.add(user);
                } else {
                    System.out.println("Warning: Invalid user data at line " + lineNumber + ": " + line);
                }
            }
            
            System.out.println("Loaded " + users.size() + " users from file.");
            
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        
        return users;
    }

    public boolean saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                bw.write(UserFileMapper.toFileLine(user));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
            return false;
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        
        if (!file.exists()) {
            System.out.println("Books file not found. Starting with empty book list.");
            return books;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                Book book = BookFileMapper.fromFileLine(line);
                if (book != null) {
                    books.add(book);
                } else {
                    System.out.println("Warning: Invalid book data at line " + lineNumber + ": " + line);
                }
            }
            
            System.out.println("Loaded " + books.size() + " books from file.");
            
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        
        return books;
    }

    public boolean saveBooks(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                bw.write(BookFileMapper.toFileLine(book));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
            return false;
        }
    }

    public List<Loan> loadLoans() {
        List<Loan> loans = new ArrayList<>();
        File file = new File(LOANS_FILE);
        
        if (!file.exists()) {
            System.out.println("Loans file not found. Starting with empty loan list.");
            return loans;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                Loan loan = LoanFileMapper.fromFileLine(line);
                if (loan != null) {
                    loans.add(loan);
                } else {
                    System.out.println("Warning: Invalid loan data at line " + lineNumber + ": " + line);
                }
            }
            
            System.out.println("Loaded " + loans.size() + " loans from file.");
            
        } catch (IOException e) {
            System.out.println("Error loading loans: " + e.getMessage());
        }
        
        return loans;
    }

    public boolean saveLoans(List<Loan> loans) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOANS_FILE))) {
            for (Loan loan : loans) {
                bw.write(LoanFileMapper.toFileLine(loan));
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error saving loans: " + e.getMessage());
            return false;
        }
    }

    public boolean checkFiles() {
        File usersFile = new File(USERS_FILE);
        File booksFile = new File(BOOKS_FILE);
        File loansFile = new File(LOANS_FILE);
        
        boolean allExist = usersFile.exists() && booksFile.exists() && loansFile.exists();
        
        if (!allExist) {
            System.out.println("File status:");
            System.out.println("  - users.txt: " + (usersFile.exists() ? "exists" : "not found"));
            System.out.println("  - books.txt: " + (booksFile.exists() ? "exists" : "not found"));
            System.out.println("  - loans.txt: " + (loansFile.exists() ? "exists" : "not found"));
        }
        
        return allExist;
    }

    public void initializeFiles() {
        createFileIfNotExists(USERS_FILE);
        createFileIfNotExists(BOOKS_FILE);
        createFileIfNotExists(LOANS_FILE);
    }

    private void createFileIfNotExists(String filename) {
        File file = new File(filename);
        
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Created file: " + filename);
                }
            } catch (IOException e) {
                System.out.println("Error creating file " + filename + ": " + e.getMessage());
            }
        }
    }

    public static String getDelimiter() {
        return DELIMITER;
    }
}
