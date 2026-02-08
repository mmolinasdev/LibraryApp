package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Book;

public class BookFileMapper {
    
    private static final String DELIMITER = "|";

    public static Book fromFileLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] fields = line.split("\\" + DELIMITER);
        
        if (fields.length < 5) {
            return null;
        }
        
        try {
            Book book = new Book();
            
            book.setId(fields[0]);
            book.setTitle(fields[1]);
            book.setAuthor(fields[2]);
            book.setIsbn(fields[3]);
            
            if (fields.length == 5) {
                boolean oldAvailable = Boolean.parseBoolean(fields[4]);
                book.setStock(1);
                book.setAvailableStock(oldAvailable ? 1 : 0);
            } else if (fields.length >= 6) {
                book.setStock(Integer.parseInt(fields[4]));
                book.setAvailableStock(Integer.parseInt(fields[5]));
            }
            
            return book;
            
        } catch (Exception e) {
            System.err.println("Error parsing book line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    public static String toFileLine(Book book) {
        if (book == null) {
            return "";
        }
        
        return book.getId() + DELIMITER + 
               book.getTitle() + DELIMITER + 
               book.getAuthor() + DELIMITER + 
               book.getIsbn() + DELIMITER + 
               book.getStock() + DELIMITER + 
               book.getAvailableStock();
    }
}
