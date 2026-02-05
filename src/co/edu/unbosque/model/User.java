package co.edu.unbosque.model;

import java.time.LocalDate;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private boolean isActive;

    public User() {
        this.isActive = true;
    }

    public User(String id, String name, String email, String phone, String address, 
                LocalDate birthDate, LocalDate registrationDate, boolean isActive) {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
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
        return "User{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", Phone='" + phone + '\'' +
                ", Address='" + address + '\'' +
                ", BirthDate=" + birthDate +
                ", RegistrationDate=" + registrationDate +
                ", Active=" + isActive +
                '}';
    }
}
