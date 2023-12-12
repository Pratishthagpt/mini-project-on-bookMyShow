package com.miniproject.bookMyShow.Models;

import com.miniproject.bookMyShow.Exceptions.UserValidation.InvalidPhoneNumberException;
import com.miniproject.bookMyShow.Exceptions.UserValidation.InvalidUsernameException;
import com.miniproject.bookMyShow.Exceptions.UserValidation.PasswordTooSimpleException;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    private String phone;

    @OneToOne
    private Admin admin;

    @OneToMany(mappedBy = "bookedBy")
    private List<Booking> bookings;

    public void setName (String name) {
        if (name.length() < 3) {
            throw new InvalidUsernameException("The username should have more than 3 letters.");
        }
        this.name = name;
    }

    public void setPhone (String phone) {
        if (phone.length() == 10) {
            this.phone = phone;
        }
        else {
            throw new InvalidPhoneNumberException("Please enter the phone no. with 10 digits only.");
        }
    }

    public void setPassword (String password) {
        if (password.length() < 8) {
            throw new PasswordTooSimpleException("Password is too simple!! Please enter the password" +
                    " having more than 8 characters");
        }
        this.password = password;   // we should store the encrypted and hashed password in database
    }

}
