package com.example.bookslibraries.kurniads.dto;


import com.example.bookslibraries.kurniads.util.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
public class MemberDto {

    private String accountId;

    private String role;

    private String name;

    private String nickname;

    private String email;

    private String password;

    private Boolean isUpdatePassword;

    private Boolean isHashPassword;

    private String phone;

    private String birthDay;

    private String address;

    private String status;

    private String auth;

}
