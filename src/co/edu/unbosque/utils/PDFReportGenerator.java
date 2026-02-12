package co.edu.unbosque.utils;

import co.edu.unbosque.model.dto.BookDTO;
import co.edu.unbosque.model.dto.LoanDTO;
import co.edu.unbosque.model.dto.UserDTO;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Utility class for generating PDF reports using Apache PDFBox
 * All reports include a standardized institutional header
 */
public class PDFReportGenerator {
    
    // Report configuration
    private static final String REPORTS_FOLDER = "reports";
    private static final float MARGIN = 50;
    private static final float FONT_SIZE = 12;
    private static final float TITLE_FONT_SIZE = 18;
    private static final float SUBTITLE_FONT_SIZE = 14;
    private static final float LINE_HEIGHT = 15;
    
    // Institutional information (Header template)
    private static final String INSTITUTION = "Universidad El Bosque";
    private static final String FACULTY = "Facultad de Ingenieria";
    private static final String COURSE = "Bases de Datos I";
    private static final String APP_NAME = "Library Management System";
    
    /**
     * Adds standardized institutional header to a PDF page
     * 
     * @param contentStream The content stream to write to
     * @param page The current page
     * @param reportType The type of report (e.g., "Users by Address", "Overdue Loans")
     * @return The Y position where content should start (after the header)
     * @throws IOException if writing fails
     */
    private static float addReportHeader(PDPageContentStream contentStream, PDPage page, String reportType) 
            throws IOException {
        float yPosition = page.getMediaBox().getHeight() - MARGIN;
        
        // Institution name (centered, bold)
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        String institutionText = INSTITUTION;
        float institutionWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(institutionText) / 1000 * 16;
        float institutionX = (page.getMediaBox().getWidth() - institutionWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(institutionX, yPosition);
        contentStream.showText(institutionText);
        contentStream.endText();
        yPosition -= 20;
        
        // Faculty and Course (centered)
        contentStream.setFont(PDType1Font.HELVETICA, 11);
        String facultyText = FACULTY + " - " + COURSE;
        float facultyWidth = PDType1Font.HELVETICA.getStringWidth(facultyText) / 1000 * 11;
        float facultyX = (page.getMediaBox().getWidth() - facultyWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(facultyX, yPosition);
        contentStream.showText(facultyText);
        contentStream.endText();
        yPosition -= 15;
        
        // App name (centered)
        String appNameText = APP_NAME;
        float appNameWidth = PDType1Font.HELVETICA.getStringWidth(appNameText) / 1000 * 11;
        float appNameX = (page.getMediaBox().getWidth() - appNameWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(appNameX, yPosition);
        contentStream.showText(appNameText);
        contentStream.endText();
        yPosition -= 30;
        
        // Horizontal line separator
        contentStream.setLineWidth(1f);
        contentStream.moveTo(MARGIN, yPosition);
        contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
        contentStream.stroke();
        yPosition -= 20;
        
        // Report title (centered, bold)
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, TITLE_FONT_SIZE);
        String reportTitle = "REPORTE";
        float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(reportTitle) / 1000 * TITLE_FONT_SIZE;
        float titleX = (page.getMediaBox().getWidth() - titleWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(titleX, yPosition);
        contentStream.showText(reportTitle);
        contentStream.endText();
        yPosition -= 25;
        
        // Report type (centered)
        contentStream.setFont(PDType1Font.HELVETICA, SUBTITLE_FONT_SIZE);
        float typeWidth = PDType1Font.HELVETICA.getStringWidth(reportType) / 1000 * SUBTITLE_FONT_SIZE;
        float typeX = (page.getMediaBox().getWidth() - typeWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(typeX, yPosition);
        contentStream.showText(reportType);
        contentStream.endText();
        yPosition -= 20;
        
        // Generation date (centered)
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        String dateText = "Fecha de Generacion: " + LocalDate.now().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        float dateWidth = PDType1Font.HELVETICA.getStringWidth(dateText) / 1000 * 10;
        float dateX = (page.getMediaBox().getWidth() - dateWidth) / 2;
        contentStream.beginText();
        contentStream.newLineAtOffset(dateX, yPosition);
        contentStream.showText(dateText);
        contentStream.endText();
        yPosition -= 30;
        
        // Another horizontal line
        contentStream.setLineWidth(0.5f);
        contentStream.moveTo(MARGIN, yPosition);
        contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
        contentStream.stroke();
        yPosition -= 20;
        
        return yPosition;
    }
    
    /**
     * Generates an example PDF report to demonstrate capabilities
     * 
     * @return The generated file path, or null if generation failed
     */
    public static String generateExampleReport() {
        // Create reports directory if it doesn't exist
        new File(REPORTS_FOLDER).mkdirs();
        
        String fileName = REPORTS_FOLDER + "/report_" + System.currentTimeMillis() + ".pdf";
        
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            // Add standardized institutional header
            float yPosition = addReportHeader(contentStream, page, "Ejemplo - Demostracion de PDFs");
            yPosition -= 10;
            
            // Summary
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE + 2);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Report Summary");
            contentStream.endText();
            yPosition -= 20;
            
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("This is an example report demonstrating PDF generation capabilities.");
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
            
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Total Records: 5");
            contentStream.endText();
            yPosition -= LINE_HEIGHT;
            
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Report Type: Demo");
            contentStream.endText();
            yPosition -= 30;
            
            // Table header
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Sample Data");
            contentStream.endText();
            yPosition -= 25;
            
            // Table content
            String[][] sampleData = {
                {"1", "Sample Item A", "Books", "10", "SUCCESS"},
                {"2", "Sample Item B", "Users", "25", "SUCCESS"},
                {"3", "Sample Item C", "Loans", "8", "WARNING"},
                {"4", "Sample Item D", "Returns", "15", "INFO"},
                {"5", "Sample Item E", "Overdue", "3", "DANGER"}
            };
            
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
            for (String[] row : sampleData) {
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, yPosition);
                String line = String.format("#%s - %s (%s) - Value: %s - Status: %s", 
                    row[0], row[1], row[2], row[3], row[4]);
                contentStream.showText(line);
                contentStream.endText();
                yPosition -= LINE_HEIGHT;
            }
            
            yPosition -= 20;
            
            // Features section
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE + 2);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Features");
            contentStream.endText();
            yPosition -= 20;
            
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
            String[] features = {
                "- Professional PDF generation with Apache PDFBox",
                "- No dependencies on external tools",
                "- Pure Java implementation",
                "- Easy to customize",
                "- Print-ready format"
            };
            
            for (String feature : features) {
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText(feature);
                contentStream.endText();
                yPosition -= LINE_HEIGHT;
            }
            
            // Footer
            yPosition = MARGIN + 20;
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Library Management System - Universidad El Bosque - 2026");
            contentStream.endText();
            
            contentStream.close();
            document.save(fileName);
            
            return fileName;
        } catch (IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            return null;
        }
    }
    
    // ============================================================================
    // REPORT TEMPLATES - TODO: Implement these methods
    // Each team member should implement ONE of these methods
    // Use generateExampleReport() above as a reference/guide
    // ============================================================================
    
    /**
     * REPORT 1 - Users by Address ✅ IMPLEMENTED
     * 
     * @param filteredUsers Users already filtered by Controller
     * @param address Address for title
     * @return PDF file path or null
     * 
     * IMPLEMENTATION:
     * - Uses institutional header template
     * - Shows summary with total users
     * - Lists: ID, Name, Email, Phone, Address, Status
     * - Automatic pagination
     */
    public static String generateUsersByAddressReport(List<UserDTO> filteredUsers, String address) {
        new File(REPORTS_FOLDER).mkdirs();
        String fileName = REPORTS_FOLDER + "/users_by_address_" + System.currentTimeMillis() + ".pdf";
        
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            // Add institutional header
            float yPosition = addReportHeader(contentStream, page, "Usuarios por Direccion: " + address);
            
            // Summary
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE + 2);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Resumen");
            contentStream.endText();
            yPosition -= 20;
            
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Total de usuarios encontrados: " + filteredUsers.size());
            contentStream.endText();
            yPosition -= 30;
            
            // Users list
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Lista de Usuarios");
            contentStream.endText();
            yPosition -= 25;
            
            // Data
            for (UserDTO user : filteredUsers) {
                // Check if we need a new page
                if (yPosition < MARGIN + 100) {
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = page.getMediaBox().getHeight() - MARGIN;
                }
                
                // User ID and Name (bold)
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText("ID: " + user.getId() + " - " + user.getName());
                contentStream.endText();
                yPosition -= LINE_HEIGHT;
                
                // Email
                contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN + 10, yPosition);
                contentStream.showText("Email: " + user.getEmail());
                contentStream.endText();
                yPosition -= LINE_HEIGHT;
                
                // Phone
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN + 10, yPosition);
                contentStream.showText("Telefono: " + user.getPhone());
                contentStream.endText();
                yPosition -= LINE_HEIGHT;
                
                // Address
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN + 10, yPosition);
                contentStream.showText("Direccion: " + user.getAddress());
                contentStream.endText();
                yPosition -= LINE_HEIGHT;
                
                // Status
                String status = user.isActive() ? "Activo" : "Inactivo";
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN + 10, yPosition);
                contentStream.showText("Estado: " + status);
                contentStream.endText();
                yPosition -= 25;
            }
            
            contentStream.close();
            document.save(fileName);
            
            return fileName;
        } catch (IOException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * TODO: REPORT 2 - Books Loaned by Month
     * ASSIGNED TO: [Team Member Name]
     * 
     * @param filteredLoans Loans already filtered by Controller
     * @param allBooks All books (for lookup by ID)
     * @param year Year for title
     * @param month Month for title
     * @return PDF file path or null
     * 
     * STEPS:
     * 1. Create PDDocument, PDPage, PDPageContentStream
     * 2. Add header: addReportHeader(contentStream, page, "Prestamos del Mes: " + monthName)
     * 3. Add summary: "Total: [filteredLoans.size()]"
     * 4. Loop loans: write Loan ID, Book Title, User ID, Date, Status
     * 5. Save document
     * 
     * TIP: Find book with allBooks.stream().filter(b -> b.getId().equals(loan.getBookId())).findFirst()
     */
    public static String generateLoansByMonthReport(List<LoanDTO> filteredLoans, List<BookDTO> allBooks, 
                                                    int year, int month) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Report not implemented yet. Assigned to: [Team Member Name]");
    }
    
    /**
     * TODO: REPORT 3 - Users with Overdue Loans
     * ASSIGNED TO: [Team Member Name]
     * 
     * @param overdueLoans Overdue loans already identified by Controller
     * @param allUsers All users (for lookup by ID)
     * @param allBooks All books (for lookup by ID)
     * @return PDF file path or null
     * 
     * STEPS:
     * 1. Create PDDocument, PDPage, PDPageContentStream
     * 2. Add header: addReportHeader(contentStream, page, "Prestamos Vencidos")
     * 3. Add summary: "Total vencidos: [overdueLoans.size()]"
     * 4. Loop loans: write User Name, Book Title, Due Date, Days Overdue
     * 5. Save document
     * 
     * TIP: Days overdue = ChronoUnit.DAYS.between(returnDate, LocalDate.now())
     */
    public static String generateOverdueLoansReport(List<LoanDTO> overdueLoans, List<UserDTO> allUsers, 
                                                    List<BookDTO> allBooks) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Report not implemented yet. Assigned to: [Team Member Name]");
    }
    
    /**
     * TODO: REPORT 4 - Inactive Users (No Loans in Last Month)
     * ASSIGNED TO: [Team Member Name]
     * 
     * @param inactiveUsers Users already identified as inactive by Controller
     * @return PDF file path or null
     * 
     * STEPS:
     * 1. Create PDDocument, PDPage, PDPageContentStream
     * 2. Add header: addReportHeader(contentStream, page, "Usuarios Inactivos")
     * 3. Add summary: "Total inactivos: [inactiveUsers.size()]"
     * 4. Add subtitle: "Sin prestamos en ultimos 30 dias"
     * 5. Loop users: write ID, Name, Email, Phone, Registration Date
     * 6. Save document
     */
    public static String generateInactiveUsersReport(List<UserDTO> inactiveUsers) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Report not implemented yet. Assigned to: [Team Member Name]");
    }
    
    /**
     * TODO: REPORT 5 - Users with Birthdays in Specific Month
     * ASSIGNED TO: [Team Member Name]
     * 
     * @param birthdayUsers Users already filtered and sorted by Controller
     * @param month Month for title
     * @return PDF file path or null
     * 
     * STEPS:
     * 1. Create PDDocument, PDPage, PDPageContentStream
     * 2. Add header: addReportHeader(contentStream, page, "Cumpleanos del Mes: " + monthName)
     * 3. Add summary: "Total usuarios: [birthdayUsers.size()]"
     * 4. Loop users: write Name, Birthday, Age, Email
     * 5. Save document
     * 
     * TIP: Parse date with DateFormatter.parseTextDateToLocalDate(user.getBirthDate())
     * TIP: Calculate age = LocalDate.now().getYear() - birthDate.getYear()
     */

    public static String generateBirthdayUsersReport(List<UserDTO> birthdayUsers, int month) {
        new File(REPORTS_FOLDER).mkdirs();
        String fileName = REPORTS_FOLDER + "/birthday_users_" + month + "_" + System.currentTimeMillis() + ".pdf";

        String monthName;
        try {
            monthName = java.time.Month.of(month)
                    .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "CO"));
        } catch (Exception e) {
            monthName = String.valueOf(month);
        }
        if (monthName != null && !monthName.isEmpty()) {
            monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float yPosition = addReportHeader(contentStream, page, "Cumpleanos del Mes: " + monthName);

            // Summary
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE + 2);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Resumen");
            contentStream.endText();
            yPosition -= 20;

            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Total de usuarios: " + (birthdayUsers == null ? 0 : birthdayUsers.size()));
            contentStream.endText();
            yPosition -= 30;

            // List title
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText("Usuarios que cumplen años este mes");
            contentStream.endText();
            yPosition -= 25;

            java.time.format.DateTimeFormatter birthFmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate today = java.time.LocalDate.now();

            if (birthdayUsers != null) {
                for (UserDTO user : birthdayUsers) {
                    if (user == null) continue;

                    // New page if needed
                    if (yPosition < MARGIN + 110) {
                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = addReportHeader(contentStream, page, "Cumpleaños del Mes: " + monthName);
                        yPosition -= 10;
                    }

                    String name = user.getName() == null ? "" : user.getName();
                    String email = user.getEmail() == null ? "" : user.getEmail();
                    String birthStr = user.getBirthDate() == null ? "" : user.getBirthDate().trim();

                    java.time.LocalDate birthDate = null;
                    try {
                        if (!birthStr.isEmpty()) birthDate = java.time.LocalDate.parse(birthStr);
                    } catch (Exception ignored) { }

                    String birthDisplay = birthDate == null ? "N/A" : birthDate.format(birthFmt);
                    String ageDisplay = "N/A";
                    if (birthDate != null) {
                        int age = java.time.Period.between(birthDate, today).getYears();
                        if (age < 0) age = 0;
                        ageDisplay = String.valueOf(age);
                    }

                    // Name (bold)
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN, yPosition);
                    contentStream.showText(name);
                    contentStream.endText();
                    yPosition -= LINE_HEIGHT;

                    // Birthday + age
                    contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN + 10, yPosition);
                    contentStream.showText("Cumpleaños: " + birthDisplay + " | Edad: " + ageDisplay);
                    contentStream.endText();
                    yPosition -= LINE_HEIGHT;

                    // Email
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN + 10, yPosition);
                    contentStream.showText("Email: " + email);
                    contentStream.endText();
                    yPosition -= 22;
                }
            }

            contentStream.close();
            document.save(fileName);
            return fileName;

        } catch (IOException e) {
            System.err.println("Error generating birthday users PDF: " + e.getMessage());
            return null;
        }
    }

}
