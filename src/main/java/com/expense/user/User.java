package com.expense.user;

import jakarta.persistence.*;
import lombok.*;
import com.expense.user.enums.UserRoles;


@Entity // tells JPA (Java ORM) to map this to a SQL table
@Table(name = "users") // table name in MySQL
@Data // Lombok: generates getter/setter/toString/etc
@NoArgsConstructor // needed for JPA
@AllArgsConstructor
@Builder // allows easy object building (User.builder()...)
public class User {
    @Id // marks this as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false) // userName must be unique
    private String userName;

    @Column(unique = true, nullable = false) // email must be unique
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles  role = UserRoles.ROLE_USER; // e.g., ROLE_USER or ROLE_ADMIN
}
