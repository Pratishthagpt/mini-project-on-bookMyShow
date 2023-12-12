package com.miniproject.bookMyShow.DTOs.User;

import com.miniproject.bookMyShow.DTOs.ResponseStatus;
import com.miniproject.bookMyShow.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthResponseDTO {
    private User user;
    private ResponseStatus responseStatus;
    private String failureReason;
}
