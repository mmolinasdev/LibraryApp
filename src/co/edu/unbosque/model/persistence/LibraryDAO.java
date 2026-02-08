package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Book;
import co.edu.unbosque.model.Loan;
import co.edu.unbosque.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryDAO {
    private final FileManager fileManager;
    private List<User> users;
    private List<Book> books;
    private List<Loan> loans;

    public LibraryDAO() {
        this.fileManager = new FileManager();
        users = new ArrayList<>();
        books = new ArrayList<>();
        loans = new ArrayList<>();
        loadData();
    }

    public boolean createUser(User user) {
        if (findUserById(user.getId()) != null) {
            return false;
        }
        users.add(user);
        saveUsers();
        return true;
    }

    public boolean updateUser(User user) {
        User existingUser = findUserById(user.getId());
        if (existingUser == null) {
            return false;
        }
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setActive(user.isActive());
        saveUsers();
        return true;
    }

    public boolean deleteUser(String id) {
        User user = findUserById(id);
        if (user == null) {
            return false;
        }
        
        boolean hasActiveLoans = loans.stream()
            .anyMatch(l -> l.getUserId().equals(id) && l.isActive());
        
        if (hasActiveLoans) {
            return false;
        }
        
        users.remove(user);
        saveUsers();
        return true;
    }

    public User findUserById(String id) {
        return users.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<User> findUsersByName(String name) {
        return users.stream()
            .filter(u -> u.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean createBook(Book book) {
        if (findBookById(book.getId()) != null) {
            return false;
        }
        books.add(book);
        saveBooks();
        return true;
    }

    public boolean updateBook(Book book) {
        Book existingBook = findBookById(book.getId());
        if (existingBook == null) {
            return false;
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setStock(book.getStock());
        existingBook.setAvailableStock(book.getAvailableStock());
        saveBooks();
        return true;
    }

    public boolean deleteBook(String id) {
        Book book = findBookById(id);
        if (book == null) {
            return false;
        }
        
        boolean isLoaned = loans.stream()
            .anyMatch(l -> l.getBookId().equals(id) && l.isActive());
        
        if (isLoaned) {
            return false;
        }
        
        books.remove(book);
        saveBooks();
        return true;
    }

    public Book findBookById(String id) {
        return books.stream()
            .filter(b -> b.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<Book> findBooksByTitle(String title) {
        return books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public String createLoan(String userId, String bookId) {
        User user = findUserById(userId);
        if (user == null) {
            return null;
        }

        Book book = findBookById(bookId);
        if (book == null || book.getAvailableStock() <= 0) {
            return null;
        }

        String loanId = "L" + System.currentTimeMillis();
        Loan loan = new Loan(loanId, userId, bookId, LocalDate.now(), null, true);
        loans.add(loan);
        book.setAvailableStock(book.getAvailableStock() - 1);
        
        saveLoans();
        saveBooks();
        return loanId;
    }

    public boolean registerReturn(String loanId) {
        Loan loan = findLoanById(loanId);

        if (loan == null || !loan.isActive()) {
            return false;
        }

        loan.setReturnDate(LocalDate.now());
        loan.setActive(false);

        Book book = findBookById(loan.getBookId());

        if (book != null) {
            book.setAvailableStock(book.getAvailableStock() + 1);
        }

        saveLoans();
        saveBooks();
        return true;
    }

    public Loan findLoanById(String id) {
        return loans.stream()
            .filter(l -> l.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public List<Loan> getActiveLoans() {
        return loans.stream()
            .filter(Loan::isActive)
            .collect(Collectors.toList());
    }

    public List<Loan> findLoansByUserId(String userId) {
        return loans.stream()
            .filter(l -> l.getUserId().equals(userId))
            .collect(Collectors.toList());
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    private void loadData() {
        users = fileManager.loadUsers();
        books = fileManager.loadBooks();
        loans = fileManager.loadLoans();
    }

    private void saveUsers() {
        fileManager.saveUsers(users);
    }

    private void saveBooks() {
        fileManager.saveBooks(books);
    }

    private void saveLoans() {
        fileManager.saveLoans(loans);
    }
}
