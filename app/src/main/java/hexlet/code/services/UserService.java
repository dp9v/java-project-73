package hexlet.code.services;

import hexlet.code.dtos.UserDto;
import hexlet.code.models.User;
import hexlet.code.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createNewUser(UserDto userDto) {
        return userRepository.save(
            merge(new User(), userDto)
        );
    }

    public User updateUser(long id, UserDto userDto) {
        return userRepository.save(
            merge(getById(id), userDto)
        );
    }

    public User getById(long id) {
        return userRepository
            .findById(id)
            .orElseThrow();
    }

    public List<User> getAll() {
        return userRepository.findAll()
            .stream()
            .toList();
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    private User merge(User user, UserDto userDto) {
        return user
            .setEmail(userDto.email())
            .setFirstName(userDto.firstName())
            .setLastName(userDto.lastName())
            .setPassword(passwordEncoder.encode(userDto.password()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Not found user with 'username': " + username));
    }

}
