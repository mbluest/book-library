package com.example.bookslibraries.kurniads.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
public class LoginDto {

    private String accountId;

    private String nickname;

    private String email;

    private String password;

    private Boolean isHashPassword;

    private String role;

    private String auth;
}
