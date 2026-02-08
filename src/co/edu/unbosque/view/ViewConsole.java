package co.edu.unbosque.view;

import co.edu.unbosque.model.dto.BookDTO;
import co.edu.unbosque.model.dto.LoanDTO;
import co.edu.unbosque.model.dto.UserDTO;

import java.util.List;
import java.util.Scanner;

public class ViewConsole {
    private Scanner scanner;

    public ViewConsole() {
        this.scanner = new Scanner(System.in);
    }

    public String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine(); // Clear buffer
            System.out.print(message);
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return number;
    }

    public boolean readBoolean(String message) {
        System.out.print(message + " (yes/no): ");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String error) {
        System.out.println("ERROR: " + error);
    }

    public void showSuccess(String message) {
        System.out.println("SUCCESS: " + message);
    }

    public void showUser(UserDTO user) {
        if (user != null) {
            System.out.println("\n--- User Details ---");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Birth Date: " + user.getBirthDate());
            System.out.println("Registration Date: " + user.getRegistrationDate());
            System.out.println("Status: " + (user.isActive() ? "Active" : "Inactive"));
        } else {
            System.out.println("User not found.");
        }
    }

    public void showUsers(List<UserDTO> users) {
        if (users.isEmpty()) {
            System.out.println("\nNo users found.");
        } else {
            System.out.println("\n+-----------+----------------------+------------------------+--------------+-----------+");
            System.out.println("|                                    USER LIST                                       |");
            System.out.println("+-----------+----------------------+------------------------+--------------+-----------+");
            System.out.println("|    ID     |        Name          |         Email          |  Reg. Date   |  Status   |");
            System.out.println("+-----------+----------------------+------------------------+--------------+-----------+");
            
            for (UserDTO user : users) {
                String status = user.isActive() ? "Active" : "Inactive";
                
                System.out.printf("| %-9s | %-20s | %-22s | %-12s | %-9s |%n",
                    truncateText(user.getId(), 9),
                    truncateText(user.getName(), 20),
                    truncateText(user.getEmail(), 22),
                    truncateText(user.getRegistrationDate(), 12),
                    status
                );
            }
            
            System.out.println("+-----------+----------------------+------------------------+--------------+-----------+");
            System.out.println("Total users: " + users.size() + " | Active: " + 
                users.stream().filter(UserDTO::isActive).count());
        }
    }

    public void showBook(BookDTO book) {
        if (book != null) {
            System.out.println("Book{ID='" + book.getId() + "', Title='" + book.getTitle() + 
                             "', Author='" + book.getAuthor() + "', ISBN='" + book.getIsbn() + 
                             "', Stock=" + book.getStock() + ", Available=" + book.getAvailableStock() + "}");
        } else {
            System.out.println("Book not found.");
        }
    }

    public void showBooks(List<BookDTO> books) {
        if (books.isEmpty()) {
            System.out.println("\nNo books found.");
        } else {
            System.out.println("\n+-----------+--------------------------+-----------------------+-------------------+-------+-----------+");
            System.out.println("|                                        BOOK LIST                                             |");
            System.out.println("+-----------+--------------------------+-----------------------+-------------------+-------+-----------+");
            System.out.println("|    ID     |         Title            |        Author         |       ISBN        | Stock | Available |");
            System.out.println("+-----------+--------------------------+-----------------------+-------------------+-------+-----------+");
            
            for (BookDTO book : books) {
                System.out.printf("| %-9s | %-24s | %-21s | %-17s | %-5d | %-9d |%n",
                    truncateText(book.getId(), 9),
                    truncateText(book.getTitle(), 24),
                    truncateText(book.getAuthor(), 21),
                    truncateText(book.getIsbn(), 17),
                    book.getStock(),
                    book.getAvailableStock()
                );
            }
            
            System.out.println("+-----------+--------------------------+-----------------------+-------------------+-------+-----------+");
            int totalStock = books.stream().mapToInt(BookDTO::getStock).sum();
            int totalAvailable = books.stream().mapToInt(BookDTO::getAvailableStock).sum();
            System.out.println("Total books: " + books.size() + " | Total copies: " + totalStock + 
                             " | Available: " + totalAvailable);
        }
    }

    public void showLoan(LoanDTO loan) {
        if (loan != null) {
            System.out.println("Loan{ID='" + loan.getId() + "', User ID='" + loan.getUserId() + 
                             "', Book ID='" + loan.getBookId() + "', Loan Date=" + loan.getLoanDate() + 
                             ", Return Date=" + loan.getReturnDate() + 
                             ", Active=" + (loan.isActive() ? "Yes" : "No") + "}");
        } else {
            System.out.println("Loan not found.");
        }
    }

    public void showLoans(List<LoanDTO> loans) {
        if (loans.isEmpty()) {
            System.out.println("\nNo loans found.");
        } else {
            System.out.println("\n+-----------------+-----------+-----------+--------------+--------------+----------------------+");
            System.out.println("|                                     LOAN LIST                                       |");
            System.out.println("+-----------------+-----------+-----------+--------------+--------------+----------------------+");
            System.out.println("|    Loan ID      |  User ID  |  Book ID  |  Loan Date   | Return Date  |       Status         |");
            System.out.println("+-----------------+-----------+-----------+--------------+--------------+----------------------+");
            
            for (LoanDTO loan : loans) {
                String status = loan.isActive() ? "Active" : "Returned";
                
                System.out.printf("| %-15s | %-9s | %-9s | %-12s | %-12s | %-20s |%n",
                    truncateText(loan.getId(), 15),
                    truncateText(loan.getUserId(), 9),
                    truncateText(loan.getBookId(), 9),
                    loan.getLoanDate(),
                    loan.getReturnDate(),
                    status
                );
            }
            
            System.out.println("+-----------------+-----------+-----------+--------------+--------------+----------------------+");
            System.out.println("Total loans: " + loans.size() + " | Active: " + 
                loans.stream().filter(LoanDTO::isActive).count());
        }
    }

    public void showLoansWithDetails(List<LoanDTO> loans, List<UserDTO> users, List<BookDTO> books) {
        if (loans.isEmpty()) {
            System.out.println("\nNo active loans found.");
        } else {
            System.out.println("\n+-----------------+-----------------------+-----------------------------------+--------------+----------------------+");
            System.out.println("|                                 ACTIVE LOANS WITH DETAILS                                        |");
            System.out.println("+-----------------+-----------------------+-----------------------------------+--------------+----------------------+");
            System.out.println("|    Loan ID      |      User Name        |            Book Title             |  Loan Date   |       Status         |");
            System.out.println("+-----------------+-----------------------+-----------------------------------+--------------+----------------------+");
            
            for (LoanDTO loan : loans) {
                UserDTO user = users.stream()
                    .filter(u -> u.getId().equals(loan.getUserId()))
                    .findFirst()
                    .orElse(null);
                String userName = user != null ? user.getName() : "Unknown User";
                
                BookDTO book = books.stream()
                    .filter(b -> b.getId().equals(loan.getBookId()))
                    .findFirst()
                    .orElse(null);
                String bookTitle = book != null ? book.getTitle() : "Unknown Book";
                
                String status = loan.isActive() ? "Active" : "Returned";
                
                System.out.printf("| %-15s | %-21s | %-33s | %-12s | %-20s |%n",
                    truncateText(loan.getId(), 15),
                    truncateText(userName, 21),
                    truncateText(bookTitle, 33),
                    loan.getLoanDate(),
                    status
                );
            }
            
            System.out.println("+-----------------+-----------------------+-----------------------------------+--------------+----------------------+");
            System.out.println("Total active loans: " + loans.size());
        }
    }
    
    private String truncateText(String text, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    public void showWelcome() {
        System.out.println("=================================================");
        System.out.println("   LIBRARY MANAGEMENT SYSTEM");
        System.out.println("=================================================\n");
    }

    public int showMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. User Management");
        System.out.println("2. Book Management");
        System.out.println("3. Loan Management");
        System.out.println("4. Search");
        System.out.println("5. View Active Loans");
        System.out.println("0. Exit");
        System.out.println("================================");
        return readInt("Select an option: ");
    }

    public int showUserMenu() {
        System.out.println("\n======== USER MANAGEMENT ========");
        System.out.println("1. Create User");
        System.out.println("2. Update User");
        System.out.println("3. Delete User");
        System.out.println("4. List All Users");
        System.out.println("0. Back to Main Menu");
        System.out.println("=================================");
        return readInt("Select an option: ");
    }

    public int showBookMenu() {
        System.out.println("\n======== BOOK MANAGEMENT ========");
        System.out.println("1. Create Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. List All Books");
        System.out.println("0. Back to Main Menu");
        System.out.println("=================================");
        return readInt("Select an option: ");
    }

    public int showLoanMenu() {
        System.out.println("\n======== LOAN MANAGEMENT ========");
        System.out.println("1. Create Loan");
        System.out.println("2. Register Return");
        System.out.println("3. List All Loans");
        System.out.println("0. Back to Main Menu");
        System.out.println("=================================");
        return readInt("Select an option: ");
    }

    public int showSearchMenu() {
        System.out.println("\n=========== SEARCH ===========");
        System.out.println("1. Search Books by Title");
        System.out.println("2. Search Users by Name");
        System.out.println("3. Search Loans by User");
        System.out.println("0. Back to Main Menu");
        System.out.println("==============================");
        return readInt("Select an option: ");
    }

    public UserDTO collectUserData() {
        System.out.println("\n--- Enter User Data ---");
        String id = readString("User ID: ");
        String name = readString("Name: ");
        String email = readString("Email: ");
        String phone = readString("Phone: ");
        String address = readString("Address: ");
        String birthDate = readString("Birth Date (yyyy-MM-dd) [optional]: ");
        
        String registrationDate = java.time.LocalDate.now().toString();
        boolean isActive = true;
        
        return new UserDTO(id, name, email, phone, address, birthDate, registrationDate, isActive);
    }

    public UserDTO collectUserUpdateData(UserDTO existingUser) {
        System.out.println("\n--- Update User Data ---");
        showUser(existingUser);
        
        String name = readString("New name (press Enter to keep current): ");
        if (name.isEmpty()) name = existingUser.getName();
        
        String email = readString("New email (press Enter to keep current): ");
        if (email.isEmpty()) email = existingUser.getEmail();
        
        String phone = readString("New phone (press Enter to keep current): ");
        if (phone.isEmpty()) phone = existingUser.getPhone();
        
        String address = readString("New address (press Enter to keep current): ");
        if (address.isEmpty()) address = existingUser.getAddress();
        
        String birthDate = readString("New birth date (yyyy-MM-dd, press Enter to keep current): ");
        if (birthDate.isEmpty()) birthDate = existingUser.getBirthDate();
        
        String statusInput = readString("Active? (yes/no, press Enter to keep current): ");
        boolean isActive = existingUser.isActive();

        if (!statusInput.isEmpty()) {
            isActive = statusInput.equalsIgnoreCase("yes") || statusInput.equalsIgnoreCase("y");
        }
        
        return new UserDTO(existingUser.getId(), name, email, phone, address, birthDate, 
                          existingUser.getRegistrationDate(), isActive);
    }

    public BookDTO collectBookData() {
        System.out.println("\n--- Enter Book Data ---");
        String id = readString("Book ID: ");
        String title = readString("Title: ");
        String author = readString("Author: ");
        String isbn = readString("ISBN: ");
        int stock = readInt("Stock quantity: ");
        return new BookDTO(id, title, author, isbn, stock, stock);
    }

    public BookDTO collectBookUpdateData(BookDTO existingBook) {
        System.out.println("\n--- Update Book Data ---");
        showBook(existingBook);
        
        String title = readString("New title (press Enter to keep current): ");
        if (title.isEmpty()) title = existingBook.getTitle();
        
        String author = readString("New author (press Enter to keep current): ");
        if (author.isEmpty()) author = existingBook.getAuthor();
        
        String isbn = readString("New ISBN (press Enter to keep current): ");
        if (isbn.isEmpty()) isbn = existingBook.getIsbn();
        
        String stockInput = readString("New stock quantity (press Enter to keep current): ");
        int stock = existingBook.getStock();
        if (!stockInput.isEmpty()) {
            try {
                stock = Integer.parseInt(stockInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock quantity. Keeping current value.");
            }
        }
        
        int currentLoaned = existingBook.getStock() - existingBook.getAvailableStock();
        int availableStock = stock - currentLoaned;
        
        if (availableStock < 0) {
            System.out.println("Warning: New stock is less than currently loaned copies. Setting available to 0.");
            availableStock = 0;
        }
        
        return new BookDTO(existingBook.getId(), title, author, isbn, stock, availableStock);
    }

    public void showGoodbye() {
        System.out.println("\nThank you for using the Library Management System!");
        System.out.println("Goodbye!");
    }

    public void close() {
        scanner.close();
    }
}
