package co.edu.unbosque.controller.facade;

import co.edu.unbosque.model.Book;
import co.edu.unbosque.model.Loan;
import co.edu.unbosque.model.User;
import co.edu.unbosque.model.dto.*;
import co.edu.unbosque.model.persistence.*;

import java.util.List;

public class Library {
    private final LibraryDAO libraryDAO;

    public Library() {
        this.libraryDAO = new LibraryDAO();
    }

    public boolean createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        return libraryDAO.createUser(user);
    }

    public boolean updateUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        return libraryDAO.updateUser(user);
    }

    public boolean deleteUser(String id) {
        return libraryDAO.deleteUser(id);
    }

    public UserDTO findUserById(String id) {
        User user = libraryDAO.findUserById(id);
        return UserMapper.toDTO(user);
    }

    public List<UserDTO> findUsersByName(String name) {
        List<User> users = libraryDAO.findUsersByName(name);
        return UserMapper.toDTOList(users);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = libraryDAO.getAllUsers();
        return UserMapper.toDTOList(users);
    }

    public boolean createBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        return libraryDAO.createBook(book);
    }

    public boolean updateBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        return libraryDAO.updateBook(book);
    }

    public boolean deleteBook(String id) {
        return libraryDAO.deleteBook(id);
    }

    public BookDTO findBookById(String id) {
        Book book = libraryDAO.findBookById(id);
        return BookMapper.toDTO(book);
    }

    public List<BookDTO> findBooksByTitle(String title) {
        List<Book> books = libraryDAO.findBooksByTitle(title);
        return BookMapper.toDTOList(books);
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = libraryDAO.getAllBooks();
        return BookMapper.toDTOList(books);
    }

    public String createLoan(String userId, String bookId) {
        return libraryDAO.createLoan(userId, bookId);
    }

    public boolean registerReturn(String loanId) {
        return libraryDAO.registerReturn(loanId);
    }

    public LoanDTO findLoanById(String id) {
        Loan loan = libraryDAO.findLoanById(id);
        return LoanMapper.toDTO(loan);
    }

    public List<LoanDTO> getActiveLoans() {
        List<Loan> loans = libraryDAO.getActiveLoans();
        return LoanMapper.toDTOList(loans);
    }

    public List<LoanDTO> findLoansByUserId(String userId) {
        List<Loan> loans = libraryDAO.findLoansByUserId(userId);
        return LoanMapper.toDTOList(loans);
    }

    public List<LoanDTO> getAllLoans() {
        List<Loan> loans = libraryDAO.getAllLoans();
        return LoanMapper.toDTOList(loans);
    }
}
