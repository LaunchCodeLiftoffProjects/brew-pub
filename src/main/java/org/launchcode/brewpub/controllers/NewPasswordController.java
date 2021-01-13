package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.UserRepository;
import org.launchcode.brewpub.models.dto.NewPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NewPasswordController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/forgotPassword")
    public String viewForgotPasswordForm(Model model) {
        model.addAttribute("title","Forgot Password");
        model.addAttribute(new NewPasswordDTO());
        return "forgotPassword";
    }

    @PostMapping("/newPassword")
    public String newPasswordForm(Model model, NewPasswordDTO newPasswordDTO) {
        model.addAttribute("title", "New Password");
        User existingUser = userRepository.findByEmail(newPasswordDTO.getEmail());
        if (existingUser == null) {
            return "redirect:";
        }
        model.addAttribute("newPasswordDTO", newPasswordDTO);
        return "newPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetUserPassword(Model model, NewPasswordDTO newPasswordDTO) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (newPasswordDTO.getEmail() != null && newPasswordDTO.getPassword().equals(newPasswordDTO.getVerifyPassword())) {
            User existingUser = userRepository.findByEmail(newPasswordDTO.getEmail());
            existingUser.setPwhash(encoder.encode(newPasswordDTO.getPassword()));
            userRepository.save(existingUser);
            model.addAttribute("title","New Password");
            model.addAttribute("message", "Password successfully reset. You can now log in with the new credentials.");
            model.addAttribute("successResetPassword");
        } else {
            model.addAttribute("title","New Password");
            model.addAttribute("message","Passwords do not match.");
        }
        return "newPassword";
    }
}