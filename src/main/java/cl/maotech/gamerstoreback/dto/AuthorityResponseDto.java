package cl.maotech.gamerstoreback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityResponseDto {
    private Long id;
    private Long userId;
    private String authority;
}

