package org.launchcode.helloworld.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
    public class forgotPasswordController {

        @GetMapping("/forgotPassword")
        public String viewForgotPasswordForm(){
            return "forgotPassword";
        }

    }

