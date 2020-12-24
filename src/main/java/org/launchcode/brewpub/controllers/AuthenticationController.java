package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.BrewUser;
import org.launchcode.brewpub.models.data.BrewUserRepository;
import org.launchcode.brewpub.models.dto.CreateAccountDTO;
import org.launchcode.brewpub.models.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {
    @Autowired
    BrewUserRepository brewUserRepository;

    private static final String userSessionKey = "user";

    public BrewUser getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<BrewUser> user = brewUserRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, BrewUser brewUser) {
        session.setAttribute(userSessionKey, brewUser.getId());
    }



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

        BrewUser existingUsername = brewUserRepository.findByUsername(createAccountDTO.getUsername());
        BrewUser existingEmail = brewUserRepository.findByEmail(createAccountDTO.getEmail());

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

        BrewUser newBrewUser = new BrewUser(createAccountDTO.getFirstName(),createAccountDTO.getLastName(), createAccountDTO.getEmail(), createAccountDTO.getUsername(), createAccountDTO.getPassword());
        brewUserRepository.save(newBrewUser);
        setUserInSession(request.getSession(), newBrewUser);

        return "redirect:";
    }

    @GetMapping("/userLogin")
    public String displayLoginDTOForm(Model model) {
        model.addAttribute(new LoginDTO());
        model.addAttribute("title", "Log In");
        return "userLogin";
    }

    @PostMapping("/userLogin")
    public String processLoginDTOForm(@ModelAttribute @Valid LoginDTO loginDTO,
                                   Errors errors, HttpServletRequest request,
                                   Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "userLogin";
        }

        BrewUser theBrewUser = brewUserRepository.findByUsername(loginDTO.getUsername());

        if (theBrewUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("title", "Log In");
            return "userLogin";
        }

        String password = loginDTO.getPassword();

        if (!theBrewUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");
            return "userLogin";
        }

        setUserInSession(request.getSession(), theBrewUser);

        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/userLogin";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
