package com.ganatan.backend_java.modules.users.entities;

import com.ganatan.backend_java.modules.users.user.login.RegisterRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String email;

    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo", columnDefinition = "BYTEA")
    private byte[] photo;

    private String photoContentType;

    public User(RegisterRequest post) {
        this.email = post.getEmail();
    }
}
