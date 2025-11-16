package cl.maotech.gamerstoreback.model;

import cl.maotech.gamerstoreback.constant.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = Messages.User.NAME_REQUIRED)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = Messages.User.EMAIL_REQUIRED)
    @Email(message = Messages.User.EMAIL_INVALID)
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = Messages.User.PASSWORD_REQUIRED)
    @Column(nullable = false)
    private String password;

    @NotBlank(message = Messages.User.PHONE_REQUIRED)
    @Column(nullable = false)
    private String phone;
}
