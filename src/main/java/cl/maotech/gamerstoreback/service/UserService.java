package cl.maotech.gamerstoreback.service;

import cl.maotech.gamerstoreback.constant.Messages;
import cl.maotech.gamerstoreback.exception.DuplicateResourceException;
import cl.maotech.gamerstoreback.exception.ResourceNotFoundException;
import cl.maotech.gamerstoreback.model.User;
import cl.maotech.gamerstoreback.repository.UserRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRespository userRespository;

    public List<User> getAllUsers() {
        return userRespository.findAll();
    }

    public User getUserById(Long id) {
        return userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.User.NOT_FOUND + id));
    }

    public User createUser(User user) {
        if (userRespository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException(Messages.User.EMAIL_ALREADY_EXISTS + user.getEmail());
        }
        return userRespository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);

        if (!existingUser.getEmail().equals(user.getEmail()) && userRespository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException(Messages.User.EMAIL_ALREADY_EXISTS + user.getEmail());
        }

        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRespository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRespository.delete(user);
    }
}
