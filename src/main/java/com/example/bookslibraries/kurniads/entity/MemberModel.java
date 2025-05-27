package com.example.bookslibraries.kurniads.entity;

import com.example.bookslibraries.kurniads.dto.TimeStampBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="member")
@Data
@NoArgsConstructor
public class MemberModel extends TimeStampBase {

    @Id
    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birth_day", nullable = false)
    private String birthDay;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private int isActive;

}
