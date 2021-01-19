package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class EditAccountController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/accountDetails")
    public String showUserAccountInfo(Model model, Principal principal) {
        model.addAttribute("title", "accountDetails");
        Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        if (resultUser.isEmpty()) {
            return "index";
        } else {
            User user = resultUser.get();
            model.addAttribute("user", user);
        }
        return "editAccount/accountDetails";
    }

    @GetMapping("/editAccount")
    public String showEditAccountForm(Model model, Principal principal) {
        model.addAttribute("title", "editAccount");
        Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        if (resultUser.isEmpty()) {
            return "index";
        } else {
            User user = resultUser.get();
            model.addAttribute("user", user);
        }
        return "editAccount/editAccount";
    }

    @RequestMapping("/resetAccountInformation")
    public String processEditAccountForm(@ModelAttribute @Valid User userTemp,
                                         Errors errors, Model model, Principal principal, HttpServletRequest request, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("user", userTemp);
            model.addAttribute("title", "editAccount");
            model.addAttribute("errors", errors);
            return "editAccount/editAccount";
        } else {
            Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));

            if (resultUser.isEmpty()) {
                return "index";
            }

            User user = resultUser.get();

            user.setEmail(userTemp.getEmail());
            user.setFirstName(userTemp.getFirstName());
            user.setLastName(userTemp.getLastName());

            if (!user.getUsername().equals(userTemp.getUsername())) {
                user.setUsername(userTemp.getUsername());
                userRepository.save(user);
                model.addAttribute("message", "Account information successfully changed.");
                return "login";
            } else {
                userRepository.save(user);
                return "redirect:/accountDetails";
            }
        }
    }
}
