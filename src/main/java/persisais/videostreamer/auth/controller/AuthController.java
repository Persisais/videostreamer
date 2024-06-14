package persisais.videostreamer.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import persisais.videostreamer.user.dto.UserRequest;
import persisais.videostreamer.user.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService; // Убедитесь, что у вас есть сервис, который может обрабатывать регистрацию и аутентификацию

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRequest("", "", "", "USER")); // Инициализация с дефолтными значениями
        return "register";
    }

    @PostMapping("/register")
    public void registerUserAccount(@ModelAttribute("user") UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            //return "register";
        }
        try {
            //userService.registerNewUser(userRequest);
            //return "redirect:/login"; // перенаправление на страницу входа после успешной регистрации
        } catch (Exception e) {
            result.rejectValue("email", "user.email", "An account already exists for this email.");
            //return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Если вы используете Spring Security, метод для обработки POST запроса на вход не требуется,
    // поскольку это будет обрабатываться автоматически
}