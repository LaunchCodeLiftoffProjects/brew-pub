package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.UserRepository;
import org.launchcode.brewpub.models.dto.CreateAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/createAccount")
    public String displayCreateAccountForm(Model model) {
        model.addAttribute(new CreateAccountDTO());
        model.addAttribute("title", "createAccount");
        return "/createAccount";
    }

    @PostMapping("/createAccount")
    public String processCreateAccountForm(@ModelAttribute @Valid CreateAccountDTO createAccountDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "createAccount");
            model.addAttribute("errors", errors);
            return "createAccount";
        }

        User existingUsername = userRepository.findByUsername(createAccountDTO.getUsername());
        User existingEmail = userRepository.findByEmail(createAccountDTO.getEmail());

        if (existingUsername != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "createAccount");
            return "createAccount";
        }

        if (existingEmail != null) {
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
            model.addAttribute("title", "createAccount");
            return "createAccount";
        }

        String password = createAccountDTO.getPassword();
        String verifyPassword = createAccountDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "createAccount");
            return "createAccount";
        }

        User newUser = new User(createAccountDTO.getFirstName(),createAccountDTO.getLastName(), createAccountDTO.getEmail(), createAccountDTO.getUsername(), createAccountDTO.getPassword());
        userRepository.save(newUser);

        try {
            request.login(createAccountDTO.getUsername(), createAccountDTO.getPassword());
        } catch (ServletException exception) {
            return "redirect:/login";
        }

        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
