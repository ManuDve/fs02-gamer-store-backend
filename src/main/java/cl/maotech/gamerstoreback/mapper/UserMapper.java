package cl.maotech.gamerstoreback.mapper;

import cl.maotech.gamerstoreback.dto.UserResponseDto;
import cl.maotech.gamerstoreback.model.User;

public class UserMapper {

    public static UserResponseDto toResponseDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getUsername(),  // El campo username se serializa como "email" gracias a @JsonProperty
                user.getPhone()
        );
    }

    private UserMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
