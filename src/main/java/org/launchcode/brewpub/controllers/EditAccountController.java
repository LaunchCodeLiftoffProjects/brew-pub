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

@Controller
public class EditAccountController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/accountDetails")
    public String showUserAccountInfo(Model model) {
        model.addAttribute("title", "accountDetails");
        model.addAttribute(new EditAccountDTO());
        return "accountDetails";
    }

    @PostMapping("/editAccount")
    public String showEditAccountForm(Model model, EditAccountDTO editAccountDTO) {
        model.addAttribute("title", "editAccount");
        User existingUser = userRepository.findByEmail(editAccountDTO.getEmail());
        if (existingUser == null) {
            return "redirect:";
        }
        model.addAttribute("editAccountDTO", editAccountDTO);
        return "editAccount";
    }

    @RequestMapping("/resetAccountInformation")
    public String processEditAccountForm(@ModelAttribute @Valid EditAccountDTO editAccountDTO,
                                         Errors errors, HttpServletRequest request,
                                         Model model) {

        User existingUser = userRepository.findByEmail(editAccountDTO.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        existingUser.setPwhash(encoder.encode(editAccountDTO.getPassword()));

     /*   if (errors.hasErrors()) {
            model.addAttribute("title", "editAccount");
            model.addAttribute("errors", errors);
            return "editAccount";
        }

        User existingUsername = userRepository.findByUsername(editAccountDTO.getUsername());
        User existingEmail = userRepository.findByEmail(editAccountDTO.getEmail());

        if (existingUsername != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "editAccount");
            return "editAccount";
        }*/




        if (editAccountDTO.getEmail() != null && editAccountDTO.getPassword().equals(editAccountDTO.getVerifyPassword())) {
            existingUser.setPwhash(encoder.encode(editAccountDTO.getPassword()));
            userRepository.save(existingUser);
            model.addAttribute("message", "Password successfully reset. You can now log in with the new credentials.");
            model.addAttribute("successResetPassword");
        } else {
            model.addAttribute("message","Passwords do not match.");
        }
        return "editAccount";

    }
}
