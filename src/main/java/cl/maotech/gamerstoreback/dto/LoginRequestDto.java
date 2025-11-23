package cl.maotech.gamerstoreback.dto;

import cl.maotech.gamerstoreback.constant.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = Messages.Validation.EMAIL_NOT_EMPTY)
    @Email(message = Messages.Validation.EMAIL_VALID)
    private String email;

    @NotBlank(message = Messages.Validation.PASSWORD_NOT_EMPTY)
    private String password;
}
