package com.miniproject.bookMyShow.Services;


import com.miniproject.bookMyShow.Exceptions.Accounts.AccountAlreadyExistsException;
import com.miniproject.bookMyShow.Exceptions.Accounts.UserNotFoundException;
import com.miniproject.bookMyShow.Exceptions.UserValidation.InvalidPhoneNumberException;
import com.miniproject.bookMyShow.Models.User;
import com.miniproject.bookMyShow.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService {
    private UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userSignUp(String name, String email, String password, String phoneNumber) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AccountAlreadyExistsException("Account already exists!! Please go to login user.");
        }

        User newUser = new User();
        try {
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setPhone(phoneNumber);
        }catch (InvalidPhoneNumberException ex) {
            throw ex;
        }

        User savedUser = userRepository.save(newUser);

        return savedUser;
    }
    
    public User userLogin (String email, String password)
    {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Account not present. " +
                    "Please go to signup and register as new user.");
        }

        User user = userOptional.get();
        return user;
    }



}
