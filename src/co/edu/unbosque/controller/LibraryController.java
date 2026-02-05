package co.edu.unbosque.controller;

import co.edu.unbosque.controller.facade.Library;
import co.edu.unbosque.model.dto.*;
import co.edu.unbosque.view.ViewConsole;

import java.util.List;

public class LibraryController {
    private Library library;
    private ViewConsole view;

    public LibraryController() {
        this.library = new Library();
        this.view = new ViewConsole();
    }

    public void start() {
        view.showWelcome();
        
        boolean exit = false;

        while (!exit) {
            int option = view.showMainMenu();
            
            switch (option) {
                case 1:
                    handleUserManagement();
                    break;
                case 2:
                    handleBookManagement();
                    break;
                case 3:
                    handleLoanManagement();
                    break;
                case 4:
                    handleSearch();
                    break;
                case 5:
                    handleViewActiveLoans();
                    break;
                case 0:
                    exit = true;
                    view.showGoodbye();
                    break;
                default:
                    view.showError("Invalid option. Please try again.");
            }
        }
        
        view.close();
    }

    private void handleUserManagement() {
        boolean backToMainMenu = false;
        
        while (!backToMainMenu) {
            int option = view.showUserMenu();
            
            switch (option) {
                case 1:
                    createUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    listAllUsers();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    view.showError("Invalid option.");
            }
        }
    }

    private void createUser() {
        UserDTO userDTO = view.collectUserData();
        
        if (library.createUser(userDTO)) {
            view.showSuccess("User created successfully.");
        } else {
            view.showError("User with this ID already exists.");
        }
    }

    private void updateUser() {
        String id = view.readString("\nEnter user ID to update: ");
        
        UserDTO existingUser = library.findUserById(id);
        
        if (existingUser == null) {
            view.showError("User not found.");
            return;
        }
        
        UserDTO updatedUser = view.collectUserUpdateData(existingUser);
        
        if (library.updateUser(updatedUser)) {
            view.showSuccess("User updated successfully.");
        } else {
            view.showError("Failed to update user.");
        }
    }

    private void deleteUser() {
        String id = view.readString("\nEnter user ID to delete: ");
        
        if (library.deleteUser(id)) {
            view.showSuccess("User deleted successfully.");
        } else {
            view.showError("Cannot delete user. User not found or has active loans.");
        }
    }

    private void listAllUsers() {
        List<UserDTO> users = library.getAllUsers();
        view.showUsers(users);
    }

    private void handleBookManagement() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            int option = view.showBookMenu();
            
            switch (option) {
                case 1:
                    createBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    listAllBooks();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    view.showError("Invalid option.");
            }
        }
    }

    private void createBook() {
        BookDTO bookDTO = view.collectBookData();
        
        if (library.createBook(bookDTO)) {
            view.showSuccess("Book created successfully.");
        } else {
            view.showError("Book with this ID already exists.");
        }
    }

    private void updateBook() {
        String id = view.readString("\nEnter book ID to update: ");
        
        BookDTO existingBook = library.findBookById(id);
        
        if (existingBook == null) {
            view.showError("Book not found.");
            return;
        }
        
        BookDTO updatedBook = view.collectBookUpdateData(existingBook);
        
        if (library.updateBook(updatedBook)) {
            view.showSuccess("Book updated successfully.");
        } else {
            view.showError("Failed to update book.");
        }
    }

    private void deleteBook() {
        String id = view.readString("\nEnter book ID to delete: ");
        
        if (library.deleteBook(id)) {
            view.showSuccess("Book deleted successfully.");
        } else {
            view.showError("Cannot delete book. Book not found or is currently loaned.");
        }
    }

    private void listAllBooks() {
        List<BookDTO> books = library.getAllBooks();
        view.showBooks(books);
    }

    private void handleLoanManagement() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            int option = view.showLoanMenu();
            
            switch (option) {
                case 1:
                    createLoan();
                    break;
                case 2:
                    registerReturn();
                    break;
                case 3:
                    listAllLoans();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    view.showError("Invalid option.");
            }
        }
    }

    private void createLoan() {
        view.showMessage("\n--- Create Loan ---");
        
        String userId = view.readString("Enter user ID: ");
        String bookId = view.readString("Enter book ID: ");
        
        String loanId = library.createLoan(userId, bookId);
        
        if (loanId != null) {
            view.showSuccess("Loan created successfully. Loan ID: " + loanId);
        } else {
            view.showError("Failed to create loan. User not found, book not found, or book not available.");
        }
    }

    private void registerReturn() {
        view.showMessage("\n--- Register Return ---");
        
        String loanId = view.readString("Enter loan ID: ");
        
        if (library.registerReturn(loanId)) {
            view.showSuccess("Return registered successfully.");
        } else {
            view.showError("Failed to register return. Loan not found or already returned.");
        }
    }

    private void listAllLoans() {
        List<LoanDTO> loans = library.getAllLoans();
        view.showLoans(loans);
    }

    private void handleSearch() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            int option = view.showSearchMenu();
            
            switch (option) {
                case 1:
                    searchBooksByTitle();
                    break;
                case 2:
                    searchUsersByName();
                    break;
                case 3:
                    searchLoansByUser();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    view.showError("Invalid option.");
            }
        }
    }

    private void searchBooksByTitle() {
        view.showMessage("\n--- Search Books by Title ---");
        
        String title = view.readString("Enter title (or part of title): ");
        
        List<BookDTO> books = library.findBooksByTitle(title);
        view.showBooks(books);
    }

    private void searchUsersByName() {
        view.showMessage("\n--- Search Users by Name ---");
        
        String name = view.readString("Enter name (or part of name): ");
        
        List<UserDTO> users = library.findUsersByName(name);
        view.showUsers(users);
    }

    private void searchLoansByUser() {
        view.showMessage("\n--- Search Loans by User ---");
        
        String userId = view.readString("Enter user ID: ");
        
        List<LoanDTO> loans = library.findLoansByUserId(userId);
        view.showLoans(loans);
    }

    private void handleViewActiveLoans() {
        List<LoanDTO> activeLoans = library.getActiveLoans();
        List<UserDTO> users = library.getAllUsers();
        List<BookDTO> books = library.getAllBooks();
        
        view.showLoansWithDetails(activeLoans, users, books);
    }
}
