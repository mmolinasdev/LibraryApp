package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Loan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoanFileManager {
    
    private final String filePath;
    private static final String DELIMITER = "|";
    
    public LoanFileManager(String dataDirectory) {
        this.filePath = dataDirectory + "loans.txt";
    }
    
    public List<Loan> load() {
        List<Loan> loans = new ArrayList<>();
        File file = new File(filePath);
        
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

    public boolean save(List<Loan> loans) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
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
