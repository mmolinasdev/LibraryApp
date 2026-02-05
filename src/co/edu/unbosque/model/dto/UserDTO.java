package co.edu.unbosque.model.dto;

public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String birthDate;           
    private String registrationDate;  
    private boolean isActive;

    public UserDTO() {
    }

    public UserDTO(String id, String name, String email, String phone, String address,
                   String birthDate, String registrationDate, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
