package com.miniproject.bookMyShow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin extends User{
    private String name;
    private String email;
    private String password;
    private String phone;

}
