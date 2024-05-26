package ua.petproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.petproject.models.User;
import ua.petproject.service.UserService;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        User existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            result.rejectValue("email", "There is already user registered with the email/username provided");
        }
        User existingUserUsername = userService.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            result.rejectValue("username", "There is already user registered with the email/username provided");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/api/books?success";
    }
}
