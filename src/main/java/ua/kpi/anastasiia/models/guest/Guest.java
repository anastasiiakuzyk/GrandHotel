package ua.kpi.anastasiia.models.guest;

import javax.swing.text.html.Option;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class Guest {
    int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String first_name;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 4, max = 30, message = "Name should be between 4 and 30 characters")
    String last_name;

    @Size(min = 13, max = 13, message = "Phone number should be +38 + 10 digits")
    String phone;

    @Size(min = 9, max = 9, message = "Passport number should be 9 digits")
    String passport;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    String email;

    List<Integer> options;

    String optionsList;

    public Guest() {
    }

    public Guest(String firstName, String lastName, String phone, String passport, String email) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.phone = phone;
        this.passport = passport;
        this.email = email;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public void setOptions(List<Integer> options) {
        this.options = options;
    }

    public String getOptionsList() {
        if (optionsList == null) {
            optionsList = "";
        }
        return optionsList;
    }

    public void setOptionsList(String optionsList) {
        this.optionsList = optionsList;
    }
}
