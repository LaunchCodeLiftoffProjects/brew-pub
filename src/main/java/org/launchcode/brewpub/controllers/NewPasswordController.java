package org.launchcode.brewpub.controllers;

import org.launchcode.brewpub.models.User;
import org.launchcode.brewpub.models.data.UserRepository;
import org.launchcode.brewpub.models.dto.CreateAccountDTO;
import org.launchcode.brewpub.models.dto.NewPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class NewPasswordController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/forgotPassword")
    public String viewForgotPasswordForm(Model model) {
        model.addAttribute("title", "forgotPassword");
        model.addAttribute(new NewPasswordDTO());
        return "forgotPassword";
    }

    @RequestMapping(value="/newPassword", method = { RequestMethod.GET, RequestMethod.POST })
    public String newPasswordForm(Model model, NewPasswordDTO newPasswordDTO, Principal principal) {
        model.addAttribute("title", "newPassword");
        User existingUser = userRepository.findByEmail(newPasswordDTO.getEmail());
        if (existingUser == null && principal == null) {
            return "redirect:";
        } else if (principal != null) {
            Optional<User> resultUser= Optional.ofNullable(userRepository.findByUsername(principal.getName()));
        } else if (existingUser != null) {
            model.addAttribute("newPasswordDTO", newPasswordDTO);
        }
        return "newPassword";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetUserPassword(Model model, NewPasswordDTO newPasswordDTO) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (newPasswordDTO.getEmail() != null && newPasswordDTO.getPassword().equals(newPasswordDTO.getVerifyPassword())) {
            User existingUser = userRepository.findByEmail(newPasswordDTO.getEmail());
            existingUser.setPwhash(encoder.encode(newPasswordDTO.getPassword()));
            userRepository.save(existingUser);
            model.addAttribute("message", "Password successfully reset. You can now log in with the new credentials.");
            model.addAttribute("successResetPassword");
        } else {
            model.addAttribute("message","Passwords do not match.");
        }
        return "newPassword";
    }
}