package co.edu.unbosque.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {
    private String id;
    private String userId;
    private String bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean active;

    public Loan() {
    }

    public Loan(String id, String userId, String bookId, 
               LocalDate loanDate, LocalDate returnDate, boolean active) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Loan{" +
                "ID='" + id + '\'' +
                ", User ID='" + userId + '\'' +
                ", Book ID='" + bookId + '\'' +
                ", Loan Date=" + loanDate.format(formatter) +
                ", Return Date=" + (returnDate != null ? 
                    returnDate.format(formatter) : "Pending") +
                ", Active=" + (active ? "Yes" : "No") +
                '}';
    }
}
