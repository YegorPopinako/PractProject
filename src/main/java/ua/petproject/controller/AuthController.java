package ua.petproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.petproject.models.UserEntity;
import ua.petproject.service.UserService;

@Controller
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        UserEntity userEntity = new UserEntity();
        model.addAttribute("userEntity", userEntity);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("userEntity") @Valid UserEntity userEntity, BindingResult result, Model model) {
        UserEntity existingUserEmailEntity = userService.findByEmail(userEntity.getEmail());
        if(existingUserEmailEntity != null && existingUserEmailEntity.getEmail() != null && !existingUserEmailEntity.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        UserEntity existingUserUsernameEntity = userService.findByUsername(userEntity.getUsername());
        if(existingUserUsernameEntity != null && existingUserUsernameEntity.getUsername() != null && !existingUserUsernameEntity.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if (result.hasErrors()) {
            model.addAttribute("userEntity", userEntity);
            return "register";
        }
        userService.saveUser(userEntity);
        return "redirect:/api/books?success";
    }
}
