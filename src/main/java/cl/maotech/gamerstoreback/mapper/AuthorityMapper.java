package cl.maotech.gamerstoreback.mapper;

import cl.maotech.gamerstoreback.dto.AuthorityResponseDto;
import cl.maotech.gamerstoreback.model.Authority;

public class AuthorityMapper {

    public static AuthorityResponseDto toResponseDto(Authority authority) {
        if (authority == null) {
            return null;
        }
        return new AuthorityResponseDto(
                authority.getId(),
                authority.getUser() != null ? authority.getUser().getId() : null,
                authority.getAuthority()
        );
    }

    private AuthorityMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

