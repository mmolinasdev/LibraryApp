package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Loan;
import co.edu.unbosque.model.dto.LoanDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class LoanMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LoanDTO toDTO(Loan loan) {
        if (loan == null) {
            return null;
        }
        
        LoanDTO dto = new LoanDTO();
        
        dto.setId(loan.getId());
        dto.setUserId(loan.getUserId());
        dto.setBookId(loan.getBookId());
        
        String loanDate = loan.getLoanDate().format(formatter);
        dto.setLoanDate(loanDate);
        
        String returnDate = loan.getReturnDate() != null ? 
                           loan.getReturnDate().format(formatter) : "Pending";
        dto.setReturnDate(returnDate);
        
        dto.setActive(loan.isActive());
        
        return dto;
    }

    public static Loan toEntity(LoanDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Loan loan = new Loan();
        
        loan.setId(dto.getId());
        loan.setUserId(dto.getUserId());
        loan.setBookId(dto.getBookId());
        
        LocalDate loanDate = LocalDate.parse(dto.getLoanDate(), formatter);
        loan.setLoanDate(loanDate);
        
        LocalDate returnDate = dto.getReturnDate().equals("Pending") ? null : 
                              LocalDate.parse(dto.getReturnDate(), formatter);
        loan.setReturnDate(returnDate);
        
        loan.setActive(dto.isActive());
        
        return loan;
    }

    public static List<LoanDTO> toDTOList(List<Loan> loans) {
        if (loans == null) {
            return null;
        }
        
        return loans.stream()
                .map(LoanMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Loan> toEntityList(List<LoanDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(LoanMapper::toEntity)
                .collect(Collectors.toList());
    }
}
