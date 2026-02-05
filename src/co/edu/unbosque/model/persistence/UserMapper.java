package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.User;
import co.edu.unbosque.model.dto.UserDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserDTO dto = new UserDTO();
        
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        
        String birthDate = user.getBirthDate() != null ? user.getBirthDate().format(formatter) : "";
        dto.setBirthDate(birthDate);
        
        String registrationDate = user.getRegistrationDate() != null ? 
                                 user.getRegistrationDate().format(formatter) : "";
        dto.setRegistrationDate(registrationDate);
        
        dto.setActive(user.isActive());
        
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();

        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        
        LocalDate birthDate = (dto.getBirthDate() != null && !dto.getBirthDate().isEmpty()) ? 
                             LocalDate.parse(dto.getBirthDate(), formatter) : null;
        user.setBirthDate(birthDate);
        
        LocalDate registrationDate = (dto.getRegistrationDate() != null && !dto.getRegistrationDate().isEmpty()) ?
                                    LocalDate.parse(dto.getRegistrationDate(), formatter) : LocalDate.now();
        user.setRegistrationDate(registrationDate);
        
        user.setActive(dto.isActive());
        
        return user;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<User> toEntityList(List<UserDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
