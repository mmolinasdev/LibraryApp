package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Loan;

import java.time.LocalDate;

public class LoanFileMapper {
    
    private static final String DELIMITER = "|";

    public static Loan fromFileLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] fields = line.split("\\" + DELIMITER);
        
        if (fields.length != 6) {
            return null;
        }
        
        try {
            Loan loan = new Loan();
            
            loan.setId(fields[0]);
            loan.setUserId(fields[1]);
            loan.setBookId(fields[2]);
            
            LocalDate loanDate = LocalDate.parse(fields[3]);
            loan.setLoanDate(loanDate);
            
            LocalDate returnDate = fields[4].equals("null") || fields[4].isEmpty() ? 
                                  null : LocalDate.parse(fields[4]);
            loan.setReturnDate(returnDate);
            
            loan.setActive(Boolean.parseBoolean(fields[5]));
            
            return loan;
            
        } catch (Exception e) {
            System.err.println("Error parsing loan line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    public static String toFileLine(Loan loan) {
        if (loan == null) {
            return "";
        }
        
        String loanDateStr = loan.getLoanDate() != null ? loan.getLoanDate().toString() : "";
        String returnDateStr = loan.getReturnDate() != null ? loan.getReturnDate().toString() : "null";
        
        return loan.getId() + DELIMITER + 
               loan.getUserId() + DELIMITER + 
               loan.getBookId() + DELIMITER + 
               loanDateStr + DELIMITER + 
               returnDateStr + DELIMITER + 
               loan.isActive();
    }
}
