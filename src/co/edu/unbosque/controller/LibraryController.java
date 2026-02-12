package co.edu.unbosque.controller;

import co.edu.unbosque.controller.facade.Library;
import co.edu.unbosque.model.dto.*;
import co.edu.unbosque.utils.PDFReportGenerator;
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
                case 6:
                    handleReports();
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

    private void handleReports() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            int option = view.showReportsMenu();
            
            switch (option) {
                case 1:
                    generateUsersByAddressReport();
                    break;
                case 2:
                    generateLoansByMonthReport();
                    break;
                case 3:
                    generateOverdueLoansReport();
                    break;
                case 4:
                    generateInactiveUsersReport();
                    break;
                case 5:
                    generateBirthdayUsersReport();
                    break;
                case 6:
                    generateExampleReport();
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    view.showError("Invalid option.");
            }
        }
    }

    private void generateUsersByAddressReport() {
        view.showMessage("\n--- Users by Address Report ---");
        String address = view.readString("Enter address to search: ");
        
        try {
            List<UserDTO> allUsers = library.getAllUsers();
            List<UserDTO> filteredUsers = allUsers.stream()
                .filter(u -> u.getAddress().toLowerCase().contains(address.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
            
            if (filteredUsers.isEmpty()) {
                view.showMessage("No users found at address: " + address);
                return;
            }
            
            String fileName = PDFReportGenerator.generateUsersByAddressReport(filteredUsers, address);

            view.showSuccess("PDF report generated successfully!");
            view.showMessage("File: " + fileName);
            view.showMessage("Open the PDF file with any PDF reader.");
        } catch (UnsupportedOperationException e) {
            view.showError("Report not implemented yet.");
            view.showMessage("TODO: Assigned team member should implement this in PDFReportGenerator.java");
        } catch (Exception e) {
            view.showError("Failed to generate report: " + e.getMessage());
        }
    }

    private void generateLoansByMonthReport() {
        view.showMessage("\n--- Books Loaned by Month Report ---");
        int year = view.readInt("Enter year (e.g., 2024): ");
        int month = view.readInt("Enter month (1-12): ");
        
        if (month < 1 || month > 12) {
            view.showError("Invalid month. Please enter a value between 1 and 12.");
            return;
        }
        
        // TODO: Implement business logic here
        // 1. Get data: library.getAllLoans(), library.getAllBooks()
        // 2. Filter loans by year and month
        // 3. Check if empty
        // 4. Call: PDFReportGenerator.generateLoansByMonthReport(filteredLoans, allBooks, year, month)
        view.showError("Report not implemented yet.");
        view.showMessage("TODO: Assigned team member should implement filtering logic and call PDFReportGenerator");
    }

    private void generateOverdueLoansReport() {
        view.showMessage("\n--- Overdue Loans Report ---");
        
        // TODO: Implement business logic here
        // 1. Get data: library.getAllLoans(), library.getAllUsers(), library.getAllBooks()
        // 2. Get today's date: LocalDate.now()
        // 3. Filter loans: loan.isActive() && returnDate.isBefore(today)
        // 4. Check if empty
        // 5. Call: PDFReportGenerator.generateOverdueLoansReport(overdueLoans, allUsers, allBooks)
        view.showError("Report not implemented yet.");
        view.showMessage("TODO: Assigned team member should implement filtering logic and call PDFReportGenerator");
    }

    private void generateInactiveUsersReport() {
        view.showMessage("\n--- Inactive Users Report ---");
        
        // TODO: Implement business logic here
        // 1. Get data: library.getAllUsers(), library.getAllLoans()
        // 2. Calculate date: LocalDate.now().minusDays(30)
        // 3. Get Set<String> of active userIds (who borrowed in last 30 days)
        // 4. Filter users NOT in activeUserIds
        // 5. Check if empty
        // 6. Call: PDFReportGenerator.generateInactiveUsersReport(inactiveUsers)
        view.showError("Report not implemented yet.");
        view.showMessage("TODO: Assigned team member should implement filtering logic and call PDFReportGenerator");
    }

    private void generateBirthdayUsersReport() {
        view.showMessage("\n--- Users with Birthdays Report ---");
        int month = view.readInt("Enter month (1-12): ");

        if (month < 1 || month > 12) {
            view.showError("Invalid month. Please enter a value between 1 and 12.");
            return;
        }

        // NOTE: UserDTO birthDate comes in ISO format (yyyy-MM-dd) from UserMapper.toDTO().
        // Therefore, we must parse with LocalDate.parse(...) (NOT DateFormatter.parseTextDateToLocalDate).

        List<UserDTO> allUsers = library.getAllUsers();
        if (allUsers == null || allUsers.isEmpty()) {
            view.showError("No users found in the system.");
            return;
        }

        List<UserDTO> birthdayUsers = new java.util.ArrayList<>();
        for (UserDTO user : allUsers) {
            if (user == null) continue;
            String birthDateStr = user.getBirthDate();
            if (birthDateStr == null || birthDateStr.trim().isEmpty()) continue;

            try {
                java.time.LocalDate birthDate = java.time.LocalDate.parse(birthDateStr.trim());
                if (birthDate.getMonthValue() == month) {
                    birthdayUsers.add(user);
                }
            } catch (Exception ignored) {
                // Skip malformed dates instead of crashing the report.
            }
        }

        if (birthdayUsers.isEmpty()) {
            view.showError("No users with birthdays found for month: " + month);
            return;
        }

        // Sort by day of month, then by name
        birthdayUsers.sort((u1, u2) -> {
            try {
                int d1 = java.time.LocalDate.parse(u1.getBirthDate()).getDayOfMonth();
                int d2 = java.time.LocalDate.parse(u2.getBirthDate()).getDayOfMonth();
                if (d1 != d2) return Integer.compare(d1, d2);
            } catch (Exception ignored) { }

            String n1 = u1.getName() == null ? "" : u1.getName();
            String n2 = u2.getName() == null ? "" : u2.getName();
            return n1.compareToIgnoreCase(n2);
        });

        try {
            String fileName = PDFReportGenerator.generateBirthdayUsersReport(birthdayUsers, month);
            if (fileName == null) {
                view.showError("Failed to generate report.");
                return;
            }
            view.showSuccess("Birthday users report generated successfully!");
            view.showMessage("File: " + fileName);
        } catch (Exception e) {
            view.showError("Failed to generate report: " + e.getMessage());
        }
    }


    private void generateExampleReport() {
        view.showMessage("\n--- Example Report (Demo) ---");
        view.showMessage("Generating PDF report with sample data...");
        
        try {
            String fileName = PDFReportGenerator.generateExampleReport();
            view.showSuccess("Example PDF report generated successfully!");
            view.showMessage("File: " + fileName);
            view.showMessage("Open the PDF file with any PDF reader.");
        } catch (UnsupportedOperationException e) {
            view.showError("PDF generation not configured yet.");
            view.showMessage("");
            view.showMessage("Please follow these steps:");
            view.showMessage("1. Download PDFBox JARs (see PDF_SETUP.md)");
            view.showMessage("2. Add JARs to lib/ folder");
            view.showMessage("3. Uncomment code in PDFReportGenerator.java");
            view.showMessage("4. Recompile with: javac -cp \"lib/*:src\" -d bin -sourcepath src src/Main.java");
        } catch (Exception e) {
            view.showError("Failed to generate report: " + e.getMessage());
        }
    }
}
