package com.miniproject.bookMyShow.Controllers;

import com.miniproject.bookMyShow.DTOs.*;
import com.miniproject.bookMyShow.DTOs.User.UserLoginRequestDTO;
import com.miniproject.bookMyShow.DTOs.User.UserAuthResponseDTO;
import com.miniproject.bookMyShow.DTOs.User.UserSignUpRequestDTO;
import com.miniproject.bookMyShow.Exceptions.Accounts.AccountsException;
import com.miniproject.bookMyShow.Exceptions.UserValidation.InvalidUserDetailsException;
import com.miniproject.bookMyShow.Models.User;
import com.miniproject.bookMyShow.Services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserAuthController {

    private UserAuthService userAuthService;

    @Autowired
    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    public UserAuthResponseDTO signUpUser(UserSignUpRequestDTO signupRequest) {
        UserAuthResponseDTO newUserSignUpResponse = new UserAuthResponseDTO();

        User newUser;
        try {
            newUser = userAuthService.userSignUp(signupRequest.getName(), signupRequest.getEmail(),
                    signupRequest.getPassword(), signupRequest.getPhone());

            newUserSignUpResponse.setResponseStatus(ResponseStatus.SUCCESS);
            newUserSignUpResponse.setUser(newUser);

        } catch (InvalidUserDetailsException ex) {
            newUserSignUpResponse.setResponseStatus(ResponseStatus.FAILURE);
            newUserSignUpResponse.setFailureReason(ex.getMessage());
        }

        return newUserSignUpResponse;
    }

    public UserAuthResponseDTO loginUser (UserLoginRequestDTO loginRequest) {
        UserAuthResponseDTO loginResponseDTO = new UserAuthResponseDTO();

        User user;
        try{
            user = userAuthService.userLogin(loginRequest.getEmail(), loginRequest.getPassword());
            loginResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            loginResponseDTO.setUser(user);
        } catch (AccountsException ex) {
            loginResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            loginResponseDTO.setFailureReason(ex.getMessage());
        }
        return loginResponseDTO;
    }
}
