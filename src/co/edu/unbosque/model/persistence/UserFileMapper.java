package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.User;
import co.edu.unbosque.utils.DateFormatter;

import java.time.LocalDate;

public class UserFileMapper {
    
    private static final String DELIMITER = "|";

    public static User fromFileLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] fields = line.split("\\" + DELIMITER);
        
        if (fields.length != 8) {
            return null;
        }
        
        try {
            User user = new User();
            
            user.setId(fields[0]);
            user.setName(fields[1]);
            user.setEmail(fields[2]);
            user.setPhone(fields[3]);
            user.setAddress(fields[4]);
            
            LocalDate birthDate = fields[5].isEmpty() ? null : 
                                DateFormatter.parseTextDateToLocalDate(fields[5]);
            user.setBirthDate(birthDate);
            
            LocalDate registrationDate = fields[6].isEmpty() ? null : LocalDate.parse(fields[6]);
            user.setRegistrationDate(registrationDate);
            
            user.setActive(Boolean.parseBoolean(fields[7]));
            
            return user;
            
        } catch (Exception e) {
            System.err.println("Error parsing user line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    public static String toFileLine(User user) {
        if (user == null) {
            return "";
        }

        String birthDateStr = user.getBirthDate() != null ? 
                             DateFormatter.convertDateForFileStorage(user.getBirthDate()) : "";

        String registrationDateStr = user.getRegistrationDate() != null ? 
                                    user.getRegistrationDate().toString() : "";
        
        return user.getId() + DELIMITER + 
               user.getName() + DELIMITER + 
               user.getEmail() + DELIMITER + 
               user.getPhone() + DELIMITER + 
               user.getAddress() + DELIMITER + 
               birthDateStr + DELIMITER + 
               registrationDateStr + DELIMITER + 
               user.isActive();
    }
}
