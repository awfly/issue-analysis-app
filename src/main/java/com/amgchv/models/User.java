package com.amgchv.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue
    private Long userId;

    @NonNull
    private String account;

    @NonNull
    private String lastName;

    @NonNull
    private String firstName;

    @NonNull
    @Email
    private String email;

    @NonNull
    private String password; // added for Spring Security

    @OneToOne
    private Role role;
}
