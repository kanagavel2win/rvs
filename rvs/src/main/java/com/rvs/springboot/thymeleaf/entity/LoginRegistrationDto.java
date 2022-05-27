package com.rvs.springboot.thymeleaf.entity;

import javax.validation.constraints.NotEmpty;

import com.rvs.springboot.thymeleaf.constraint.FieldMatch;

import lombok.Getter;
import lombok.Setter;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
})

@Getter @Setter
public class LoginRegistrationDto {
	@NotEmpty
    private String empid;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}
