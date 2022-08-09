package br.com.letscode.domain.entity;

import io.quarkus.security.jpa.*;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = User.TABLE_NAME)
@UserDefinition
public class User {

    public static final String TABLE_NAME = "USERS";
    public static final String SEQUENCE_NAME = "USERS_SEQ";

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = User.SEQUENCE_NAME, sequenceName = User.SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(generator = User.SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Username
    @Column(name = "USERNAME")
    public String username;

    @Password
    @Column(name = "PASSWORD")
    public String password;

    @Roles
    @Column(name = "ROLE")
    public String role;

}
