package cl.maotech.gamerstoreback.dto;

import cl.maotech.gamerstoreback.constant.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityRequestDto {

    @NotNull(message = Messages.Authority.USER_NOT_FOUND)
    private Long userId;

    @NotBlank(message = Messages.Authority.AUTHORITY_REQUIRED)
    private String authority;
}

