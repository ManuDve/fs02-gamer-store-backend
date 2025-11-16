package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.dto.UserResponseDto;
import cl.maotech.gamerstoreback.exception.DuplicateResourceException;
import cl.maotech.gamerstoreback.exception.ResourceNotFoundException;
import cl.maotech.gamerstoreback.mapper.UserMapper;
import cl.maotech.gamerstoreback.model.User;
import cl.maotech.gamerstoreback.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRespository userRespository;

    public List<UserResponseDto> getAllUsers() {
        return userRespository.findAll().stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.NOT_FOUND + id));
        return UserMapper.toResponseDto(user);
    }

    public UserResponseDto createUser(User user) {
        if (userRespository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException(Messages.User.EMAIL_ALREADY_EXISTS + user.getUsername());
        }
        User savedUser = userRespository.save(user);
        return UserMapper.toResponseDto(savedUser);
    }

    public UserResponseDto updateUser(Long id, User user) {
        User existingUser = userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.NOT_FOUND + id));

        if (!existingUser.getUsername().equals(user.getUsername()) && userRespository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException(Messages.User.EMAIL_ALREADY_EXISTS + user.getUsername());
        }

        existingUser.setName(user.getName());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhone(user.getPhone());
        User updatedUser = userRespository.save(existingUser);
        return UserMapper.toResponseDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.NOT_FOUND + id));
        userRespository.delete(user);
    }
}
