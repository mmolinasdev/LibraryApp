package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookFileManager {
    
    private final String filePath;
    private static final String DELIMITER = "|";
    
    public BookFileManager(String dataDirectory) {
        this.filePath = dataDirectory + "books.txt";
    }
    
    public List<Book> load() {
        List<Book> books = new ArrayList<>();
        File file = new File(filePath);
        
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

    public boolean save(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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
    
    public boolean exists() {
        return new File(filePath).exists();
    }
    
    public boolean createIfNotExists() {
        File file = new File(filePath);
        
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Created file: " + filePath);
                    return true;
                }
            } catch (IOException e) {
                System.out.println("Error creating file " + filePath + ": " + e.getMessage());
            }
        }
        return false;
    }
    
    public static String getDelimiter() {
        return DELIMITER;
    }
}
