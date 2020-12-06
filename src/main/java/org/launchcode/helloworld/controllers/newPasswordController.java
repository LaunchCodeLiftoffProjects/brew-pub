package org.launchcode.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class newPasswordController {

    @GetMapping("/newPassword")
    public String newPasswordForm(){
        return "newPassword";
    }

}