package com.example.bookslibraries.kurniads.entity;

import com.example.bookslibraries.kurniads.dto.TimeStampBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="role")
@Data
@NoArgsConstructor
public class RoleModel extends TimeStampBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name", nullable = false)
    private String roleName;
}
