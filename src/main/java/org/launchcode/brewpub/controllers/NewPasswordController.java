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

    @PostMapping("/newPassword")
    public String newPasswordForm(Model model, NewPasswordDTO newPasswordDTO) {
        model.addAttribute("title", "newPassword");
        User existingUser = userRepository.findByEmail(newPasswordDTO.getEmail());
        if (existingUser == null) {
            return "redirect:";
        }
        model.addAttribute("newPasswordDTO", newPasswordDTO);
        return "newPassword";
    }

//    @PostMapping("")
//    public String processNewPasswordForm(@ModelAttribute @Valid NewPasswordDTO newPasswordDTO, Errors errors, Model model) {
//        String password = newPasswordDTO.getPassword();
//        String verifyPassword = newPasswordDTO.getVerifyPassword();
//
//        if (password.equals(verifyPassword)) {
//
//        }
//        return "did it";

//    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetUserPassword(Model model, NewPasswordDTO newPasswordDTO) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (newPasswordDTO.getEmail() != null && newPasswordDTO.getPassword().equals(newPasswordDTO.getVerifyPassword())) {
       //  if (true) {
            // Use email to find user
            User existingUser = userRepository.findByEmail(newPasswordDTO.getEmail());
            existingUser.setPwhash(encoder.encode(newPasswordDTO.getPassword()));
            userRepository.save(existingUser);
            model.addAttribute("message", "Password successfully reset. You can now log in with the new credentials.");
            model.addAttribute("successResetPassword");
        } else {
            model.addAttribute("message","Not a valid user");
        }
        return "newPassword";
    }
}