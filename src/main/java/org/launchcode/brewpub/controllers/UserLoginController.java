package org.launchcode.brewpub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserLoginController {

    @RequestMapping("/userLogin")
    public String displayUserLoginForm() {

        return "userLogin";
    }
}
