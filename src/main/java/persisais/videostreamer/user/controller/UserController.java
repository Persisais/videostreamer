package persisais.videostreamer.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
//import io.swagger.v3.oas.annotations.tags.Tag;
import persisais.videostreamer.user.model.User;
import persisais.videostreamer.user.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class UserController {
    private final UserService userService;

    @PostMapping("sign-up")
    public List<User> signUp() {
        return userService.getAll();
    }
    @PostMapping("sign-in")
    public List<User> signIn() {
        return userService.getAll();
    }
}
