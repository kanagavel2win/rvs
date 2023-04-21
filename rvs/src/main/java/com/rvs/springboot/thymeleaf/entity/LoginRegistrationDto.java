package com.rvs.springboot.thymeleaf.entity;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;



@Getter @Setter
public class LoginRegistrationDto {
	@NotEmpty
    private String empid;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}
