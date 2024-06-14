package persisais.videostreamer.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import persisais.videostreamer.user.dto.UserRequest;
import persisais.videostreamer.user.model.User;
import persisais.videostreamer.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

/*    public void registerNewUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new IllegalStateException("Email already exists");
        }

        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setEmail(userRequest.email());
        //newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setPassword(userRequest.password());
        newUser.setUserRole(userRequest.userRole());
        userRepository.save(newUser);
    }*/
}
