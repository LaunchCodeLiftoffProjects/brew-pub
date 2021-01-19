package org.launchcode.brewpub.controllers;


import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.UserRepository;
import org.launchcode.brewpub.models.dto.EditAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String processEditAccountForm(@ModelAttribute @Valid EditAccountDTO editAccountDTO,
                                         Errors errors, HttpServletRequest request,
                                         Model model) {

        User existingUser = userRepository.findByEmail(editAccountDTO.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        existingUser.setPwhash(encoder.encode(editAccountDTO.getPassword()));


        if (editAccountDTO.getEmail() != null && editAccountDTO.getPassword().equals(editAccountDTO.getVerifyPassword())) {
            existingUser.setPwhash(encoder.encode(editAccountDTO.getPassword()));
            userRepository.save(existingUser);
            model.addAttribute("message", "Password successfully reset. You can now log in with the new credentials.");
            model.addAttribute("successResetPassword");
        } else {
            model.addAttribute("message","Passwords do not match.");
        }
        return "editAccount/editAccount";

    }
}
